package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.WerewolfForm;

public class HumanWerewolfFormAction extends WerewolfFormAction {

    public HumanWerewolfFormAction() {
        super(WerewolfForm.HUMAN);
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.human_form_enabled.get();
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.human_form_cooldown.get() * 20;
    }
}
