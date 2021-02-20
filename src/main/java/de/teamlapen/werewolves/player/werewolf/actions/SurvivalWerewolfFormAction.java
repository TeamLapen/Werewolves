package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.commons.lang3.tuple.Triple;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static de.teamlapen.werewolves.player.ModPlayerEventHandler.CLAWS;

public class SurvivalWerewolfFormAction extends WerewolfFormAction {

    public SurvivalWerewolfFormAction() {
        super(WerewolfForm.SURVIVALIST);
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("d45bf864-acab-4fb9-9440-0319483e7fdb"), "survival_form_armor", WerewolvesConfig.BALANCE.SKILLS.survival_form_armor::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("ad6a329c-5ca0-4b7b-8bd5-f3f17f3fba00"), "survival_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.survival_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("429ac45a-05e7-4102-b506-e1f1a3a6aca9"), "survival_form_armor_speed", WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount::get, AttributeModifier.Operation.MULTIPLY_TOTAL));
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