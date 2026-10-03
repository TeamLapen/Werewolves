package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.client.WerewolvesModClient;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;endLastBatch()V", shift = At.Shift.AFTER))
    private void test(DeltaTracker pDeltaTracker, boolean pRenderBlockOutline, Camera pCamera, GameRenderer pGameRenderer, LightTexture pLightTexture, Matrix4f pFrustumMatrix, Matrix4f pProjectionMatrix, CallbackInfo ci) {
        WerewolvesModClient.getInstance().getRenderHandler().endVisionBatch();
    }
}
