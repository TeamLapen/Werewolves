package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;

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
        return ModConfig.balance().SKILLS.leap_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer iWerewolfPlayer, ActivationContext context) {
        Player player = iWerewolfPlayer.asEntity();
        player.getAbilities().setFlyingSpeed(player.getAbilities().getFlyingSpeed() + 0.05F);
        return IActionResult.SUCCESS;
    }

    @Override
    public int getCooldown(IWerewolfPlayer player) {
        return player.getRefinementHandler().isRefinementEquipped(ModRefinements.NO_LEAP_COOLDOWN) ? 0 : ModConfig.balance().SKILLS.leap_cooldown.get() * 20;
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
    public void onReActivatedServer(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer iWerewolfPlayer) {
        return false;
    }


}
