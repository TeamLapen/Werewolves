package de.teamlapen.werewolves.mixin.entity;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.werewolves.core.WerewolfActions;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;

@Mixin(targets = "net/minecraft/inventory/container/PlayerContainer$1")
public class MixinPlayerContainerArmorSlot extends Slot {

    @Deprecated
    public MixinPlayerContainerArmorSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Shadow(aliases = "this$0")
    @Final
    private PlayerContainer this$0;

    @Nonnull
    @Override
    public ItemStack getStack() {
        LazyOptional<WerewolfPlayer> player = WerewolfPlayer.getOpt(((MixinPlayerContainerAccessor) this$0).getPlayer());
        if ((player.map(Helper::isFormActionActive).orElse(false))) {
            if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 344)) {
                return player.map(w -> w.getArmorItems().get(39 - this.getSlotIndex() + 1)).orElse(ItemStack.EMPTY);
            }
        }
        return super.getStack();
    }

    @Inject(method = "isItemValid(Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "RETURN"), cancellable = true)
    public void isItemValid(ItemStack stack, @Nonnull CallbackInfoReturnable<Boolean> cir){
        if (cir.getReturnValue()) {
            LazyOptional<WerewolfPlayer> player = WerewolfPlayer.getOpt(((MixinPlayerContainerAccessor) this$0).getPlayer());
            if (player.map(Helper::isFormActionActive).orElse(false)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "getBackground()Lcom/mojang/datafixers/util/Pair;", at = @At("RETURN"), cancellable = true)
    public void getBackground(CallbackInfoReturnable<Pair<ResourceLocation, ResourceLocation>> cir) {
        LazyOptional<WerewolfPlayer> player = WerewolfPlayer.getOpt(((MixinPlayerContainerAccessor) this$0).getPlayer());
        if (player.map(Helper::isFormActionActive).orElse(false)) {
            ResourceLocation old = cir.getReturnValue().getSecond();
            cir.setReturnValue(Pair.of(PlayerContainer.LOCATION_BLOCKS_TEXTURE, new ResourceLocation(REFERENCE.MODID, old.getPath())));
        }
    }
}
