package de.teamlapen.werewolves.effects.inst;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.effect.EffectInstanceWithSource;
import de.teamlapen.werewolves.mixin.MobEffectInstanceAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;

public class WerewolfNightVisionEffectInstance extends MobEffectInstance {

    public WerewolfNightVisionEffectInstance() {
        this(null);
    }

    public WerewolfNightVisionEffectInstance(MobEffectInstance otherEffect) {
        super(MobEffects.NIGHT_VISION, -1, 0, false, false, false, otherEffect);
        this.getCures().clear();
        ((EffectInstanceWithSource) this).setSource(VReference.PERMANENT_INVISIBLE_MOB_EFFECT);
    }

    public boolean update(@Nonnull MobEffectInstance other) {
        return false;
    }

    public boolean equals(Object other) {
        return other == this;
    }

    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable p_76455_2_) {
        if (((MobEffectInstanceAccessor) this).getHiddenEffect() != null) {
            ((MobEffectInstanceAccessor) ((MobEffectInstanceAccessor) this).getHiddenEffect()).invokeTickDownDuration();
        }
        return true;
    }

    @Nonnull
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        return nbt;
    }
}
