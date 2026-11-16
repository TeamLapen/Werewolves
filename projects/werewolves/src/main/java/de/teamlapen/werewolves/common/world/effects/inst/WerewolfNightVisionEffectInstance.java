package de.teamlapen.werewolves.common.world.effects.inst;

import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.mixin.MobEffectInstanceAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;

public class WerewolfNightVisionEffectInstance extends MobEffectInstance {

    public WerewolfNightVisionEffectInstance() {
        this(null);
    }

    public WerewolfNightVisionEffectInstance(@Nullable MobEffectInstance otherEffect) {
        super(MobEffects.NIGHT_VISION, -1, 0, false, false, false, checkEffect(otherEffect));
        factions$addProperty(WReference.PERMANENT_INVISIBLE_MOB_EFFECT);
    }

    public boolean update(@Nonnull MobEffectInstance other) {
        return false;
    }

    public boolean equals(Object other) {
        return other == this;
    }

    @Override
    public boolean tickServer(@Nonnull ServerLevel serverLevel, @Nonnull LivingEntity entityIn, @Nonnull Runnable onEffectUpdate) {
        tickHiddenEffect();
        return true;
    }

    @Override
    public void tickClient() {
        tickHiddenEffect();
    }

    private void tickHiddenEffect() {
        if (((MobEffectInstanceAccessor) this).getHiddenEffect() != null) {
            ((MobEffectInstanceAccessor) ((MobEffectInstanceAccessor) this).getHiddenEffect()).invokeTickDownDuration();
        }
    }

    private static @Nullable MobEffectInstance checkEffect(@Nullable MobEffectInstance effectInstance) {
        return effectInstance != null && !effectInstance.isInfiniteDuration() ? effectInstance : null;
    }

}
