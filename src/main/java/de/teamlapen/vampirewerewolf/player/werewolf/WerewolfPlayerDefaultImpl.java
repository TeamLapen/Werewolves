package de.teamlapen.vampirewerewolf.player.werewolf;

import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.IActionHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkillHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;
import java.util.function.Predicate;

@Deprecated
public class WerewolfPlayerDefaultImpl implements IWerewolfPlayer {

    @Override
    public EntityLivingBase getRepresentingEntity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canLeaveFaction() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IFaction getDisguisedAs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPlayableFaction<IWerewolfPlayer> getFaction() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getLevel() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxLevel() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Predicate<Entity> getNonFriendlySelector(boolean otherFactionPlayers, boolean ignoreDisguise) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EntityPlayer getRepresentingPlayer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isDisguised() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isRemote() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLevelChanged(int newLevel, int oldLevel) {
        // TODO Auto-generated method stub

    }

    @Override
    public ISkillHandler<IWerewolfPlayer> getSkillHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IActionHandler<IWerewolfPlayer> getActionHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getLastComebackCall() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxMinionCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public EntityLivingBase getMinionTarget() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getTheDistanceSquared(Entity e) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public UUID getThePersistentID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isTheEntityAlive() {
        // TODO Auto-generated method stub
        return false;
    }

}
