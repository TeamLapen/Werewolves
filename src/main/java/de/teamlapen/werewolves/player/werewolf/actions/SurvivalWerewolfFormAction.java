package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.WerewolfForm;

public class SurvivalWerewolfFormAction extends WerewolfFormAction {

    public SurvivalWerewolfFormAction() {
        super(WerewolfForm.SURVIVALIST);
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.survival_form_enabled.get();
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.survival_form_cooldown.get() * 20;
    }
}
