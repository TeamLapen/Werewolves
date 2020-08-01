package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class SenseWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.sense_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        return true;
    }

    @Override
    public int getDuration(int level) {
        return WerewolvesConfig.BALANCE.SKILLS.sense_duration.get();
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
        List<LivingEntity> entities = player.getRepresentingPlayer().getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(player.getRepresentingPlayer().getPosition()).grow(WerewolvesConfig.BALANCE.SKILLS.sense_radius.get(), 10, WerewolvesConfig.BALANCE.SKILLS.sense_radius.get()), EntityPredicates.NOT_SPECTATING.and((entity) -> entity != player.getRepresentingPlayer()));
        WerewolvesMod.proxy.setTrackedEntities(entities);
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
        WerewolvesMod.proxy.clearTrackedEntities();
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return false;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.sense_cooldown.get() + WerewolvesConfig.BALANCE.SKILLS.sense_duration.get();
    }
}
