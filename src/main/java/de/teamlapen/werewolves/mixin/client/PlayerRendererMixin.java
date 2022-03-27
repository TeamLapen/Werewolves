package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.core.ModEntityRenderer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    @Inject(method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At(value = "HEAD", shift = At.Shift.BY, by = 1), cancellable = true)
    private void rightArm(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn, CallbackInfo ci) {
        if (Helper.isWerewolf(playerIn) && WerewolfPlayer.get(playerIn).getForm().isTransformed()) {
            if (ModEntityRenderer.render.renderRightArm(matrixStackIn, bufferIn, combinedLightIn, playerIn)) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "renderLeftHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;)V", at = @At(value = "HEAD", shift = At.Shift.BY, by = 1), cancellable = true)
    private void leftArm(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn, CallbackInfo ci) {
        if (Helper.isWerewolf(playerIn) && WerewolfPlayer.get(playerIn).getForm().isTransformed()) {
            if (ModEntityRenderer.render.renderLeftArm(matrixStackIn, bufferIn, combinedLightIn, playerIn)) {
                ci.cancel();
            }
        }
    }
}
