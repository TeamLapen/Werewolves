package de.teamlapen.werewolves.effects.inst;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class UnWerewolfEffectInstance extends MobEffectInstance {
    public UnWerewolfEffectInstance(int durationIn) {
        super(ModEffects.UN_WEREWOLF.get(), durationIn, 0, false, true, false);
        this.setNoCounter(true);
    }

    @Override
    public boolean tick(@NotNull LivingEntity entityIn, @NotNull Runnable runnable) {
        return (this.getDuration() % 10 != 0 || !(entityIn instanceof Player) || Helper.isWerewolf((Player) entityIn)) && super.tick(entityIn, runnable);
    }
}
