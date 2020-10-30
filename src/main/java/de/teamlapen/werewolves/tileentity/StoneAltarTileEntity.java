package de.teamlapen.werewolves.tileentity;

import de.teamlapen.lib.lib.inventory.InventoryHelper;
import de.teamlapen.lib.lib.tile.InventoryTileEntity;
import de.teamlapen.vampirism.core.ModParticles;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.player.werewolf.WerewolfLevelConf;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;

public class StoneAltarTileEntity extends InventoryTileEntity implements ITickableTileEntity {
    private static final ITextComponent NAME = new TranslationTextComponent("container.werewolves.stone_altar");
    private int targetLevel;
    private Phase phase = Phase.NOT_RUNNING;
    private PlayerEntity player;
    private int ticks;

    public StoneAltarTileEntity() {
        super(ModTiles.stone_altar, 2, StoneAltarContainer.SELECTOR_INFOS);
    }

    @Override
    public void tick() {
        if (this.world != null) {
            switch (phase) {
                case STARTING:
                    if (ticks == 0) {
                        this.phase = Phase.FOG;
                        this.ticks = 300;
                    }
                    break;
                case FOG:
                    if (ticks == 0) {
                        this.phase = Phase.ENDING;
                        this.ticks = 60;
                        this.player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60, 3, false, false));
                    } else if (this.ticks % 10 == 0) {
                        if (!world.isRemote) {
                            ModParticles.spawnParticlesServer(this.world, ParticleTypes.MYCELIUM, this.pos.getX() + Math.random(), this.pos.getY() + 1, this.pos.getZ() + Math.random(), 30, 0.6, 0.6, 0.6, 0);
                        }
                    }
                    break;
                case ENDING:
                    if (ticks == 0) {
                        this.phase = Phase.NOT_RUNNING;
                        this.endRitual();
                        this.cleanup();
                    }
                    break;
                case NOT_RUNNING:
                    return;
            }
            --ticks;
        }
    }

    public void startRitual(PlayerEntity player) {
        if (phase == Phase.NOT_RUNNING) {
            this.phase = Phase.STARTING;
            this.ticks = 40;
            this.player = player;
            this.consumeItems();
        }
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
        this.getStackInSlot(0).shrink(requirement.liverAmount);
        this.getStackInSlot(1).shrink(requirement.bonesAmount);
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
        if (!checkLevel(player)) {
            return Result.WRONG_LEVEL;
        }
        if (player.getEntityWorld().isDaytime()) {
            return Result.NIGHT_ONLY;
        } else if (!checkItemRequirements(player)) {
            return Result.INV_MISSING;
        }
        return Result.OK;
    }

    private boolean checkLevel(PlayerEntity player) {
        return true;
    }

    private boolean checkItemRequirements(PlayerEntity player) {
        int newLevel = this.targetLevel;
        WerewolfLevelConf.StoneAltarRequirement req = (WerewolfLevelConf.StoneAltarRequirement) WerewolfLevelConf.getInstance().getRequirement(newLevel);
        ItemStack missing = InventoryHelper.checkItems(this, new Item[]{ModItems.liver, ModItems.bones}, new int[]{req.liverAmount, req.bonesAmount});
        return missing.isEmpty();
    }

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        return NAME;
    }

    @Nonnull
    @Override
    protected Container createMenu(int id, @Nonnull PlayerInventory playerInventory) {
        return new StoneAltarContainer(id, playerInventory, this, this.world == null ? IWorldPosCallable.DUMMY : IWorldPosCallable.of(this.world, this.pos));
    }

    public enum Result {
        IS_RUNNING, OK, WRONG_LEVEL, NIGHT_ONLY, INV_MISSING, OTHER_FACTION
    }

    public enum Phase {
        NOT_RUNNING, STARTING, FOG, ENDING
    }
}
