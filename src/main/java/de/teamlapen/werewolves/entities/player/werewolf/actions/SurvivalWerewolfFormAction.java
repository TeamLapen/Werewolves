package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModRefinements;
import de.teamlapen.werewolves.core.WerewolfSkills;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class SurvivalWerewolfFormAction extends WerewolfFormAction {

    public static final UUID CLIMBER_ID = UUID.fromString("df949628-07c5-4e9b-974f-9f005a74ab51");
    public SurvivalWerewolfFormAction() {
        super(WerewolfForm.SURVIVALIST);
        attributes.add(new Modifier(ModAttributes.bite_damage, UUID.fromString("7c2ab40d-b71a-4453-aa95-158f69c87696"), UUID.fromString("913462ef-a612-4d2a-a797-525d0535f8d2"), 1, "survival_form_claw_damage", WerewolvesConfig.BALANCE.SKILLS.survival_form_bite_damage::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MAX_HEALTH, UUID.fromString("d15ec6ad-9dc9-4ff0-ab86-acb502e7670d"), UUID.fromString("f9ee0de4-5903-452a-b42a-ab99d1e89bcf"), 0.5, "survival_form_health", WerewolvesConfig.BALANCE.SKILLS.survival_form_health::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("d45bf864-acab-4fb9-9440-0319483e7fdb"), UUID.fromString("2eff230c-b652-4d61-961b-cc992d9eec8a"), 0.7, "survival_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.survival_form_armor::get, () -> WerewolfSkills.resistance, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("ad6a329c-5ca0-4b7b-8bd5-f3f17f3fba00"), UUID.fromString("724e45dd-7454-4c6c-96e0-b485a010e5c0"), 0.7, "survival_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.survival_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("429ac45a-05e7-4102-b506-e1f1a3a6aca9"), UUID.fromString("0d027b0c-b87b-484c-b4ca-36bae0a2f9b9"), 0.6, "survival_form_armor_speed", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount::get, () -> WerewolfSkills.speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attributes.add(new Modifier(Attributes.ATTACK_DAMAGE, UUID.fromString("4e36859f-fadd-43cb-8e0d-722b7ab2cd4c"), UUID.fromString("a62d12ee-20e1-4169-a802-1eab2d0cc471"), 0.5, "survival_form_attack_damage", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_attack_damage.get() * 0.5, WerewolvesConfig.BALANCE.SKILLS.survival_form_attack_damage::get, () -> WerewolfSkills.damage, AttributeModifier.Operation.ADDITION));
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        if (super.activate(werewolf)) {
            if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.climber)) {
                werewolf.getRepresentingPlayer().getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).addTransientModifier(new AttributeModifier(CLIMBER_ID, "werewolf climber", 0.4, AttributeModifier.Operation.ADDITION));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onReActivated(IWerewolfPlayer werewolf) {
        super.onReActivated(werewolf);
        if (werewolf.getSkillHandler().isSkillEnabled(WerewolfSkills.climber)) {
            werewolf.getRepresentingPlayer().getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).addTransientModifier(new AttributeModifier(CLIMBER_ID, "werewolf climber", 0.4, AttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        super.onActivatedClient(werewolfPlayer);
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        super.onDeactivated(werewolf);
        werewolf.getRepresentingPlayer().getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get()).removeModifier(CLIMBER_ID);
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
        boolean duration1 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.werewolf_form_duration_survival_1);
        boolean duration2 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.werewolf_form_duration_survival_2);
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