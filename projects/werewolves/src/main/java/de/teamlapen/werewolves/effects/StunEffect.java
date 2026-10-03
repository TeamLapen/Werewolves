package de.teamlapen.werewolves.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class StunEffect extends MobEffect {

    public StunEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFA262);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        pLivingEntity.setDeltaMovement(0, Math.min(0, pLivingEntity.getDeltaMovement().y()), 0);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
