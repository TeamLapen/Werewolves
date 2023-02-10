package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.inventory.InventoryMenu$1")
public abstract class InventoryMenuSlotMixin {

    private Player player;

    /**
     * It is required to manually save the player, because otherwise the class does not compile
     */
    @Inject(method = "<init>", at = @At("RETURN"))
    private void s(InventoryMenu p_219977_, Container p_219978_, int p_219979_, int p_219980_, int p_219981_, Player par6, EquipmentSlot par7, CallbackInfo ci) {
        this.player = par6;
    }

    @Inject(method = "mayPlace(Lnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void mayPlace(ItemStack p_39746_, CallbackInfoReturnable<Boolean> cir) {
        if (!(p_39746_.getItem() instanceof IWerewolfArmor)) {
            WerewolfPlayer.getOpt(this.player).filter(IWerewolfPlayer::isArmorUnequipped).ifPresent(s -> cir.setReturnValue(false));
        }
    }
}
