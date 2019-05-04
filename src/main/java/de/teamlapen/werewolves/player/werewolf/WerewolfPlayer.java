package de.teamlapen.werewolves.player.werewolf;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.player.VampirismPlayer;
import de.teamlapen.vampirism.player.actions.ActionHandler;
import de.teamlapen.vampirism.player.skills.SkillHandler;
import de.teamlapen.werewolves.api.VReference;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.config.Balance;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfActions;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.ScoreboardUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

/**
 * Main class for Werewolve Players.
 */
public class WerewolfPlayer extends VampirismPlayer<IWerewolfPlayer> implements IWerewolfPlayer {
    @CapabilityInject(IWerewolfPlayer.class)
    public static final Capability<IWerewolfPlayer> CAP = getNull();
    private final static String TAG = "WerewolfPlayer";

    /**
     * Don't call before the construction event of the player entity is finished
     */
    public static WerewolfPlayer get(EntityPlayer player) {
        return (WerewolfPlayer) player.getCapability(CAP, null);
    }

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(IWerewolfPlayer.class, new Storage(), WerewolfPlayerDefaultImpl.class);
    }

    @SuppressWarnings("ConstantConditions")
    public static ICapabilityProvider createNewCapability(final EntityPlayer player) {
        return new ICapabilitySerializable<NBTTagCompound>() {

            final IWerewolfPlayer inst = new WerewolfPlayer(player);

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                CAP.getStorage().readNBT(CAP, inst, null, nbt);
            }

            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
                return capability == CAP ? CAP.<T>cast(inst) : null;
            }

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
                return CAP.equals(capability);
            }

            @Override
            public NBTTagCompound serializeNBT() {
                return (NBTTagCompound) CAP.getStorage().writeNBT(CAP, inst, null);
            }
        };
    }

    private static final UUID SPEED = UUID.fromString("57ac98ff-35a1-4115-96ee-2479dc7e1460");
    private static final UUID ARMOR = UUID.fromString("a16dfca7-98b1-44a1-8057-a9cb38fbfb19");
    private static final UUID ARMOR_TOUGHNESS = UUID.fromString("c70fbf55-9f19-4679-8daa-919b29ed7104");

    private final ActionHandler<IWerewolfPlayer> actionHandler;
    private final SkillHandler<IWerewolfPlayer> skillHandler;
    private final WerewolfPlayerSpecialAttributes specialAttributes;
    private int waterTime = 0;

    public WerewolfPlayer(EntityPlayer player) {
        super(player);
        actionHandler = new ActionHandler<>(this);
        skillHandler = new SkillHandler<>(this);
        specialAttributes = new WerewolfPlayerSpecialAttributes();
    }

    public WerewolfPlayerSpecialAttributes getSpecialAttributes() {
        return specialAttributes;
    }

    @Override
    public boolean canLeaveFaction() {
        return true;
    }

    @Override
    public IFaction getDisguisedAs() {
        return isDisguised() ? specialAttributes.disguisedAs : getFaction();
    }

    @Override
    public int getMaxLevel() {
        return REFERENCE.HIGHEST_WEREWOLF_LEVEL;
    }

    @Override
    public Predicate<Entity> getNonFriendlySelector(boolean otherFactionPlayers, boolean ignoreDisguise) {
        if (otherFactionPlayers) {
            return entity -> true;
        } else {
            return VampirismAPI.factionRegistry().getPredicate(getFaction(), ignoreDisguise);
        }
    }

    @Override
    public boolean isDisguised() {
        return !(specialAttributes.werewolf > 0);
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {
        if (!isRemote()) {
            ScoreboardUtil.updateScoreboard(player, ScoreboardUtil.WEREWOLF_LEVEL_CRITERIA, newLevel);
            if (newLevel > 0) {
                if (oldLevel == 0) {
                    skillHandler.enableRootSkill();
                }

            } else {
                actionHandler.resetTimers();
                skillHandler.disableAllSkills();
            }
            setValuesLevel(newLevel);
        } else {
            if (oldLevel == 0) {

            } else if (newLevel == 0) {
                actionHandler.resetTimers();
            }
        }
    }

    public void setValuesLevel(int level) {
        int harvestLevel = 0;
        float harvestSpeed = 1F;
        if (level >= 13) {
            harvestLevel = 3;
        } else if (level >= 19) {
            harvestLevel = 2;
        } else if (level >= 5) {
            harvestLevel = 1;
        }
        this.getSpecialAttributes().harvestLevel = harvestLevel;
        this.getSpecialAttributes().harvestSpeed = (float) (Balance.wp.HARVESTSPEED_MAX * (level / getMaxLevel()));
        if (this.getSpecialAttributes().werewolf > 0) {
            this.removeModifier();
            if (level != 0) {
                this.applyModifier();
            }
        }
    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        return skillHandler;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        return actionHandler;
    }

    @Override
    public ResourceLocation getCapKey() {
        return REFERENCE.WEREWOLF_PLAYER_KEY;
    }

    @Override
    public void onChangedDimension(int from, int to) {
    }

    @Override
    public void onDeath(DamageSource src) {
        actionHandler.deactivateAllActions();
    }

    @Override
    public boolean onEntityAttacked(DamageSource src, float amt) {
        return false;
    }

    @Override
    public void onJoinWorld() {
        if (getLevel() > 0) {
            actionHandler.onActionsReactivated();
        }
    }

    @Override
    public void onPlayerLoggedIn() {
    }

    @Override
    public void onPlayerLoggedOut() {
    }

    @Override
    public void onUpdate() {
        player.getEntityWorld().profiler.startSection("vampirewerewolf_werewolfPlayer");
        int level = getLevel();
        if (!isRemote()) {
            if (level > 0) {
                boolean sync = false;
                boolean syncToAll = false;
                NBTTagCompound syncPacket = new NBTTagCompound();
                if (actionHandler.updateActions()) {
                    sync = true;
                    syncToAll = true;
                    actionHandler.writeUpdateForClient(syncPacket);
                }
                if (skillHandler.isDirty()) {
                    sync = true;
                    skillHandler.writeUpdateForClient(syncPacket);
                }
                if (sync) {
                    sync(syncPacket, syncToAll);
                }
            }
        } else {
            if (level > 0) {
                actionHandler.updateActions();
            }
        }
        if (player.getEntityWorld().getWorldTime() % 40 == 0) {
            if (player.isInWater()) {
                waterTime++;
                if (waterTime > 3 && this.getSpecialAttributes().werewolf > 0) {
                    player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 60));
                    if (player.getEntityWorld().getBlockState(player.getPosition().up()).getBlock().equals(Blocks.WATER)) {
                        player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120));
                        player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60));
                    }
                }
            } else {
                waterTime = Math.max(0, --waterTime);
            }
            int heal = this.getSpecialAttributes().heals;
            if (heal >= 1) {
                int toHeal = heal / 2 < 1 ? 1 : heal / 2;
                if (this.getSpecialAttributes().healing && this.getSpecialAttributes().werewolf > 0) {
                    player.heal(toHeal);
                }
                this.getSpecialAttributes().removeHeal(toHeal);
            }
        }
        player.world.profiler.endSection();
    }

    @Override
    public void onUpdatePlayer(Phase phase) {
    }

    @Override
    protected WerewolfPlayer copyFromPlayer(EntityPlayer old) {
        WerewolfPlayer oldWerewolve = get(old);
        NBTTagCompound nbt = new NBTTagCompound();
        oldWerewolve.saveData(nbt);
        this.loadData(nbt);
        return oldWerewolve;
    }

    // TODO add armor drop
    public boolean transformWerewolf() {
        if (!this.getActionHandler().isActionUnlocked(WerewolfActions.werewolf_werewolf))
            return false;
        specialAttributes.werewolf = 1;
        // handleArmor();
        applyModifier();
        player.refreshDisplayName();
        return true;
    }

    public void transformHuman() {
        if (specialAttributes.werewolf == 0)
            return;
        specialAttributes.werewolf = 0;
        removeModifier();
        player.refreshDisplayName();
    }

    private void handleArmor() {
        for (ItemStack e : player.getArmorInventoryList()) {
            if (!e.isEmpty())
                player.dropItem(e, false);
        }
    }

    private void applyModifier() {
        EntityPlayer player = getRepresentingPlayer();
        if (this.getSpecialAttributes().werewolf > 0 && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).getModifier(SPEED) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(new AttributeModifier(SPEED, "werewolf_speed", (float) Balance.wpa.WEREWOLF_SPEED_MAX / getMaxLevel() * getLevel(), 2));
        }

        if (this.getSpecialAttributes().werewolf > 1 && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).getModifier(ARMOR) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).applyModifier(new AttributeModifier(ARMOR, "werewolf_armor", (float) Balance.wpa.WEREWOLF_ARMOR_MAX / getMaxLevel() * getLevel(), 0));
        }
        if (this.getSpecialAttributes().werewolf > 1 && player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).getModifier(ARMOR_TOUGHNESS) == null) {
            player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).applyModifier(new AttributeModifier(ARMOR_TOUGHNESS, "werewolf_armor_toughness", (float) Balance.wpa.WEREWOLF_ARMOR_THOUGNESS_MAX / getMaxLevel() * getLevel(), 0));
        }
    }

    private void removeModifier() {
        EntityPlayer player = getRepresentingPlayer();

        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(SPEED);
        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR).removeModifier(ARMOR);
        player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS).removeModifier(ARMOR_TOUGHNESS);
    }

    @Override
    protected void loadUpdate(NBTTagCompound nbt) {
        actionHandler.readUpdateFromServer(nbt);
        skillHandler.readUpdateFromServer(nbt);
    }

    @Override
    protected void writeFullUpdate(NBTTagCompound nbt) {
        actionHandler.writeUpdateForClient(nbt);
        skillHandler.writeUpdateForClient(nbt);
    }

    private void loadData(NBTTagCompound nbt) {
        actionHandler.loadFromNbt(nbt);
        skillHandler.loadFromNbt(nbt);
    }

    private void saveData(NBTTagCompound nbt) {
        actionHandler.saveToNbt(nbt);
        skillHandler.saveToNbt(nbt);
    }

    private static class Storage implements Capability.IStorage<IWerewolfPlayer> {
        @Override
        public void readNBT(Capability<IWerewolfPlayer> capability, IWerewolfPlayer instance, EnumFacing side, NBTBase nbt) {
            ((WerewolfPlayer) instance).loadData((NBTTagCompound) nbt);
        }

        @Override
        public NBTBase writeNBT(Capability<IWerewolfPlayer> capability, IWerewolfPlayer instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            ((WerewolfPlayer) instance).saveData(nbt);
            return nbt;
        }
    }

    @Override
    public IPlayableFaction<IWerewolfPlayer> getFaction() {
        return VReference.WEREWOLF_FACTION;
    }

    @Override
    public int getTheEntityID() {
        return player.getEntityId();
    }
}
