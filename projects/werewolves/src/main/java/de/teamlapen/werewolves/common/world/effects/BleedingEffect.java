package de.teamlapen.werewolves.common.world.effects;

import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.attachments.ModDamageSources;
import de.teamlapen.werewolves.util.DamageHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Deals damage over time, but never kills the entity.
 * <p>
 * Only used if Vampirism is not installed, otherwise Vampirism's bleeding effect is used (see {@link de.teamlapen.werewolves.common.core.ModEffects#bleeding()}).
 */
public class BleedingEffect extends MobEffect {

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0x740000);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (entity.isInvertedHealAndHarm()) {
            return false;
        }
        if (entity.getHealth() > 1.0F) {
            DamageHandler.hurtModded(level, entity, ModDamageSources::bloodLoss, ModConfig.balance().POTIONS.bleedingEffectDamage.get().floatValue());
        }
        return true;
    }

    /**
     * Damages every 5 ticks, halving the interval for each amplifier level
     */
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int interval = 5 >> amplifier;
        return interval <= 0 || duration % interval == 0;
    }
}
