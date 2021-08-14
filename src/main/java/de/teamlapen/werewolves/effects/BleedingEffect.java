package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class BleedingEffect extends WerewolvesEffect {

    public BleedingEffect() {
        super("bleeding", EffectType.HARMFUL, 0x740000);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 25 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isInvertedHealAndHarm()) {
            entityLivingBaseIn.removeEffect(ModEffects.bleeding);
            return;
        }

        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.hurt(DamageSource.MAGIC, 1.0F);
        }
    }
}
