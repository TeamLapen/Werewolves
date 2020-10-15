package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.player.PlayerEntity;

public class LeapAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        ((WerewolfPlayer) iWerewolfPlayer).getSpecialAttributes().leap = false;
        return true;
    }

    @Override
    public int getCooldown() {
        return 4 * 20; //TODO Config
    }

    @Override
    public boolean showInSelectAction(PlayerEntity player) {
        return false;
    }

    @Override
    public int getDuration(int i) {
        return 1000000; //TODO Config
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
