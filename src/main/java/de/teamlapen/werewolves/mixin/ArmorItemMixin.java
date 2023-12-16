package de.teamlapen.werewolves.mixin;

import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.core.BlockSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {

    @Unique
    private static ItemStack werewolves$pStack;

    @Inject(method = "dispenseArmor(Lnet/minecraft/core/BlockSource;Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "HEAD"))
    private static void saveItemStack(BlockSource pSource, ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        ArmorItemMixin.werewolves$pStack = pStack;
    }
    @ModifyArg(method = "dispenseArmor(Lnet/minecraft/core/BlockSource;Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;"))
    private static <T extends Entity> Predicate<? super T> test(Predicate<? super T> par1){
        return par1.and(entity -> !Helper.isWerewolf((Entity) entity) || !(entity instanceof Player player) || WerewolfPlayer.getOpt(player).map(s -> s.canWearArmor(werewolves$pStack)).orElse(false));
    }
}
