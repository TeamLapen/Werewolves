package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;

public class SenseWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

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
        return WerewolvesConfig.BALANCE.SKILLS.sense_duration.get() * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
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
        return WerewolvesConfig.BALANCE.SKILLS.sense_cooldown.get() + WerewolvesConfig.BALANCE.SKILLS.sense_duration.get() * 20;
    }
}
