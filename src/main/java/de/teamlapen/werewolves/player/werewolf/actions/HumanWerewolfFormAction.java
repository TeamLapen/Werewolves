package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class HumanWerewolfFormAction extends WerewolfFormAction {

    public HumanWerewolfFormAction() {
        super(WerewolfForm.HUMAN);
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("9fe8cf0f-e3d1-47f6-ba69-db13920640e1"), "human_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.human_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.human_form_armor::get, WerewolfSkills.resistance, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("ae1e52d0-5982-4657-8260-345460e6e02d"), "human_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.human_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("f30e65e0-69b0-430c-ae94-8086a7870e63"), "human_form_speed_amount", () -> WerewolvesConfig.BALANCE.SKILLS.human_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.human_form_speed_amount::get, WerewolfSkills.speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
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
