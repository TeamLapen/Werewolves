package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.entity.player.PlayerEntity;

public class LeapAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        return true;
    }

    @Override
    public int getCooldown() {
        return 4 * 20;
    }

    @Override
    public boolean showInSelectAction(PlayerEntity player) {
        return false;
    }

    @Override
    public int getDuration(int i) {
        return 100000;
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
        iWerewolfPlayer.getRepresentingPlayer().jumpMovementFactor *= 3;
        return false;
    }
}
