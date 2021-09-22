package de.teamlapen.werewolves.tileentity;

import de.teamlapen.lib.lib.inventory.InventoryHelper;
import de.teamlapen.lib.lib.tile.InventoryTileEntity;
import de.teamlapen.vampirism.core.ModParticles;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StoneAltarTileEntity extends InventoryTileEntity implements ITickableTileEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ITextComponent NAME = new TranslationTextComponent("container.werewolves.stone_altar");
    private int targetLevel;
    private Phase phase = Phase.NOT_RUNNING;
    private PlayerEntity player;
    private UUID playerUuid;
    private int ticks;
    private final LazyOptional<IItemHandler> itemHandlerOptional = LazyOptional.of(this::createWrapper);
    private List<BlockPos> fire_bowls;

    public StoneAltarTileEntity() {
        super(ModTiles.stone_altar, 2, StoneAltarContainer.SELECTOR_INFOS);
    }

    @Override
    public void tick() {
        if (this.level != null && !this.level.isClientSide) {
            if (this.playerUuid != null) {
                if (!loadRitual(this.playerUuid)) return;
                this.playerUuid = null;
                this.setChanged();
                BlockState state = this.level.getBlockState(this.worldPosition);
                this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
            }
            BlockState state = this.level.getBlockState(this.worldPosition);
            switch (this.phase) {
                case STARTING:
                    if (ticks == 0) {
                        this.phase = Phase.FOG;
                        this.ticks = 300;
                        this.setChanged();
                        this.level.sendBlockUpdated(getBlockPos(), state, state, 3); //Notify client about started ritual
                    }
                    break;
                case FOG:
                    if (ticks == 0) {
                        this.phase = Phase.ENDING;
                        this.ticks = 90;
                        this.player.addEffect(new EffectInstance(Effects.BLINDNESS, 120, 3, false, false));
                        this.setChanged();
                        this.level.setBlockAndUpdate(this.worldPosition, ModBlocks.stone_altar.defaultBlockState().setValue(StoneAltarBlock.LIT, false));
                    } else if (this.ticks % 10 == 0) {
                        ModParticles.spawnParticlesServer(this.level, ParticleTypes.MYCELIUM, this.worldPosition.getX() + Math.random(), this.worldPosition.getY() + 1, this.worldPosition.getZ() + Math.random(), 30, 0.6, 0.6, 0.6, 0);
                        if (fire_bowls != null) {
                            for (BlockPos fire_bowl : fire_bowls) {
                                ModParticles.spawnParticlesServer(this.level, ParticleTypes.MYCELIUM, fire_bowl.getX() + Math.random(), fire_bowl.getY() + 1, fire_bowl.getZ() + Math.random(), 30, 0.6, 0.6, 0.6, 0);
                            }
                        }
                    }
                    break;
                case ENDING:
                    if (ticks == 0) {
                        this.phase = Phase.NOT_RUNNING;
                        this.endRitual();
                        this.cleanup();
                        this.setChanged();
                        this.level.sendBlockUpdated(getBlockPos(), state, state, 3); //Notify client about started ritual
                    }
                    break;
                case NOT_RUNNING:
                    return;
            }
            --ticks;
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
     * Needs to be called after {@link #setPlayer(PlayerEntity)}
     */
    public void startRitual(BlockState state) {
        if (this.player == null) {
            this.level.setBlock(this.worldPosition, state.setValue(StoneAltarBlock.LIT, false),11);
            return;
        }
        if (phase == Phase.NOT_RUNNING && state.getValue(StoneAltarBlock.LIT)) {
            this.phase = Phase.STARTING;
            this.ticks = 40;
            this.consumeItems();
            this.setChanged();
        }
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);
        BlockState state = this.level.getBlockState(worldPosition);
        this.level.sendBlockUpdated(getBlockPos(), state, state, 3); //Notify client about started ritual
    }

    public void cleanup() {
        this.targetLevel = 0;
        this.player = null;
    }

    public void endRitual() {
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

    public Result canActivate(PlayerEntity player) {
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
        }
        return Result.OK;
    }

    private Result checkStructure() {
        List<BlockPos> i = new ArrayList<>();
        List<BlockPos> h = new ArrayList<>();

        AxisAlignedBB aabb = new AxisAlignedBB(this.worldPosition).inflate(3);
        for (double x = aabb.minX; x <= aabb.maxX; ++x) {
            for (double y = aabb.minY; y <= aabb.maxY; ++y) {
                for (double z = aabb.minZ; z <= aabb.maxZ; ++z) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = this.level.getBlockState(pos);
                    if (state.getBlock() == ModBlocks.stone_altar_fire_bowl) {
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

    @Nullable
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? this.itemHandlerOptional.cast() : super.getCapability(cap, side);
    }

    private boolean checkLevel(int targetLevel) {
        return targetLevel >= WerewolfLevelConf.StoneAltarRequirement.START_LVL && targetLevel <= WerewolfLevelConf.StoneAltarRequirement.LAST_LVL;
    }

    private boolean checkItemRequirements(PlayerEntity player) {
        WerewolfLevelConf.StoneAltarRequirement req = (WerewolfLevelConf.StoneAltarRequirement) WerewolfLevelConf.getInstance().getRequirement(this.targetLevel);
        ItemStack missing = InventoryHelper.checkItems(this, new Item[]{ModItems.liver, ModItems.bone}, new int[]{req.liverAmount, req.bonesAmount});
        return missing.isEmpty();
    }

    public Map<Item, Integer> getMissingItems() {
        WerewolfLevelConf.StoneAltarRequirement req = (WerewolfLevelConf.StoneAltarRequirement) WerewolfLevelConf.getInstance().getRequirement(this.targetLevel);
        return Helper.getMissingItems(this, new Item[]{ModItems.liver, ModItems.bone}, new int[]{req.liverAmount, req.bonesAmount});
    }

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        return NAME;
    }

    @Nonnull
    @Override
    protected Container createMenu(int id, @Nonnull PlayerInventory playerInventory) {
        return new StoneAltarContainer(id, playerInventory, this, this.level == null ? IWorldPosCallable.NULL : IWorldPosCallable.create(this.level, this.worldPosition));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 12, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        super.onDataPacket(net, pkt);
        this.load(null, pkt.getTag());//TODO check
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    @Override
    public void load(BlockState state, CompoundNBT tagCompound) {
        super.load(state, tagCompound);
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
    public CompoundNBT save(CompoundNBT compound) {
        if (player != null) {
            compound.putUUID("player", player.getUUID());
        }
        compound.putInt("ticks", this.ticks);
        compound.putString("phase", this.phase.name());
        return super.save(compound);
    }

    public enum Result {
        IS_RUNNING, OK, WRONG_LEVEL, NIGHT_ONLY, INV_MISSING, OTHER_FACTION, STRUCTURE_LESS, STRUCTURE_LIT
    }

    public enum Phase {
        NOT_RUNNING, STARTING, FOG, ENDING
    }
}
