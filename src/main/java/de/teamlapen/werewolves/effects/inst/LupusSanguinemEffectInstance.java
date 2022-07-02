package de.teamlapen.werewolves.effects.inst;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class LupusSanguinemEffectInstance extends MobEffectInstance {
    public LupusSanguinemEffectInstance(int durationIn) {
        super(ModEffects.lupus_sanguinem.get(), durationIn, 0, false, true);
    }

    @Override
    public boolean update(@Nonnull MobEffectInstance other) {
        return false;
    }

    @Override
    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable runnable) {
        if (this.getDuration() % 10 == 0 && entityIn instanceof Player) {
            if (!Helper.canBecomeWerewolf((Player) entityIn)) {
                return false;
            }
        }
        return super.tick(entityIn, runnable);
    }
}
