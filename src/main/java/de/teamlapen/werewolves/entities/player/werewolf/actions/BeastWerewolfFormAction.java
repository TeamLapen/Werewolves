package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.core.ModRefinements;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class BeastWerewolfFormAction extends WerewolfFormAction {

    public BeastWerewolfFormAction() {
        super(WerewolfForm.BEAST);
        attributes.add(new Modifier(ModAttributes.bite_damage, UUID.fromString("128d73ba-f99a-452a-96fc-494c317ee7e3"), UUID.fromString("4e32baca-7752-4323-8942-76891822e836"), 1, "beast_form_claw_damage", WerewolvesConfig.BALANCE.SKILLS.beast_form_bite_damage::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MAX_HEALTH, UUID.fromString("5b99db11-01cf-4430-bf41-ff6adc11ccb0"), UUID.fromString("d41d9079-15a0-414d-847e-8708b9226be5"), 0.5, "beast_form_health", WerewolvesConfig.BALANCE.SKILLS.beast_form_health::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("0b281a87-829f-4d98-9a3b-116549cfdd57"), UUID.fromString("faebe9f8-d133-44a7-b872-83560ca4d927"), 0.7, "beast_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.beast_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.beast_form_armor::get, WerewolfSkills.resistance, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("f47e2130-39c4-496f-8d47-572abdc03920"), UUID.fromString("b8eb47be-1e10-41ce-8ff6-2a5c4aa4f408"), 0.7, "beast_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.beast_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("e9748d20-a9a5-470c-99a4-44167df71aa5"), UUID.fromString("cf0d3fce-6fb3-4274-842b-bced59637eaf"), 0.5, "beast_form_speed_amount", () -> WerewolvesConfig.BALANCE.SKILLS.beast_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.beast_form_speed_amount::get, WerewolfSkills.speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attributes.add(new Modifier(Attributes.ATTACK_DAMAGE, UUID.fromString("2792aac3-5ae1-47d9-8ba6-0b473b6b666a"), UUID.fromString("07a8e8e1-ff43-41f9-8053-c947482ef6c2"), 0.5, "beast_form_attack_damage", () -> WerewolvesConfig.BALANCE.SKILLS.beast_form_attack_damage.get() * 0.5, WerewolvesConfig.BALANCE.SKILLS.beast_form_attack_damage::get, WerewolfSkills.damage, AttributeModifier.Operation.ADDITION));
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
        boolean duration1 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.werewolf_form_duration_beast_1);
        boolean duration2 = werewolf.getSkillHandler().isRefinementEquipped(ModRefinements.werewolf_form_duration_beast_2);
        if (duration1 || duration2) {
            if (duration2) {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_beast_2.get() * 20;
            } else {
                limit += WerewolvesConfig.BALANCE.REFINEMENTS.werewolf_form_duration_beast_1.get() * 20;
            }
        }
        return limit;
    }
}
