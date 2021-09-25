package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.player.actions.ILastingAction;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModAttributes;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import java.util.UUID;

public class RageWerewolfAction extends DefaultWerewolfAction implements ILastingAction<IWerewolfPlayer>, IActionCooldownMenu{

    private static final UUID BITE_MODIFIER = UUID.fromString("0ae51804-eaf4-456e-b4ff-24ed326557f4");

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.rage_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolf) {
        werewolf.getRepresentingPlayer().addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, this.getDuration(werewolf.getLevel()), 1, false, false));
        werewolf.getRepresentingPlayer().addEffect(new EffectInstance(Effects.DAMAGE_BOOST, this.getDuration(werewolf.getLevel()), 0, false, false));
        werewolf.getRepresentingPlayer().getAttribute(ModAttributes.bite_damage).addPermanentModifier(new AttributeModifier(BITE_MODIFIER, "rage_bite_modifier", 2, AttributeModifier.Operation.ADDITION));
        return true;
    }

    @Override
    public int getDuration(int i) {
        return WerewolvesConfig.BALANCE.SKILLS.rage_duration.get() * 20;
    }

    @Override
    public void onActivatedClient(IWerewolfPlayer iWerewolfPlayer) {
    }

    @Override
    public void onDeactivated(IWerewolfPlayer werewolf) {
        werewolf.getRepresentingPlayer().removeEffect(Effects.DAMAGE_BOOST);
        werewolf.getRepresentingPlayer().removeEffect(Effects.MOVEMENT_SPEED);
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
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.rage_cooldown.get() * 20;
    }


}
