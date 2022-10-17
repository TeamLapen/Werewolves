package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Shadow @Final private EntityRenderDispatcher entityRenderDispatcher;

    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "renderPlayerArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", shift = At.Shift.BEFORE), cancellable = true)
    private void renderWerewolfPlayerArm(PoseStack stack, MultiBufferSource bufferSource, int p_109349_, float p_109350_, float p_109351_, HumanoidArm arm, CallbackInfo ci) {
        EntityRenderer<? super LocalPlayer> renderer = this.entityRenderDispatcher.getRenderer(this.minecraft.player);
        if (renderer instanceof WerewolfPlayerRenderer werewolfPlayerRenderer) {
            ci.cancel();
            if (arm != HumanoidArm.LEFT) {
                werewolfPlayerRenderer.renderRightHand(stack, bufferSource, p_109349_, this.minecraft.player);
            } else {
                werewolfPlayerRenderer.renderLeftHand(stack, bufferSource, p_109349_, this.minecraft.player);
            }
        }
    }

    @Inject(method = "renderMapHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderDispatcher;getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;", shift = At.Shift.BEFORE), cancellable = true)
    private void renderWerewolfMapHand(PoseStack p_109362_, MultiBufferSource p_109363_, int p_109364_, HumanoidArm p_109365_, CallbackInfo ci) {
        EntityRenderer<? super LocalPlayer> renderer = this.entityRenderDispatcher.getRenderer(this.minecraft.player);
        if (renderer instanceof WerewolfPlayerRenderer) {
            ci.cancel();
        }
    }
}
