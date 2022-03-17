package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.core.ModClient;
import net.minecraft.client.renderer.*;
import com.mojang.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Camera;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin {

    @Shadow
    @Final
    private RenderBuffers renderBuffers;

    @Inject(method = "renderLevel(Lcom/mojang/blaze3d/matrix/MatrixStack;FJZLnet/minecraft/client/renderer/ActiveRenderInfo;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/util/math/vector/Matrix4f;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;glint()Lnet/minecraft/client/renderer/RenderType;"))
    private void addBuffer(PoseStack l, float outlinelayerbuffer, long i2, boolean j2, Camera k2, GameRenderer l2, LightTexture i3, Matrix4f irendertypebuffer, CallbackInfo ci){
        MultiBufferSource.BufferSource irendertypebuffer$impl = this.renderBuffers.bufferSource();
        irendertypebuffer$impl.endBatch(ModClient.OIL_GLINT);
        irendertypebuffer$impl.endBatch(ModClient.OIL_GLINT_DIRECT);
    }
}
