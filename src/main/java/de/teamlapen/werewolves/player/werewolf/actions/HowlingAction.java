package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.api.entity.player.IWerewolfPlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.WEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class HowlingAction extends DefaultWerewolfAction {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.howling_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        PlayerEntity player = werewolfPlayer.getRepresentingPlayer();
        if (player.getActivePotionEffect(WEffects.howling) != null) {
            return false;
        }
        AxisAlignedBB bb = new AxisAlignedBB(player.getPosition());
        bb.grow(10);
        List<LivingEntity> entities = player.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, bb);
        entities.forEach(entity -> entity.addPotionEffect(new EffectInstance(WEffects.howling, (WerewolvesConfig.BALANCE.howling_duration.get() + WerewolvesConfig.BALANCE.howling_disabled_duration.get())*20)));
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.howling_cooldown.get() * 20;
    }
}
