package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HideNameAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public HideNameAction() {
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.hide_name_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf, ActivationContext context) {
        return true;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.hide_name_duration.get();
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolf) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
    }

    @Override
    public boolean onUpdate(@NotNull IWerewolfPlayer werewolf) {
        return !werewolf.getForm().isTransformed();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.hide_name_cooldown.get() * 20;
    }

    @Override
    public boolean canBeUsedBy(@NotNull IWerewolfPlayer werewolf) {
        return werewolf.getForm().isTransformed();
    }

    @Override
    public boolean showHudCooldown(Player player) {
        return true;
    }

    @Override
    public boolean showHudDuration(Player player) {
        return true;
    }
}
