package de.teamlapen.werewolves.common.world.effects.inst;

import de.teamlapen.werewolves.common.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class UnWerewolfEffectInstance extends MobEffectInstance {
    public UnWerewolfEffectInstance(int durationIn) {
        super(ModEffects.UN_WEREWOLF, durationIn, 0, false, true, false);
    }

    @Override
    public boolean tickServer(@Nonnull ServerLevel serverLevel, @Nonnull LivingEntity entityIn, @Nonnull Runnable runnable) {
        return (this.getDuration() % 10 != 0 || !(entityIn instanceof Player) || Helper.isWerewolf((Player) entityIn)) && super.tickServer(serverLevel, entityIn, runnable);
    }
}
