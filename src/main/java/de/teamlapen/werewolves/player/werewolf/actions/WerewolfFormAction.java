package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class WerewolfFormAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public static final UUID ARMOR = UUID.fromString("0b281a87-829f-4d98-9a3b-116549cfdd57");
    public static final UUID ARMOR_TOUGHNESS = UUID.fromString("f47e2130-39c4-496f-8d47-572abdc03920");
    public static final UUID MOVEMENT_SPEED = UUID.fromString("e9748d20-a9a5-470c-99a4-44167df71aa5");
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @return how much percentage is left
     */
    public static float getDurationPercentage(IWerewolfPlayer player) {
        long durationMax = WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.time_limit.get() *20/*+ (player.getSkillHandler().isSkillEnabled(WerewolfSkills.werewolf_form_more_time) ? WerewolvesConfig.BALANCE.SKILLS.LONGER_FORM.time.get() * 20 : 0)*/;
        return 1 - (float)((WerewolfPlayer)player).getSpecialAttributes().werewolfTime/durationMax;
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.enabled.get();
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        return (player.getRepresentingPlayer().getEntityWorld().getDayTime() > 12000 && getDurationPercentage(player) > 0.3) || player.getActionHandler().isActionActive(this);
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).activateWerewolfForm();
        this.applyModifier(player.getRepresentingPlayer(), true);
        return true;
    }

    @Override
    public int getDuration(int level) {
        return MathHelper.clamp(WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.duration.get(), 10, Integer.MAX_VALUE / 20 - 1) * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().werewolfForm = true;
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
        if(player.getRepresentingPlayer().world.isRemote()){
            ((WerewolfPlayer) player).getSpecialAttributes().werewolfForm = false;
        }else {
            ((WerewolfPlayer) player).deactivateWerewolfForm();
        }
        this.applyModifier(player.getRepresentingPlayer(), false);
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().werewolfForm = true;
        this.applyModifier(player.getRepresentingPlayer(), true);
    }

    private void applyModifier(PlayerEntity player, boolean activate) {
        IAttributeInstance armor = player.getAttributes().getAttributeInstance(SharedMonsterAttributes.ARMOR);
        IAttributeInstance armor_toughness = player.getAttributes().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS);
        IAttributeInstance movement_speed = player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        if (armor == null || armor_toughness == null) {
            LOGGER.warn("Could not apply attribute modifier to entity: {}", player.getName());
            return;
        }
        if (activate) {
            if (armor.getModifier(ARMOR) == null) {
                armor.applyModifier(new AttributeModifier(ARMOR, "werewolf_form_armor", WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.armor.get(), AttributeModifier.Operation.ADDITION));
            }
            if (armor_toughness.getModifier(ARMOR_TOUGHNESS) == null) {
                armor_toughness.applyModifier(new AttributeModifier(ARMOR_TOUGHNESS, "werewolf_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.armor.get(), AttributeModifier.Operation.ADDITION));
            }
            if (movement_speed.getModifier(MOVEMENT_SPEED) == null) {
                movement_speed.applyModifier(new AttributeModifier(MOVEMENT_SPEED, "werewolf_form_movement_speed", WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.speed_amount.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        } else {
            armor.removeModifier(ARMOR);
            armor_toughness.removeModifier(ARMOR_TOUGHNESS);
            movement_speed.removeModifier(MOVEMENT_SPEED);
        }
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return ++((WerewolfPlayer) player).getSpecialAttributes().werewolfTime > WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.time_limit.get() *20/*+ (player.getSkillHandler().isSkillEnabled(WerewolfSkills.werewolf_form_more_time) ? WerewolvesConfig.BALANCE.SKILLS.LONGER_FORM.time.get() * 20 : 0)*/;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.WEREWOLFFORM.cooldown.get() * 20;
    }
}
