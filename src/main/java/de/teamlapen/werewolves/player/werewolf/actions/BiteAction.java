package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;

public class BiteAction extends DefaultWerewolfAction {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.BITE.enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).biteEntity(((WerewolfPlayer)player).getSpecialAttributes().target);
        ((WerewolfPlayer)player).getSpecialAttributes().target = 0;
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.BITE.cooldown.get() * 20;
    }

    @Override
    public boolean showInSelectAction() {
        return false;
    }
}
