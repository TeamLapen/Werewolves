package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.werewolves.client.model.geom.CancelRenderingModelPart;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public class ModelPartMixin implements CancelRenderingModelPart {

    private boolean skipRendering = false;

    @Override
    public void setSkipRendering(boolean skipRendering) {
        this.skipRendering = skipRendering;
    }

    @Inject(method = "compile", at = @At("HEAD"), cancellable = true)
    private void skipCompile(PoseStack.Pose p_104291_, VertexConsumer p_104292_, int p_104293_, int p_104294_, float p_104295_, float p_104296_, float p_104297_, float p_104298_, CallbackInfo ci) {
        if (skipRendering) {
            ci.cancel();
        }
    }
}
