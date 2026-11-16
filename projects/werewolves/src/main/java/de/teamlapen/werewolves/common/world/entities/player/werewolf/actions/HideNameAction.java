package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.api.factions.actions.IActionResult;

import de.teamlapen.faction.api.factions.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.config.ModConfig;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HideNameAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public HideNameAction() {
    }

    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.hide_name_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer werewolf, ActivationContext context) {
        return IActionResult.SUCCESS;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return ModConfig.balance().SKILLS.hide_name_duration.get();
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolf) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
    }

    @Override
    public void onReActivatedServer(IWerewolfPlayer werewolf) {
    }

    @Override
    public boolean onUpdate(@NotNull IWerewolfPlayer werewolf) {
        return !werewolf.getForm().isTransformed();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return ModConfig.balance().SKILLS.hide_name_cooldown.get() * 20;
    }

    @Override
    public IActionResult canBeUsedBy(@NotNull IWerewolfPlayer werewolf) {
        return (werewolf.getForm().isTransformed()) ? IActionResult.SUCCESS : IActionResult.RESTRICTED;
    }




}
