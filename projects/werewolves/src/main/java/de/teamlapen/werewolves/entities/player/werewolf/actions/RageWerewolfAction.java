package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class RageWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.rage_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf, ActivationContext context) {
        applyEffects(werewolf);
        werewolf.asEntity().getAttribute(ModAttributes.BITE_DAMAGE).addPermanentModifier(new AttributeModifier(RegUtil.id(this), WerewolvesConfig.BALANCE.SKILLS.rage_bite_damage.get(), AttributeModifier.Operation.ADD_VALUE));
        return true;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return (WerewolvesConfig.BALANCE.SKILLS.rage_duration.get()  + WerewolvesConfig.BALANCE.SKILLS.rage_duration_level_increase.get() * werewolf.getLevel()) * 20;
    }

    @Override
    public boolean canBeUsedBy(IWerewolfPlayer player) {
        return player.getForm().isTransformed();
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        removePotionEffect(werewolf, MobEffects.DAMAGE_BOOST);
        removePotionEffect(werewolf, MobEffects.MOVEMENT_SPEED);
        werewolf.asEntity().getAttribute(ModAttributes.BITE_DAMAGE).removeModifier(RegUtil.id(this));
    }

    @Override
    public void onReActivated(IWerewolfPlayer iWerewolfPlayer) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer iWerewolfPlayer) {
        if (!iWerewolfPlayer.isRemote() && iWerewolfPlayer.asEntity().tickCount % 20 == 0) {
            applyEffects(iWerewolfPlayer);
        }
        return false;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.rage_cooldown.get() * 20;
    }

    protected void applyEffects(IWerewolfPlayer werewolf) {
        int speedAmplifier = werewolf.getForm() == WerewolfForm.SURVIVALIST ? 1 : 0;
        int damageAmplifier = werewolf.getForm() == WerewolfForm.BEAST ? 1 : 0;
        addEffectInstance(werewolf, new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 22, speedAmplifier, false, false));
        addEffectInstance(werewolf, new MobEffectInstance(MobEffects.DAMAGE_BOOST, 22, damageAmplifier, false, false));
    }

    @Override
    public boolean showHudCooldown(Player player) {
        return true;
    }

    @Override
    public boolean showHudDuration(Player player) {
        return true;
    }
}
