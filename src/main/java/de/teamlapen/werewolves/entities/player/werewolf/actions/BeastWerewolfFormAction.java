package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModRefinements;
import de.teamlapen.werewolves.core.ModSkills;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class BeastWerewolfFormAction extends WerewolfFormAction {

    public BeastWerewolfFormAction() {
        super(WerewolfForm.BEAST);
        attributes.add(new Modifier.Builder(ModAttributes.BITE_DAMAGE, WResourceLocation.mod("beast_form_claw_damage")).withValues(WerewolvesConfig.BALANCE.SKILLS.beast_form_bite_damage, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MAX_HEALTH, WResourceLocation.mod("beast_form_health")).withDayModifier(0.5).withValues(WerewolvesConfig.BALANCE.SKILLS.beast_form_health, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR, WResourceLocation.mod("beast_form_armor")).withDayModifier(0.7).withValues(WerewolvesConfig.BALANCE.SKILLS.beast_form_armor, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR_TOUGHNESS, WResourceLocation.mod("beast_form_armor_toughness")).withDayModifier(0.7).withValues(WerewolvesConfig.BALANCE.SKILLS.beast_form_armor_toughness, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MOVEMENT_SPEED, WResourceLocation.mod("beast_form_speed_amount")).withDayModifier(0.5).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.beast_form_speed_amount.get() * 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).withSkillModifier(ModSkills.SPEED, WerewolvesConfig.BALANCE.SKILLS.beast_form_speed_amount).build());
        attributes.add(new Modifier.Builder(Attributes.ATTACK_DAMAGE, WResourceLocation.mod("beast_form_attack_damage")).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.beast_form_attack_damage.get() * 0.5, AttributeModifier.Operation.ADD_VALUE).withDayModifier(0.5).withSkillModifier(ModSkills.DAMAGE, WerewolvesConfig.BALANCE.SKILLS.beast_form_attack_damage).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_CONSUMPTION, WResourceLocation.mod("beast_form_food_consumption")).withValues(WerewolvesConfig.BALANCE.SKILLS.beast_form_food_consumption, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_GAIN, WResourceLocation.mod("beast_form_food_gain")).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.beast_form_food_gain.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.beast_form_enabled.get();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.beast_form_cooldown.get() * 20;
    }

    @Override
    public int getTimeModifier(IWerewolfPlayer werewolf) {
        int limit = super.getTimeModifier(werewolf);
        boolean duration1 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_BEAST_1.get());
        boolean duration2 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_BEAST_2.get());
        if (duration1 || duration2) {
            if (duration2) {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_beast_2.get() * 20;
            } else {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_beast_1.get() * 20;
            }
        }
        return limit;
    }

    @Override
    public boolean usesTransformationTime(IWerewolfPlayer werewolf) {
        return super.usesTransformationTime(werewolf) && !(werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_RAGE.get()) && werewolf.getActionHandler().isActionActive(ModActions.RAGE.get()));
    }

    @Override
    public void checkDayNightModifier(IWerewolfPlayer werewolfPlayer) {
        if (werewolfPlayer.getSkillHandler().isSkillEnabled(ModSkills.BEAST_RAGE.get()) && werewolfPlayer.getActionHandler().isActionActive(ModActions.RAGE.get())) {
            checkDayNightModifier(werewolfPlayer, true);
        } else {
            super.checkDayNightModifier(werewolfPlayer);
        }
    }

    @Override
    public boolean consumesWerewolfTime(IWerewolfPlayer werewolf) {
        return !(werewolf.getActionHandler().isActionActive(ModActions.RAGE.get()) && werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_RAGE.get()));
    }
}
