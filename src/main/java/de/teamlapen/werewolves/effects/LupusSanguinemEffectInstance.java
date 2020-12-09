package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;

public class LupusSanguinemEffectInstance extends EffectInstance {
    public LupusSanguinemEffectInstance(int durationIn) {
        super(ModEffects.lupus_sanguinem, durationIn, 0, false, true);
    }

    @Override
    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable runnable) {
        return (this.getDuration() % 10 != 0 || !(entityIn instanceof PlayerEntity) || Helper.canBecomeWerewolf((PlayerEntity) entityIn)) && super.tick(entityIn, runnable);
    }
}
