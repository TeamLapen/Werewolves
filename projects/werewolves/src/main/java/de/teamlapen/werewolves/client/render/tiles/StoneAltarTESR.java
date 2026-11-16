package de.teamlapen.werewolves.client.render.tiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.common.world.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.common.world.blocks.entity.StoneAltarBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StoneAltarTESR implements BlockEntityRenderer<StoneAltarBlockEntity, StoneAltarTESR.StoneAltarRenderState> {

    private final ItemModelResolver itemModelResolver;

    public StoneAltarTESR(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public @NotNull StoneAltarRenderState createRenderState() {
        return new StoneAltarRenderState();
    }

    @Override
    public void extractRenderState(@NotNull StoneAltarBlockEntity blockEntity, @NotNull StoneAltarRenderState renderState, float partialTick, @NotNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);
        renderState.facing = blockEntity.getBlockState().getValue(StoneAltarBlock.HORIZONTAL_FACING);
        if (!blockEntity.getItem(0).isEmpty()) {
            this.itemModelResolver.updateForTopItem(renderState.liver, ModItems.LIVER.get().getDefaultInstance(), ItemDisplayContext.GUI, blockEntity.getLevel(), null, 0);
        } else {
            renderState.liver.clear();
        }
        if (!blockEntity.getItem(1).isEmpty()) {
            this.itemModelResolver.updateForTopItem(renderState.bone, ModItems.CRACKED_BONE.get().getDefaultInstance(), ItemDisplayContext.GUI, blockEntity.getLevel(), null, 0);
        } else {
            renderState.bone.clear();
        }
    }

    @Override
    public void submit(@NotNull StoneAltarRenderState renderState, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector nodeCollector, @NotNull CameraRenderState cameraRenderState) {
        if (!renderState.liver.isEmpty()) {
            prepareItem(renderState, poseStack);
            poseStack.translate(-0.5, 0, 0.5); // translate to final location
            poseStack.mulPose(Axis.YP.rotationDegrees(22.5f)); // rotate final
            poseStack.mulPose(Axis.XN.rotationDegrees(90f)); // rotate to flat
            renderState.liver.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
        if (!renderState.bone.isEmpty()) {
            prepareItem(renderState, poseStack);
            poseStack.translate(0.5, 0, 0.5); // translate to final location
            poseStack.mulPose(Axis.YN.rotationDegrees(22.5f)); // rotate final
            poseStack.mulPose(Axis.XN.rotationDegrees(90f)); // rotate to flat
            renderState.bone.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }

    private void prepareItem(@NotNull StoneAltarRenderState renderState, @NotNull PoseStack poseStack) {
        poseStack.pushPose();
        poseStack.translate(0.5, 0.64, 0.5); // translate to middle
        poseStack.scale(0.4f, 0.4f, 0.4f); // adjust item size
        switch (renderState.facing) { // rotated based on facing
            case WEST -> poseStack.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> poseStack.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> poseStack.mulPose(Axis.YP.rotationDegrees(270));
            default -> {
            }
        }
    }

    public static class StoneAltarRenderState extends BlockEntityRenderState {
        public final ItemStackRenderState liver = new ItemStackRenderState();
        public final ItemStackRenderState bone = new ItemStackRenderState();
        public Direction facing = Direction.NORTH;
    }
}
