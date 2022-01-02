package de.teamlapen.werewolves.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.annotation.Nonnull;
import java.util.Collections;

public class WerewolfNightVisionEffect extends EffectInstance {

    public WerewolfNightVisionEffect() {
        this(null);
    }

    public WerewolfNightVisionEffect(EffectInstance otherEffect) {
        super(Effects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false, otherEffect);
        this.setCurativeItems(Collections.emptyList());
    }

    public boolean update(@Nonnull EffectInstance other) {
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
    public CompoundNBT save(@Nonnull CompoundNBT nbt) {
        return nbt;
    }
}
