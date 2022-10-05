package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;

public class HideNameAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public HideNameAction() {
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.hide_name_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        return true;
    }

    @Override
    public int getDuration(int i) {
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
    public boolean onUpdate(IWerewolfPlayer werewolf) {
        return !werewolf.getForm().isTransformed();
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.hide_name_cooldown.get() * 20;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer werewolf) {
        return werewolf.getForm().isTransformed();
    }
}
