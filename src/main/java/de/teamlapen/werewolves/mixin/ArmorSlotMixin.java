package de.teamlapen.werewolves.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ArmorSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ArmorSlot.class)
public class ArmorSlotMixin {

    @WrapOperation(method = "mayPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;canEquip(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/entity/LivingEntity;)Z"))
    private boolean test(ItemStack instance, EquipmentSlot equipmentSlot, LivingEntity entity, Operation<Boolean> original) {
        if (entity instanceof Player player && !(instance.getItem() instanceof IWerewolfArmor) && !WerewolfPlayer.get(player).canWearArmor(instance)) {
            return false;
        }
        return original.call(instance, equipmentSlot, entity);
    }
}
