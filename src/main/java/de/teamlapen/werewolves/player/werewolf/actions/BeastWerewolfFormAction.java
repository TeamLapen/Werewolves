package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.WerewolfForm;

public class BeastWerewolfFormAction extends WerewolfFormAction {

    public BeastWerewolfFormAction() {
        super(WerewolfForm.BEAST);
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.beast_form_enabled.get();
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.beast_form_cooldown.get() * 20;
    }
}
