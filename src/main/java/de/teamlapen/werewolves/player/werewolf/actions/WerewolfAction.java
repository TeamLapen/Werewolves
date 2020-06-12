package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class WerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final UUID ARMOR = UUID.fromString("0b281a87-829f-4d98-9a3b-116549cfdd57");
    public static final UUID ARMOR_TOUGHNESS = UUID.fromString("f47e2130-39c4-496f-8d47-572abdc03920");

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.werewolf_form_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).activateWerewolfForm();
        this.applyModifier(player.getRepresentingPlayer(),true);
        return true;
    }

    @Override
    public int getDuration(int level) {
        return MathHelper.clamp(WerewolvesConfig.BALANCE.werewolf_form_duration.get(), 10, Integer.MAX_VALUE / 20 - 1) * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).getSpecialAttributes().trueForm = true;
    }

    @Override
    public void onDeactivated(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).deactivateWerewolfForm();
        this.applyModifier(player.getRepresentingPlayer(),false);
    }

    @Override
    public void onReActivated(IWerewolfPlayer player) {
        ((WerewolfPlayer)player).getSpecialAttributes().trueForm = true;
        this.applyModifier(player.getRepresentingPlayer(),true);
    }

    private void applyModifier(PlayerEntity player, boolean activate) {
        IAttributeInstance armor = player.getAttributes().getAttributeInstance(SharedMonsterAttributes.ARMOR);
        IAttributeInstance armor_toughness = player.getAttributes().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS);
        if(armor == null|| armor_toughness == null) {
            LOGGER.warn("Could not apply attribute modifier to entity: {}",player.getName());
            return;
        }
        if(activate) {
            if(armor.getModifier(ARMOR) == null){
                armor.applyModifier(new AttributeModifier(ARMOR,"werewolf_form_armor",WerewolvesConfig.BALANCE.werewolf_form_armor.get(), AttributeModifier.Operation.ADDITION));
            }
            if(armor_toughness.getModifier(ARMOR_TOUGHNESS) == null){
                armor_toughness.applyModifier(new AttributeModifier(ARMOR_TOUGHNESS,"werewolf_form_armor_toughness",WerewolvesConfig.BALANCE.werewolf_form_armor.get(), AttributeModifier.Operation.ADDITION));
            }
        }else {
            armor.removeModifier(ARMOR);
            armor_toughness.removeModifier(ARMOR_TOUGHNESS);
        }
    }

    @Override
    public boolean onUpdate(IWerewolfPlayer player) {
        return false;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.werewolf_form_cooldown.get() * 20 + 1;
    }
}
