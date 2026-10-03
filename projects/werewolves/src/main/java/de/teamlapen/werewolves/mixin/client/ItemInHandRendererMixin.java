package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.WerewolvesModClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "renderPlayerArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", shift = At.Shift.BEFORE), cancellable = true)
    private void renderWerewolfPlayerArm(PoseStack stack, MultiBufferSource bufferSource, int pCombinedLight, float p_109350_, float p_109351_, HumanoidArm arm, CallbackInfo ci) {
        if (WerewolvesModClient.getInstance().getModPlayerRenderer().renderArm(this.minecraft.player, stack, bufferSource, pCombinedLight, arm)) {
            ci.cancel();
        }
    }

    @Inject(method = "renderMapHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", shift = At.Shift.BEFORE), cancellable = true)
    private void renderWerewolfMapHand(PoseStack p_109362_, MultiBufferSource p_109363_, int p_109364_, HumanoidArm p_109365_, CallbackInfo ci) {
        if (WerewolvesModClient.getInstance().getModPlayerRenderer().renderArmMap(this.minecraft.player, p_109362_, p_109363_, p_109364_, p_109365_)) {
            ci.cancel();
        }
    }
}
