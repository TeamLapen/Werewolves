package de.teamlapen.werewolves.blocks.entity;

import de.teamlapen.lib.lib.blockentity.InventoryBlockEntity;
import de.teamlapen.lib.lib.inventory.InventoryHelper;
import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.core.ModParticles;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StoneAltarBlockEntity extends InventoryBlockEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Component NAME = Component.translatable("container.werewolves.stone_altar");
    private int targetLevel;
    private Phase phase = Phase.NOT_RUNNING;
    private Player player;
    private UUID playerUuid;
    private int ticks;
    private final LazyOptional<IItemHandler> itemHandlerOptional = LazyOptional.of(this::createWrapper);
    private List<BlockPos> fire_bowls;

    public StoneAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModTiles.STONE_ALTAR.get(), pos, state, 2, StoneAltarContainer.SELECTOR_INFOS);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, StoneAltarBlockEntity blockEntity) {
        if (level != null && !level.isClientSide) {
            if (blockEntity.playerUuid != null) {
                if (!blockEntity.loadRitual(blockEntity.playerUuid)) return;
                blockEntity.playerUuid = null;
                blockEntity.setChanged();
                level.sendBlockUpdated(pos, state, state, 3);
            }
            switch (blockEntity.phase) {
                case STARTING:
                    if (blockEntity.ticks == 0) {
                        blockEntity.phase = Phase.FOG;
                        blockEntity.ticks = 300;
                        blockEntity.setChanged();
                        level.sendBlockUpdated(pos, state, state, 3); //Notify client about started ritual
                    }
                    break;
                case FOG:
                    if (blockEntity.ticks == 0) {
                        blockEntity.phase = Phase.ENDING;
                        blockEntity.ticks = 90;
                        blockEntity.player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 120, 3, false, false));
                        blockEntity.setChanged();
                        level.setBlockAndUpdate(pos, state.setValue(StoneAltarBlock.LIT, false));
                    } else if (blockEntity.ticks % 10 == 0) {
                        ModParticles.spawnParticlesServer(level, ParticleTypes.MYCELIUM, pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random(), 30, 0.6, 0.6, 0.6, 0);
                        if (blockEntity.fire_bowls != null) {
                            for (BlockPos fire_bowl : blockEntity.fire_bowls) {
                                ModParticles.spawnParticlesServer(level, ParticleTypes.MYCELIUM, fire_bowl.getX() + Math.random(), fire_bowl.getY() + 1, fire_bowl.getZ() + Math.random(), 30, 0.6, 0.6, 0.6, 0);
                            }
                        }
                    }
                    break;
                case ENDING:
                    if (blockEntity.ticks == 0) {
                        blockEntity.phase = Phase.NOT_RUNNING;
                        blockEntity.endRitual();
                        blockEntity.cleanup();
                        blockEntity.setChanged();
                        level.sendBlockUpdated(pos, state, state, 3); //Notify client about started ritual
                    }
                    break;
                case NOT_RUNNING:
                    return;
            }
            --blockEntity.ticks;
        }
    }

    public void aboardRitual() {
        this.phase = Phase.NOT_RUNNING;
        this.ticks = 0;
        this.cleanup();
    }

    public boolean loadRitual(UUID playerUuid) {
        if (this.level == null) return false;
        if (this.level.players().size() == 0) return false;
        this.player = this.level.getPlayerByUUID(playerUuid);
        if (this.player != null && player.isAlive()) {
            this.targetLevel = WerewolfPlayer.get(player).getLevel() + 1;
        } else {
            this.phase = Phase.NOT_RUNNING;
            this.ticks = 0;
            LOGGER.warn("Failed to find player {}", playerUuid);
            return false;
        }
        return true;
    }

    /**
     * Needs to be called after {@link #setPlayer(Player)}
     */
    public void startRitual(BlockState state) {
        if (this.player == null) {
            this.level.setBlock(this.worldPosition, state.setValue(StoneAltarBlock.LIT, false), 11);
            return;
        }
        if (phase == Phase.NOT_RUNNING && state.getValue(StoneAltarBlock.LIT)) {
            this.phase = Phase.STARTING;
            this.ticks = 40;
            this.consumeItems();
            this.setChanged();
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setItem(int slot, @Nonnull ItemStack stack) {
        super.setItem(slot, stack);
        BlockState state = this.level.getBlockState(worldPosition);
        this.level.sendBlockUpdated(getBlockPos(), state, state, 3); //Notify client about started ritual
    }

    public void cleanup() {
        this.targetLevel = 0;
        this.player = null;
    }

    public void endRitual() {
        WerewolfPlayer.getOpt(this.player).ifPresent(werewolf -> {
            werewolf.getLevelHandler().reset();
            werewolf.syncLevelHandler();
        });
        FactionPlayerHandler handler = FactionPlayerHandler.get(this.player);
        int lvl = handler.getCurrentLevel() + 1;
        handler.setFactionLevel(WReference.WEREWOLF_FACTION, lvl);
    }

    public void consumeItems() {
        WerewolfLevelConf.StoneAltarRequirement requirement = ((WerewolfLevelConf.StoneAltarRequirement) WerewolfLevelConf.getInstance().getRequirement(FactionPlayerHandler.get(this.player).getCurrentLevel() + 1));
        this.getItem(0).shrink(requirement.liverAmount);
        this.getItem(1).shrink(requirement.bonesAmount);
    }

    public Phase getCurrentPhase() {
        return phase;
    }

    public Result canActivate(Player player) {
        if (phase != Phase.NOT_RUNNING) {
            return Result.IS_RUNNING;
        }
        if (!Helper.isWerewolf(player)) {
            return Result.OTHER_FACTION;
        }
        this.targetLevel = WerewolfPlayer.get(player).getLevel() + 1;
        if (!checkLevel(this.targetLevel)) {
            return Result.WRONG_LEVEL;
        }
        Result r = checkStructure();
        if (r != null) {
            return r;
        } else if (player.getCommandSenderWorld().isDay()) {
            return Result.NIGHT_ONLY;
        } else if (!checkItemRequirements(player)) {
            return Result.INV_MISSING;
        } else if (!WerewolfPlayer.getOpt(player).map(w -> w.getLevelHandler().canLevelUp()).orElse(false) && !VampirismMod.inDev) {
            return Result.TO_LESS_BLOOD;
        }
        return Result.OK;
    }

    private Result checkStructure() {
        List<BlockPos> i = new ArrayList<>();
        List<BlockPos> h = new ArrayList<>();

        AABB aabb = new AABB(this.worldPosition).inflate(3);
        for (double x = aabb.minX; x <= aabb.maxX; ++x) {
            for (double y = aabb.minY; y <= aabb.maxY; ++y) {
                for (double z = aabb.minZ; z <= aabb.maxZ; ++z) {
                    BlockPos pos = new BlockPos((int) x, (int) y, (int) z);
                    BlockState state = this.level.getBlockState(pos);
                    if (state.getBlock() == ModBlocks.STONE_ALTAR_FIRE_BOWL.get()) {
                        i.add(pos);
                        if (state.getValue(StoneAltarFireBowlBlock.LIT)) {
                            h.add(pos);
                        }
                    }
                }
            }
        }
        if (h.size() >= 4) {
            this.fire_bowls = h;
            return null;
        } else if (i.size() >= 4) {
            return Result.STRUCTURE_LIT;
        } else {
            return Result.STRUCTURE_LESS;
        }
    }

    private boolean checkLevel(int targetLevel) {
        return targetLevel >= WerewolfLevelConf.StoneAltarRequirement.START_LVL && targetLevel <= WerewolfLevelConf.StoneAltarRequirement.LAST_LVL;
    }

    private boolean checkItemRequirements(Player player) {
        WerewolfLevelConf.StoneAltarRequirement req = (WerewolfLevelConf.StoneAltarRequirement) WerewolfLevelConf.getInstance().getRequirement(this.targetLevel);
        ItemStack missing = InventoryHelper.checkItems(this, new Item[]{ModItems.LIVER.get(), ModItems.CRACKED_BONE.get()}, new int[]{req.liverAmount, req.bonesAmount});
        return missing.isEmpty();
    }

    public Map<Item, Integer> getMissingItems() {
        WerewolfLevelConf.StoneAltarRequirement req = (WerewolfLevelConf.StoneAltarRequirement) WerewolfLevelConf.getInstance().getRequirement(this.targetLevel);
        return Helper.getMissingItems(this, new Item[]{ModItems.LIVER.get(), ModItems.CRACKED_BONE.get()}, new int[]{req.liverAmount, req.bonesAmount});
    }

    @Nonnull
    @Override
    protected Component getDefaultName() {
        return NAME;
    }

    @Nonnull
    @Override
    protected AbstractContainerMenu createMenu(int id, @Nonnull Inventory playerInventory) {
        return new StoneAltarContainer(id, playerInventory, this, this.level == null ? ContainerLevelAccess.NULL : ContainerLevelAccess.create(this.level, this.worldPosition));
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        if (pkt.getTag() != null) {
            this.load(pkt.getTag());//TODO check
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public void load(@Nonnull CompoundTag tagCompound) {
        super.load(tagCompound);
        if (tagCompound.hasUUID("player")) {
            UUID playerUuid = tagCompound.getUUID("player");
            if (!this.loadRitual(playerUuid)) {
                this.playerUuid = playerUuid;
            }
            this.ticks = tagCompound.getInt("ticks");
            this.phase = Phase.valueOf(tagCompound.getString("phase"));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        if (player != null) {
            compound.putUUID("player", player.getUUID());
        }
        compound.putInt("ticks", this.ticks);
        compound.putString("phase", this.phase.name());
        super.saveAdditional(compound);
    }

    public enum Result {
        IS_RUNNING, OK, WRONG_LEVEL, NIGHT_ONLY, INV_MISSING, OTHER_FACTION, STRUCTURE_LESS, STRUCTURE_LIT, TO_LESS_BLOOD
    }

    public enum Phase {
        NOT_RUNNING, STARTING, FOG, ENDING
    }
}
