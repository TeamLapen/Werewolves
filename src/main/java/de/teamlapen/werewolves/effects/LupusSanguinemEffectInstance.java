package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;

public class LupusSanguinemEffectInstance extends EffectInstance {
    public LupusSanguinemEffectInstance(int durationIn) {
        super(ModEffects.LUPUS_SANGUINEM.get(), durationIn, 0, false, true);
    }

    @Override
    public boolean update(@Nonnull EffectInstance other) {
        return false;
    }

    @Override
    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable runnable) {
        if (this.getDuration() % 10 == 0 && entityIn instanceof PlayerEntity) {
            if (!Helper.canBecomeWerewolf((PlayerEntity) entityIn)) {
                return false;
            }
        }
        return super.tick(entityIn, runnable);
    }
}
