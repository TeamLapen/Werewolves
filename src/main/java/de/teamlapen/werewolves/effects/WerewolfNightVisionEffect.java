package de.teamlapen.werewolves.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

import javax.annotation.Nonnull;
import java.util.Collections;

public class WerewolfNightVisionEffect extends EffectInstance {

    public WerewolfNightVisionEffect() {
        super(Effects.NIGHT_VISION, 10000, 0, false, false);
        this.setCurativeItems(Collections.emptyList());
    }

    public boolean combine(@Nonnull EffectInstance other) {
        return false;
    }

    public boolean equals(Object other) {
        return other == this;
    }

    @Nonnull
    public String getEffectName() {
        return "effect.werewolves.night_vision";
    }

    public boolean getIsPotionDurationMax() {
        return true;
    }

    public void performEffect(@Nonnull LivingEntity entityIn) {
    }

    public boolean tick(@Nonnull LivingEntity entityIn, @Nonnull Runnable p_76455_2_) {
        return true;
    }

    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT nbt) {
        return nbt;
    }
}
