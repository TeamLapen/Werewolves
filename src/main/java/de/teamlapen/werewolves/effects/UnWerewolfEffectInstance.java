package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

import javax.annotation.Nonnull;

public class UnWerewolfEffectInstance extends EffectInstance {
    public UnWerewolfEffectInstance(int durationIn) {
        super(ModEffects.UN_WEREWOLF.get(), durationIn, 0, false, true, false);
    }

    @Override
    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable runnable) {
        return (this.getDuration() % 10 != 0 || !(entityIn instanceof PlayerEntity) || Helper.isWerewolf((PlayerEntity) entityIn)) && super.tick(entityIn, runnable);
    }
}
