package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class HowlingAction extends DefaultWerewolfAction {

    @Override
    public boolean isEnabled() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_enabled.get();
    }

    @Override
    protected boolean activate(IWerewolfPlayer werewolfPlayer) {
        PlayerEntity player = werewolfPlayer.getRepresentingPlayer();
        if (player.getActivePotionEffect(ModEffects.howling) != null) {
            return false;
        }
        AxisAlignedBB bb = new AxisAlignedBB(player.getPosition());
        bb.grow(10);
        List<LivingEntity> entities = player.getEntityWorld().getEntitiesWithinAABB(LivingEntity.class, bb);
        entities.forEach(entity -> entity.addPotionEffect(new EffectInstance(ModEffects.howling, (WerewolvesConfig.BALANCE.SKILLS.howling_duration.get() + WerewolvesConfig.BALANCE.SKILLS.howling_disabled_duration.get()) * 20, 0, true, false, true)));
        return true;
    }

    @Override
    public int getCooldown() {
        return WerewolvesConfig.BALANCE.SKILLS.howling_cooldown.get() * 20;
    }
}
