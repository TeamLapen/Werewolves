package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;

public class SixthSenseAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.sixth_sense_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.sixth_sense_cooldown.get() * 20;
    }

    @Override
    public int getDuration(int i) {
        return WerewolvesConfig.BALANCE.SKILLS.sixth_sense_duration.get();
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer iWerewolfPlayer) {

    }

    @Override
    public void onDeactivated(IWerewolfPlayer iWerewolfPlayer) {

    }

    @Override
    public void onReActivated(IWerewolfPlayer iWerewolfPlayer) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer iWerewolfPlayer) {
        return false;
    }
}
