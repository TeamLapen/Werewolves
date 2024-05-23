package de.teamlapen.werewolves.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(SuspiciousStewItem.class)
public class SuspiciousStewItemMixin {

    @WrapOperation(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/SuspiciousStewItem;listPotionEffects(Lnet/minecraft/world/item/ItemStack;Ljava/util/function/Consumer;)V"))
    private void finishUsing(ItemStack pStack, Consumer<SuspiciousEffectHolder.EffectEntry> pOutput, Operation<Void> original, ItemStack originalStack, Level pLevel, LivingEntity pEntityLiving) {
        if (!Helper.canEat(pEntityLiving, pStack)) {
            ItemStack copy = pStack.copy();
            CompoundTag tag = copy.getTag();
            if (tag != null) {
                tag.remove("effects");
            }
            pStack = copy;
        }
        original.call(pStack, pOutput);
    }
}
