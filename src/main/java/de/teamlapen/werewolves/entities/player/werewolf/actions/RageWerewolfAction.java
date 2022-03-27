package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.player.action.IActionCooldownMenu;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class RageWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu {

    private static final UUID BITE_MODIFIER = UUID.fromString("0ae51804-eaf4-456e-b4ff-24ed326557f4");

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.rage_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        addEffectInstance(werewolf, new MobEffectInstance(MobEffects.MOVEMENT_SPEED, this.getDuration(werewolf), 1, false, false));
        addEffectInstance(werewolf, new MobEffectInstance(MobEffects.DAMAGE_BOOST, this.getDuration(werewolf), 0, false, false));
        werewolf.getRepresentingPlayer().getAttribute(ModAttributes.bite_damage).addPermanentModifier(new AttributeModifier(BITE_MODIFIER, "rage_bite_modifier", WerewolvesConfig.BALANCE.SKILLS.rage_bite_damage.get(), AttributeModifier.Operation.ADDITION));
        return true;
    }

    @Override
    public int getDuration(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.rage_duration.get() * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        removePotionEffect(werewolf, MobEffects.DAMAGE_BOOST);
        removePotionEffect(werewolf, MobEffects.MOVEMENT_SPEED);
        werewolf.getRepresentingPlayer().getAttribute(ModAttributes.bite_damage).removeModifier(BITE_MODIFIER);
    }

    @Override
    public void onReActivated(IWerewolfPlayer iWerewolfPlayer) {

    }

    @Override
    public boolean onUpdate(IWerewolfPlayer iWerewolfPlayer) {
        return false;
    }

    @Override
    public int getCooldown(IWerewolfPlayer werewolf) {
        return WerewolvesConfig.BALANCE.SKILLS.rage_cooldown.get() * 20;
    }


}
