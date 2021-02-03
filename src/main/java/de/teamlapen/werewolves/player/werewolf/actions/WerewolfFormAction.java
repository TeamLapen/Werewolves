package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

import static de.teamlapen.werewolves.player.ModPlayerEventHandler.CLAWS;

public class WerewolfFormAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    public static final UUID ARMOR = UUID.fromString("0b281a87-829f-4d98-9a3b-116549cfdd57");
    public static final UUID ARMOR_TOUGHNESS = UUID.fromString("f47e2130-39c4-496f-8d47-572abdc03920");
    public static final UUID MOVEMENT_SPEED = UUID.fromString("e9748d20-a9a5-470c-99a4-44167df71aa5");
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @return how much percentage is left
     */
    public static float getDurationPercentage(IWerewolfPlayer player) {
        long durationMax = WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
        return 1 - (float) ((WerewolfPlayer) player).getSpecialAttributes().werewolfTime / durationMax;
    }

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.werewolf_form_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).activateWerewolfForm();
        this.applyModifier(player.getRepresentingPlayer(), true);
        player.getRepresentingPlayer().recalculateSize();
        return true;
    }

    @Override
    public int getDuration(int level) {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().werewolfForm = true;
        player.getRepresentingPlayer().recalculateSize();
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
        if(player.getRepresentingPlayer().world.isRemote()){
            ((WerewolfPlayer) player).getSpecialAttributes().werewolfForm = false;
        }else {
            ((WerewolfPlayer) player).deactivateWerewolfForm();
        }
        this.applyModifier(player.getRepresentingPlayer(), false);
        player.getRepresentingPlayer().recalculateSize();
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {
        ((WerewolfPlayer) player).getSpecialAttributes().werewolfForm = true;
        this.applyModifier(player.getRepresentingPlayer(), true);
    }

    private void applyModifier(PlayerEntity player, boolean activate) {
        ModifiableAttributeInstance armor = player.getAttribute(Attributes.ARMOR);
        ModifiableAttributeInstance armor_toughness = player.getAttribute(Attributes.ARMOR_TOUGHNESS);
        ModifiableAttributeInstance movement_speed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        ModifiableAttributeInstance attack_damage = player.getAttribute(Attributes.ATTACK_DAMAGE);
        if (armor == null || armor_toughness == null) {
            LOGGER.warn("Could not apply attribute modifier to entity: {}", player.getName());
            return;
        }
        if (activate) {
            if (armor.getModifier(ARMOR) == null) {
                armor.applyPersistentModifier(new AttributeModifier(ARMOR, "werewolf_form_armor", WerewolvesConfig.BALANCE.SKILLS.werewolf_form_armor.get(), AttributeModifier.Operation.ADDITION));
            }
            if (armor_toughness.getModifier(ARMOR_TOUGHNESS) == null) {
                armor_toughness.applyPersistentModifier(new AttributeModifier(ARMOR_TOUGHNESS, "werewolf_form_armor_toughness", WerewolvesConfig.BALANCE.SKILLS.werewolf_form_armor_toughness.get(), AttributeModifier.Operation.ADDITION));
            }
            if (movement_speed.getModifier(MOVEMENT_SPEED) == null) {
                movement_speed.applyPersistentModifier(new AttributeModifier(MOVEMENT_SPEED, "werewolf_form_movement_speed", WerewolvesConfig.BALANCE.SKILLS.werewolf_form_speed_amount.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
            if (player.getHeldItemMainhand().isEmpty()) { //see ModPlayerEventHandler#onEquipmentChange
                if (attack_damage.getModifier(CLAWS) == null) {
                    double damage = WerewolvesConfig.BALANCE.PLAYER.werewolf_claw_damage.get();
                    if (WerewolfPlayer.get(player).getSkillHandler().isSkillEnabled(WerewolfSkills.better_claws)) {
                        damage += WerewolvesConfig.BALANCE.SKILLS.better_claw_damage.get();
                    }
                    attack_damage.applyPersistentModifier(new AttributeModifier(CLAWS, "werewolf_claws", damage, AttributeModifier.Operation.ADDITION));
                }
            }
        } else {
            attack_damage.removeModifier(CLAWS);
            armor.removeModifier(ARMOR);
            armor_toughness.removeModifier(ARMOR_TOUGHNESS);
            movement_speed.removeModifier(MOVEMENT_SPEED);
        }
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        if (Helper.isFullMoon(player.getRepresentingPlayer().getEntityWorld()) && player.getActionHandler().isActionActive(WerewolfActions.werewolf_form)) {
            return false;
        }
        return player.getRepresentingPlayer().world.getBiome(player.getRepresentingEntity().getPosition()) == ModBiomes.werewolf_heaven || (getDurationPercentage(player) > 0.3) || player.getActionHandler().isActionActive(this);
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        if (Helper.isNight(player.getRepresentingPlayer().getEntityWorld())) {
            return false;
        }
        return ++((WerewolfPlayer) player).getSpecialAttributes().werewolfTime > WerewolvesConfig.BALANCE.SKILLS.werewolf_form_time_limit.get() * 20;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.werewolf_form_cooldown.get() * 20;
    }
}
