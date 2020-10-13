package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.player.IWerewolfPlayer;

public class HideNameAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public HideNameAction() {
    }

    @Override
    public boolean isEnabled() {
        return true; //TODO Config
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        return true;
    }

    @Override
    public int getDuration(int i) {
        return 100000;//TODO Config
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

    @Override
    public int getCooldown() {
        return 1; //TODO Config
    }
}
