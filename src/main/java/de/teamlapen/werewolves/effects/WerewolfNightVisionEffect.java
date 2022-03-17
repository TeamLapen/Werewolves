package de.teamlapen.werewolves.effects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import javax.annotation.Nonnull;
import java.util.Collections;

public class WerewolfNightVisionEffect extends MobEffectInstance {

    public WerewolfNightVisionEffect() {
        this(null);
    }

    public WerewolfNightVisionEffect(MobEffectInstance otherEffect) {
        super(MobEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false, otherEffect);
        this.setCurativeItems(Collections.emptyList());
    }

    public boolean update(@Nonnull MobEffectInstance other) {
        return false;
    }

    public boolean equals(Object other) {
        return other == this;
    }

    @Nonnull
    public String getDescriptionId() {
        return "effect.werewolves.night_vision";
    }

    public boolean isNoCounter() {
        return true;
    }

    public void applyEffect(@Nonnull LivingEntity entityIn) {
    }

    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable p_76455_2_) {
        if (this.hiddenEffect != null) {
            this.hiddenEffect.tickDownDuration();
        }
        return true;
    }

    @Nonnull
    public CompoundTag save(@Nonnull CompoundTag nbt) {
        return nbt;
    }

    @Override
    public boolean shouldRender() {
        return false;
    }
}
