package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModSkills;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class HumanWerewolfFormAction extends WerewolfFormAction {

    public HumanWerewolfFormAction() {
        super(WerewolfForm.HUMAN);
        attributes.add(new Modifier.Builder(Attributes.ARMOR, WResourceLocation.mod("human_form_armor")).withDayModifier(0.7).withValues(WerewolvesConfig.BALANCE.SKILLS.human_form_armor, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR_TOUGHNESS, WResourceLocation.mod("human_form_armor_toughness")).withDayModifier(0.7).withValues(WerewolvesConfig.BALANCE.SKILLS.human_form_armor_toughness, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MOVEMENT_SPEED, WResourceLocation.mod("human_form_speed_amount")).withDayModifier(0.5).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.human_form_speed_amount.get() * 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).withSkillModifier(ModSkills.SPEED, WerewolvesConfig.BALANCE.SKILLS.human_form_speed_amount).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_CONSUMPTION, WResourceLocation.mod("human_form_food_consumption")).withValues(WerewolvesConfig.BALANCE.SKILLS.human_form_food_consumption, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_GAIN, WResourceLocation.mod("human_form_food_gain")).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.human_form_food_gain.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.human_form_enabled.get();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolfPlayer) {
        return WerewolvesConfig.BALANCE.SKILLS.human_form_cooldown.get() * 20;
    }

    @Override
    public boolean consumesWerewolfTime(IWerewolfPlayer werewolf) {
        return false;
    }
}
