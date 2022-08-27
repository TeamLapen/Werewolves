package de.teamlapen.werewolves.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;

@Mixin(TargetingConditions.class)
public interface TargetingConditionsAccessor {

    @Nullable
    @Accessor("selector")
    Predicate<LivingEntity> getSelector();
}
