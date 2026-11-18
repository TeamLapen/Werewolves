package de.teamlapen.werewolves.misc.mixin;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Suspicious stew effects are applied as a consume listener since 1.21.2. Prevent the effects for werewolves that cannot eat the stew.
 */
@Mixin(SuspiciousStewEffects.class)
public class SuspiciousStewEffectsMixin {

    @Inject(method = "onConsume", at = @At("HEAD"), cancellable = true)
    private void checkWerewolf(Level level, LivingEntity user, ItemStack stack, Consumable consumable, CallbackInfo ci) {
        if (!Helper.canEat(user, stack)) {
            ci.cancel();
        }
    }
}
