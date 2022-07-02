package de.teamlapen.werewolves.effects.inst;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class UnWerewolfEffectInstance extends MobEffectInstance {
    public UnWerewolfEffectInstance(int durationIn) {
        super(ModEffects.un_werewolf.get(), durationIn, 0, false, true, false);
    }

    @Override
    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable runnable) {
        return (this.getDuration() % 10 != 0 || !(entityIn instanceof Player) || Helper.isWerewolf((Player) entityIn)) && super.tick(entityIn, runnable);
    }
}
