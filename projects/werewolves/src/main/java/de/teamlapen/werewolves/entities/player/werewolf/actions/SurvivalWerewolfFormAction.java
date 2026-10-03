package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModRefinements;
import de.teamlapen.werewolves.core.ModSkills;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.UUID;

public class SurvivalWerewolfFormAction extends WerewolfFormAction {

    public static final UUID CLIMBER_ID = UUID.fromString("df949628-07c5-4e9b-974f-9f005a74ab51");
    public SurvivalWerewolfFormAction() {
        super(WerewolfForm.SURVIVALIST);
        attributes.add(new Modifier.Builder(ModAttributes.BITE_DAMAGE, WResourceLocation.mod("survival_form_claw_damage")).withValues(WerewolvesConfig.BALANCE.SKILLS.survival_form_bite_damage, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MAX_HEALTH, WResourceLocation.mod("survival_form_health")).withDayModifier(0.5).withValues(WerewolvesConfig.BALANCE.SKILLS.survival_form_health, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR, WResourceLocation.mod("survival_form_armor")).withDayModifier(0.7).withValues(WerewolvesConfig.BALANCE.SKILLS.survival_form_armor, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.ARMOR_TOUGHNESS, WResourceLocation.mod("survival_form_armor_toughness")).withDayModifier(0.7).withValues(WerewolvesConfig.BALANCE.SKILLS.survival_form_armor_toughness, AttributeModifier.Operation.ADD_VALUE).build());
        attributes.add(new Modifier.Builder(Attributes.MOVEMENT_SPEED, WResourceLocation.mod("survival_form_armor_speed")).withDayModifier(0.6).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount.get() * 0.8, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).withSkillModifier(ModSkills.SPEED, WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount).build());
        attributes.add(new Modifier.Builder(Attributes.ATTACK_DAMAGE, WResourceLocation.mod("survival_form_attack_damage")).withDayModifier(0.5).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.survival_form_attack_damage.get() * 0.5, AttributeModifier.Operation.ADD_VALUE).withSkillModifier(ModSkills.DAMAGE, WerewolvesConfig.BALANCE.SKILLS.survival_form_attack_damage).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_CONSUMPTION, WResourceLocation.mod("survival_form_food_consumption")).withValues(WerewolvesConfig.BALANCE.SKILLS.survival_form_food_consumption, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).withSkillModifier(ModSkills.EFFICIENT_DIET, () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_food_consumption.get() - WerewolvesConfig.BALANCE.SKILLS.efficient_diet_food_consumption.get()).build());
        attributes.add(new Modifier.Builder(ModAttributes.FOOD_GAIN, WResourceLocation.mod("survival_form_food_gain")).withValues(() -> WerewolvesConfig.BALANCE.SKILLS.survival_form_food_gain.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).build());
    }

    public static void climberSkillEnabled(IWerewolfPlayer werewolf) {
        if (werewolf.getActionHandler().isActionActive(ModActions.SURVIVAL_FORM.get())) {
            checkStepHeight(werewolf, true);
        }
    }

    public static void climberSkillDisabled(IWerewolfPlayer werewolf) {
        if (werewolf.getActionHandler().isActionActive(ModActions.SURVIVAL_FORM.get())) {
            checkStepHeight(werewolf, false);
        }
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf, ActivationContext context) {
        if (super.activate(werewolf, context)) {
            checkStepHeight(werewolf, werewolf.getSkillHandler().isSkillEnabled(ModSkills.CLIMBER.get()));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        super.onReActivated(werewolf);
        checkStepHeight(werewolf, werewolf.getSkillHandler().isSkillEnabled(ModSkills.CLIMBER.get()));
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        super.onActivatedClient(werewolfPlayer);
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        super.onDeactivated(werewolf);
        checkStepHeight(werewolf, false);
    }

    public static void checkStepHeight(IWerewolfPlayer werewolf, boolean active) {
        AttributeInstance attribute = werewolf.asEntity().getAttribute(Attributes.STEP_HEIGHT);
        ResourceLocation id = ModActions.SURVIVAL_FORM.getId();
        if (active) {
            attribute.removeModifier(id);
            attribute.addTransientModifier(new AttributeModifier(id, 0.4, AttributeModifier.Operation.ADD_VALUE));
        } else {
            attribute.removeModifier(id);
        }
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.survival_form_enabled.get();
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.survival_form_cooldown.get() * 20;
    }

    @Override
    public int getTimeModifier(IWerewolfPlayer werewolf) {
        int limit = super.getTimeModifier(werewolf);
        boolean duration1 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_1.get());
        boolean duration2 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.WEREWOLF_FORM_DURATION_SURVIVAL_2.get());
        if (duration1 || duration2) {
            if (duration2) {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_survival_2.get() * 20;
            } else {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_survival_1.get() * 20;
            }
        }
        return limit;
    }
}