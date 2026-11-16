package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModActions;
import de.teamlapen.werewolves.common.core.ModAttributes;
import de.teamlapen.werewolves.common.core.ModRefinements;
import de.teamlapen.werewolves.common.core.ModSkills;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BeastWerewolfFormAction extends WerewolfFormAction {

    public BeastWerewolfFormAction() {
        super(WerewolfForm.BEAST);
        attributes.add(new Modifier.Builder(ModAttributes.BITE_DAMAGE, WIdentifier.mod("beast_form_claw_damage")).withValues(ModConfig.balance().SKILLS.beast_form_bite_damage, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MAX_HEALTH, WIdentifier.mod("beast_form_health")).withDayModifier(0.5).withValues(ModConfig.balance().SKILLS.beast_form_health, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR, WIdentifier.mod("beast_form_armor")).withDayModifier(0.7).withValues(ModConfig.balance().SKILLS.beast_form_armor, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR_TOUGHNESS, WIdentifier.mod("beast_form_armor_toughness")).withDayModifier(0.7).withValues(ModConfig.balance().SKILLS.beast_form_armor_toughness, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MOVEMENT_SPEED, WIdentifier.mod("beast_form_speed_amount")).withDayModifier(0.5).withValues(() -> ModConfig.balance().SKILLS.beast_form_speed_amount.get() * 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).withSkillModifier(ModSkills.SPEED, ModConfig.balance().SKILLS.beast_form_speed_amount).build());
        attributes.add(new Modifier.Builder(Attributes.ATTACK_DAMAGE, WIdentifier.mod("beast_form_attack_damage")).withValues(() -> ModConfig.balance().SKILLS.beast_form_attack_damage.get() * 0.5, AttributeModifier.Operation.ADD_VALUE).withDayModifier(0.5).withSkillModifier(ModSkills.DAMAGE, ModConfig.balance().SKILLS.beast_form_attack_damage).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_CONSUMPTION, WIdentifier.mod("beast_form_food_consumption")).withValues(ModConfig.balance().SKILLS.beast_form_food_consumption, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_GAIN, WIdentifier.mod("beast_form_food_gain")).withValues(() -> ModConfig.balance().SKILLS.beast_form_food_gain.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
    }

    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.beast_form_enabled.get();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return ModConfig.balance().SKILLS.beast_form_cooldown.get() * 20;
    }

    @Override
    public int getTimeModifier(IWerewolfPlayer werewolf) {
        int limit = super.getTimeModifier(werewolf);
        boolean duration1 = werewolf.getRefinementHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_BEAST_1);
        boolean duration2 = werewolf.getRefinementHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_BEAST_2);
        if (duration1 || duration2) {
            if (duration2) {
                limit += ModConfig.balance().REFINEMENTS.werewolf_form_duration_beast_2.get() * 20;
            } else {
                limit += ModConfig.balance().REFINEMENTS.werewolf_form_duration_beast_1.get() * 20;
            }
        }
        return limit;
    }

    @Override
    public boolean usesTransformationTime(IWerewolfPlayer werewolf) {
        return super.usesTransformationTime(werewolf) && !(werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_RAGE) && werewolf.getActionHandler().isActionActive(ModActions.RAGE));
    }

    @Override
    public void checkDayNightModifier(IWerewolfPlayer werewolfPlayer) {
        if (werewolfPlayer.getSkillHandler().isSkillEnabled(ModSkills.BEAST_RAGE) && werewolfPlayer.getActionHandler().isActionActive(ModActions.RAGE)) {
            checkDayNightModifier(werewolfPlayer, true);
        } else {
            super.checkDayNightModifier(werewolfPlayer);
        }
    }

    @Override
    public boolean consumesWerewolfTime(IWerewolfPlayer werewolf) {
        return !(werewolf.getActionHandler().isActionActive(ModActions.RAGE) && werewolf.getSkillHandler().isSkillEnabled(ModSkills.BEAST_RAGE));
    }
}
