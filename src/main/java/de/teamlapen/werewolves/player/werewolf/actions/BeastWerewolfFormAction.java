package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class BeastWerewolfFormAction extends WerewolfFormAction {

    public BeastWerewolfFormAction() {
        super(WerewolfForm.BEAST);
        attributes.add(new Modifier(Attributes.MAX_HEALTH, UUID.fromString("5b99db11-01cf-4430-bf41-ff6adc11ccb0"), "beast_form_health", WerewolvesConfig.BALANCE.SKILLS.beast_form_health::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("0b281a87-829f-4d98-9a3b-116549cfdd57"), "beast_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.beast_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.beast_form_armor::get, WerewolfSkills.resistance, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("f47e2130-39c4-496f-8d47-572abdc03920"), "beast_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.beast_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("e9748d20-a9a5-470c-99a4-44167df71aa5"), "beast_form_speed_amount", () -> WerewolvesConfig.BALANCE.SKILLS.beast_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.beast_form_speed_amount::get, WerewolfSkills.speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
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
