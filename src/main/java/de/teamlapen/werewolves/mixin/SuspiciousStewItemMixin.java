package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.Consumer;

@Mixin(SuspiciousStewItem.class)
public abstract class SuspiciousStewItemMixin extends Item {

    private SuspiciousStewItemMixin(Properties pProperties) {
        super(pProperties);
    }

    @Redirect(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"))
    private ItemStack finishUsing(Item instance, ItemStack pStack, Level pLevel, LivingEntity pLivingEntity ) {
        pStack = super.finishUsingItem(pStack, pLevel, pLivingEntity);
        if (!Helper.canEat(pLivingEntity, pStack)) {
            ItemStack copy = pStack.copy();
            CompoundTag tag = copy.getTag();
            if (tag != null) {
                tag.remove("effects");
            }
            pStack = copy;
        }
        return pStack;
    }
}
