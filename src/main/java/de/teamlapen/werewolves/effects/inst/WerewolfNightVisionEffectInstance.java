package de.teamlapen.werewolves.effects.inst;

import de.teamlapen.vampirism.api.effects.IHiddenEffectInstance;
import de.teamlapen.werewolves.mixin.MobEffectInstanceAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Optional;

public class WerewolfNightVisionEffectInstance extends MobEffectInstance implements IHiddenEffectInstance {

    public WerewolfNightVisionEffectInstance() {
        this(null);
    }

    public WerewolfNightVisionEffectInstance(MobEffectInstance otherEffect) {
        super(MobEffects.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false, false, otherEffect, Optional.empty());
        this.setCurativeItems(Collections.emptyList());
    }

    public boolean update(@NotNull MobEffectInstance other) {
        return false;
    }

    public boolean equals(Object other) {
        return other == this;
    }

    @NotNull
    public String getDescriptionId() {
        return "effect.werewolves.night_vision";
    }

    public boolean isNoCounter() {
        return true;
    }

    public void applyEffect(@NotNull LivingEntity entityIn) {
    }

    public boolean tick(@NotNull LivingEntity entityIn, @NotNull Runnable p_76455_2_) {
        if (((MobEffectInstanceAccessor) this).getHiddenEffect() != null) {
            ((MobEffectInstanceAccessor) ((MobEffectInstanceAccessor) this).getHiddenEffect()).invokeTickDownDuration();
        }
        return true;
    }

    @NotNull
    public CompoundTag save(@NotNull CompoundTag nbt) {
        return nbt;
    }
}
