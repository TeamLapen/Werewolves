package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModRefinements;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.entity.player.Player;

public class LeapAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return ModConfig.BALANCE.SKILLS.leap_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer iWerewolfPlayer, ActivationContext context) {
        Player player = iWerewolfPlayer.asEntity();
        player.getAbilities().setFlyingSpeed(player.getAbilities().getFlyingSpeed() + 0.05F);
        return true;
    }

    @Override
    public int getCooldown(IWerewolfPlayer player) {
        return player.getSkillHandler().isRefinementEquipped(ModRefinements.NO_LEAP_COOLDOWN.get()) ? 0 : ModConfig.BALANCE.SKILLS.leap_cooldown.get() * 20;
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
        ((WerewolfPlayer) iWerewolfPlayer).getSpecialAttributes().leap = false;
        iWerewolfPlayer.asEntity().getAbilities().setFlyingSpeed(iWerewolfPlayer.asEntity().getAbilities().getFlyingSpeed() - 0.05F);
    }

    @Override
    public void onReActivated(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer iWerewolfPlayer) {
        return false;
    }

    @Override
    public boolean showHudCooldown(Player player) {
        return true;
    }
}
