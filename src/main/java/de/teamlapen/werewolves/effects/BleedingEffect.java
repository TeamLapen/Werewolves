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
    public boolean isReady(int duration, int amplifier) {
        int j = 25 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isEntityUndead()) {
            entityLivingBaseIn.removePotionEffect(ModEffects.bleeding);
            return;
        }

        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);
        }
    }
}
