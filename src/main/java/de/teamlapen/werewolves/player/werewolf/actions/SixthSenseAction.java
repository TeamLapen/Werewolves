package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.player.IWerewolfPlayer;

public class SixthSenseAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    @Override
    public boolean isEnabled() {
        return true; //TODO config
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        return true;
    }

    @Override
    public int getCooldown() {
        return 30 * 20; //TODO config
    }

    @Override
    public int getDuration(int i) {
        return 1000000; //TODO config
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
