package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.player.PlayerEntity;

public class LeapAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.leap_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer) {
        ((WerewolfPlayer) iWerewolfPlayer).getSpecialAttributes().leap = false;
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.leap_cooldown.get() * 20;
    }

    @Override
    public boolean showInSelectAction(PlayerEntity player) {
        return false;
    }

    @Override
    public int getDuration(int i) {
        return 1000000;
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
        iWerewolfPlayer.getRepresentingPlayer().flyingSpeed *= 2;
        return false;
    }
}
