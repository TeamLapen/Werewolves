package de.teamlapen.werewolves.common.world.entities.player.werewolf.actions;

import de.teamlapen.faction.common.util.RegUtil;

import de.teamlapen.faction.api.factions.actions.IActionResult;

import de.teamlapen.faction.api.factions.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.core.ModAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class RageWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer> {

    @Override
    public boolean isEnabled() {
        return ModConfig.balance().SKILLS.rage_enabled.get();
    }

    @Override
    protected IActionResult activateServer(IWerewolfPlayer werewolf, ActivationContext context) {
        applyEffects(werewolf);
        werewolf.asEntity().getAttribute(ModAttributes.BITE_DAMAGE).addPermanentModifier(new AttributeModifier(RegUtil.id(this), ModConfig.balance().SKILLS.rage_bite_damage.get(), AttributeModifier.Operation.ADD_VALUE));
        return IActionResult.SUCCESS;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return (ModConfig.balance().SKILLS.rage_duration.get()  + ModConfig.balance().SKILLS.rage_duration_level_increase.get() * werewolf.getLevel()) * 20;
    }

    @Override
    public IActionResult canBeUsedBy(IWerewolfPlayer player) {
        return (player.getForm().isTransformed()) ? IActionResult.SUCCESS : IActionResult.RESTRICTED;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        removePotionEffect(werewolf, MobEffects.STRENGTH);
        removePotionEffect(werewolf, MobEffects.SPEED);
        werewolf.asEntity().getAttribute(ModAttributes.BITE_DAMAGE).removeModifier(RegUtil.id(this));
    }

    @Override
    public void onReActivatedServer(IWerewolfPlayer iWerewolfPlayer) {

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
        return ModConfig.balance().SKILLS.rage_cooldown.get() * 20;
    }

    protected void applyEffects(IWerewolfPlayer werewolf) {
        int speedAmplifier = werewolf.getForm() == WerewolfForm.SURVIVALIST ? 1 : 0;
        int damageAmplifier = werewolf.getForm() == WerewolfForm.BEAST ? 1 : 0;
        addEffectInstance(werewolf, new MobEffectInstance(MobEffects.SPEED, 22, speedAmplifier, false, false));
        addEffectInstance(werewolf, new MobEffectInstance(MobEffects.STRENGTH, 22, damageAmplifier, false, false));
    }




}
