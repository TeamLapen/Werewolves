package de.teamlapen.werewolves.integration.vampirism.client.renderer.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.integration.vampirism.common.world.blockentities.WolfsbaneDiffuserBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Renders a rotating wolfsbane above diffusers in range if the player holds a wolfsbane finder
 */
public class WolfsbaneDiffuserBESR implements BlockEntityRenderer<WolfsbaneDiffuserBlockEntity, WolfsbaneDiffuserBESR.WolfsbaneDiffuserRenderState> {

    private final ItemModelResolver itemModelResolver;

    public WolfsbaneDiffuserBESR(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NotNull WolfsbaneDiffuserRenderState createRenderState() {
        return new WolfsbaneDiffuserRenderState();
    }

    @Override
    public void extractRenderState(@NotNull WolfsbaneDiffuserBlockEntity blockEntity, @NotNull WolfsbaneDiffuserRenderState renderState, float partialTick, @NotNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        Entity e = Minecraft.getInstance().getCameraEntity();
        renderState.visible = e instanceof LivingEntity living && (living.getMainHandItem().is(ModItems.WOLFSBANE_FINDER) || living.getOffhandItem().is(ModItems.WOLFSBANE_FINDER)) && blockEntity.isInRange(e.blockPosition());
        if (renderState.visible) {
            long totalWorldTime = blockEntity.getLevel() != null ? blockEntity.getLevel().getGameTime() : 0;
            renderState.rotation = (totalWorldTime + partialTick) % 360;
            renderState.scale = (float) Mth.clamp(Math.sqrt(blockEntity.getBlockPos().distSqr(e.blockPosition())) / 16, 1, 3);
            this.itemModelResolver.updateForTopItem(renderState.item, ModBlocks.WOLFSBANE.get().asItem().getDefaultInstance(), ItemDisplayContext.GROUND, blockEntity.getLevel(), null, 0);
        } else {
            renderState.item.clear();
        }
    }

    @Override
    public void submit(@NotNull WolfsbaneDiffuserRenderState renderState, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector nodeCollector, @NotNull CameraRenderState cameraRenderState) {
        if (!renderState.visible || renderState.item.isEmpty()) return;
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        poseStack.scale(renderState.scale, renderState.scale, renderState.scale);
        poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
        renderState.item.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    public static class WolfsbaneDiffuserRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState item = new ItemStackRenderState();
        public boolean visible;
        public float rotation;
        public float scale = 1;
    }
}
