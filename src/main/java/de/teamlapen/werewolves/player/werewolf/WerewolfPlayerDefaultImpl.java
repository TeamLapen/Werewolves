package de.teamlapen.werewolves.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import de.teamlapen.werewolves.api.entities.player.werewolf.IWerewolfPlayer;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import java.util.UUID;
import java.util.function.Predicate;

@Deprecated
public class WerewolfPlayerDefaultImpl implements IWerewolfPlayer {

    @Override
    public EntityLivingBase getRepresentingEntity() {
        return null;
    }

    @Override
    public boolean canLeaveFaction() {
        return false;
    }

    @Override
    public IFaction getDisguisedAs() {
        return null;
    }

    @Override
    public IPlayableFaction<IWerewolfPlayer> getFaction() {
        return null;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public Predicate<Entity> getNonFriendlySelector(boolean otherFactionPlayers, boolean ignoreDisguise) {
        return null;
    }

    @Override
    public EntityPlayer getRepresentingPlayer() {
        return null;
    }

    @Override
    public boolean isDisguised() {
        return false;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {

    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        return null;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        return null;
    }

    @Override
    public long getLastComebackCall() {
        return 0;
    }

    @Override
    public int getMaxMinionCount() {
        return 0;
    }

    @Override
    public EntityLivingBase getMinionTarget() {
        return null;
    }

    @Override
    public double getTheDistanceSquared(Entity e) {
        return 0;
    }

    @Override
    public UUID getThePersistentID() {
        return null;
    }

    @Override
    public boolean isTheEntityAlive() {
        return false;
    }
}
