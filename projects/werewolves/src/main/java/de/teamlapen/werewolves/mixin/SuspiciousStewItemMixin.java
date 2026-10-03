package de.teamlapen.werewolves.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(SuspiciousStewItem.class)
public class SuspiciousStewItemMixin {

    @Inject(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/component/SuspiciousStewEffects;effects()Ljava/util/List;", shift = At.Shift.BEFORE))
    private void checkWerewolf(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, CallbackInfoReturnable<ItemStack> cir, @Local LocalRef<SuspiciousStewEffects> effects) {
        if (!Helper.canEat(pEntityLiving, pStack)) {
            effects.set(SuspiciousStewEffects.EMPTY);
        }
    }
}
