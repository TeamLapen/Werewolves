package de.teamlapen.werewolves.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.function.Consumer;

/**
 * Prevents consume effects (e.g. food effects) for werewolves that cannot eat the item.
 * Replaces the former {@code LivingEntity#eat -> addEatEffect} hook, consume effects are applied by {@link Consumable} since 1.21.2
 */
@Mixin(Consumable.class)
public abstract class ConsumableMixin {

    @WrapOperation(method = "onConsume", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V"))
    private void cancelWerewolvesEatEffects(List<?> instance, Consumer<?> action, Operation<Void> original, Level level, LivingEntity user, ItemStack stack) {
        if (Helper.canEat(user, stack)) {
            original.call(instance, action);
        }
    }
}
