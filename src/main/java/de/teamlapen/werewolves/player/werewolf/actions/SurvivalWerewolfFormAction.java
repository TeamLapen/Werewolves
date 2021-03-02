package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SurvivalWerewolfFormAction extends WerewolfFormAction {

    public SurvivalWerewolfFormAction() {
        super(WerewolfForm.SURVIVALIST);
        attributes.add(new Modifier(Attributes.ARMOR, UUID.fromString("d45bf864-acab-4fb9-9440-0319483e7fdb"), "survival_form_armor", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_armor.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.survival_form_armor::get, WerewolfSkills.resistance, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.ARMOR_TOUGHNESS, UUID.fromString("ad6a329c-5ca0-4b7b-8bd5-f3f17f3fba00"), "survival_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.survival_form_armor_toughness::get, AttributeModifier.Operation.ADDITION));
        attributes.add(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString("429ac45a-05e7-4102-b506-e1f1a3a6aca9"), "survival_form_armor_speed", () -> WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount.get() * 0.8, WerewolvesConfig.BALANCE.SKILLS.survival_form_speed_amount::get, WerewolfSkills.speed, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        if (super.activate(werewolfPlayer)) {
            werewolfPlayer.getRepresentingPlayer().stepHeight = 1.0f;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer werewolfPlayer) {
        super.onActivatedClient(werewolfPlayer);
        werewolfPlayer.getRepresentingPlayer().stepHeight = 1.0f;
        WerewolvesMod.proxy.toggleStepHeight(true);
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolfPlayer) {
        super.onDeactivated(werewolfPlayer);
        werewolfPlayer.getRepresentingPlayer().stepHeight = 0.6f;
        WerewolvesMod.proxy.toggleStepHeight(false);
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