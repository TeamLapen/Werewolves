package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.core.ModEntityRenderer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "renderRightHand(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/client/entity/player/AbstractClientPlayerEntity;)V", at = @At("HEAD"), cancellable = true)
    private void rightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn, CallbackInfo ci) {
        if (Helper.isWerewolf(playerIn) && WerewolfPlayer.get(playerIn).getForm().isTransformed()) {
            if(ModEntityRenderer.render.renderRightArm(matrixStackIn, bufferIn, combinedLightIn, playerIn)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderLeftHand(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/client/entity/player/AbstractClientPlayerEntity;)V", at = @At("HEAD"), cancellable = true)
    private void leftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn, CallbackInfo ci) {
        if (Helper.isWerewolf(playerIn) && WerewolfPlayer.get(playerIn).getForm().isTransformed()) {
            ModEntityRenderer.render.renderLeftArm(matrixStackIn, bufferIn, combinedLightIn, playerIn);
            ci.cancel();
        }
    }
}
