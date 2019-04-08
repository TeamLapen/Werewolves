package de.teamlapen.vampirewerewolf.player.werewolf;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirewerewolf.player.werewolf.actions.WerewolfActions;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirewerewolf.util.ScoreboardUtil;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.vampirism.player.VampirismPlayer;
import de.teamlapen.vampirism.player.actions.ActionHandler;
import de.teamlapen.vampirism.player.skills.SkillHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import javax.annotation.Nonnull;
import java.util.function.Predicate;

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

    private final ActionHandler<IWerewolfPlayer> actionHandler;
    private final SkillHandler<IWerewolfPlayer> skillHandler;
    private final WerewolfPlayerSpecialAttributes specialAttributes;
    public int harvestLevel = 0;
    public float harvestSpeed = 1;

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
        return !specialAttributes.werewolf;
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
            if (getSpecialAttributes().werewolf) {
                WerewolfActions.werewolf_werewolf.onLevelChanged(newLevel, oldLevel, this);
                int level = getLevel();
                int harvestLevel = 0;
                float harvestSpeed = 1;
                if (level >= 14) {
                    harvestLevel = 2;
                    harvestSpeed = 6F;
                } else if (level >= 10) {
                    harvestLevel = 2;
                    harvestSpeed = 4F;
                } else if (level >= 5) {
                    harvestLevel = 1;
                    harvestSpeed = 2F;
                }
                this.harvestLevel = harvestLevel;
                this.harvestSpeed = harvestSpeed;
            }
        } else {
            if (oldLevel == 0) {

            } else if (newLevel == 0) {
                actionHandler.resetTimers();
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
                if (this.getSpecialAttributes().werewolf) {
                    player.attackEntityFrom(VampireWerewolfMod.AQUA, 3);
                } else {
                    player.attackEntityFrom(VampireWerewolfMod.AQUA, 1);
                }
            }
            int heal = this.getSpecialAttributes().heals;
            if (heal >= 1) {
                int toHeal = heal / 2 < 1 ? 1 : heal / 2;
                if (this.getSpecialAttributes().healing && this.getSpecialAttributes().werewolf) {
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

    @Override
    protected void loadUpdate(NBTTagCompound nbt) {
        actionHandler.readUpdateFromServer(nbt);
        skillHandler.readUpdateFromServer(nbt);
        if (nbt.hasKey("harvestLevel")) this.harvestLevel = nbt.getInteger("harvestLevel");
        if (nbt.hasKey("harvestSpeed")) this.harvestSpeed = nbt.getFloat("harvestSpeed");
    }

    @Override
    protected void writeFullUpdate(NBTTagCompound nbt) {
        actionHandler.writeUpdateForClient(nbt);
        skillHandler.writeUpdateForClient(nbt);
        nbt.setInteger("harvestLevel", harvestLevel);
        nbt.setFloat("harvestSpeed", harvestSpeed);
    }

    private void loadData(NBTTagCompound nbt) {
        actionHandler.loadFromNbt(nbt);
        skillHandler.loadFromNbt(nbt);
        if (nbt.hasKey("harvestLevel")) this.harvestLevel = nbt.getInteger("harvestLevel");
        if (nbt.hasKey("harvestSpeed")) this.harvestSpeed = nbt.getFloat("harvestSpeed");
    }

    private void saveData(NBTTagCompound nbt) {
        actionHandler.saveToNbt(nbt);
        skillHandler.saveToNbt(nbt);
        nbt.setInteger("harvestLevel", harvestLevel);
        nbt.setFloat("harvestSpeed", harvestSpeed);
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
