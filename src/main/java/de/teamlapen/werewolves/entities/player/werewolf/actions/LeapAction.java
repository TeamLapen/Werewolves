package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModRefinements;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.entity.player.Player;

public class LeapAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.leap_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer, ActivationContext context) {
        ((WerewolfPlayer) iWerewolfPlayer).getSpecialAttributes().leap = false;
        return true;
    }

    @Override
    public int getCooldown(IWerewolfPlayer player) {
        return player.getSkillHandler().isRefinementEquipped(ModRefinements.NO_LEAP_COOLDOWN.get()) ? 0 : WerewolvesConfig.BALANCE.SKILLS.leap_cooldown.get() * 20;
    }

    @Override
    public boolean showInSelectAction(Player player) {
        return false;
    }

    @Override
    public int getDuration(IWerewolfPlayer iWerewolfPlayer) {
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
