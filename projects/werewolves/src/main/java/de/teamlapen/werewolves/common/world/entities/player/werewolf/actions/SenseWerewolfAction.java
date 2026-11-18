package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;

import de.teamlapen.faction.api.factions.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.config.ModConfig;

public class SenseWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {

    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.sense_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer player, ActivationContext context) {
        return IActionResult.SUCCESS;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return ModConfig.balance().SKILLS.sense_duration.get() * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
    }

    @Override
    public void onReActivatedServer(IWerewolfPlayer player) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return false;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return ModConfig.balance().SKILLS.sense_cooldown.get() + ModConfig.balance().SKILLS.sense_duration.get() * 20;
    }




}
