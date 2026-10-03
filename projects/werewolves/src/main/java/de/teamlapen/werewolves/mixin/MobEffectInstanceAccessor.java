package de.teamlapen.werewolves.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobEffectInstance.class)
public interface MobEffectInstanceAccessor {

    @SuppressWarnings("UnusedReturnValue")
    @Invoker("tickDownDuration")
    int invokeTickDownDuration();

    @Accessor("hiddenEffect")
    MobEffectInstance getHiddenEffect();
}
