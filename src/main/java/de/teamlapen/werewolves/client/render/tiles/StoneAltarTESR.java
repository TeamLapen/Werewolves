package de.teamlapen.werewolves.client.render.tiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class StoneAltarTESR implements BlockEntityRenderer<StoneAltarBlockEntity> {
    private static final ItemStack LIVER_STACK = ModItems.LIVER.get().getDefaultInstance();
    private static final ItemStack BONE_STACK = ModItems.CRACKED_BONE.get().getDefaultInstance();

    public StoneAltarTESR(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@Nonnull StoneAltarBlockEntity tileEntityIn, float partialTicks, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!tileEntityIn.getItem(0).isEmpty()) {
            renderItem(tileEntityIn, matrixStackIn);
            matrixStackIn.translate(-0.5, 0, 0.5); // translate to final location
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(22.5f)); // rotate final
            matrixStackIn.mulPose(Axis.XN.rotationDegrees(90f)); // rotate to flat
            Minecraft.getInstance().getItemRenderer().renderStatic(LIVER_STACK, ItemDisplayContext.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tileEntityIn.getLevel(), 0);
            matrixStackIn.popPose();
        }
        if (!tileEntityIn.getItem(1).isEmpty()) {
            renderItem(tileEntityIn, matrixStackIn);
            matrixStackIn.translate(0.5, 0, 0.5); // translate to final location
            matrixStackIn.mulPose(Axis.YN.rotationDegrees(22.5f)); // rotate final
            matrixStackIn.mulPose(Axis.XN.rotationDegrees(90f)); // rotate to flat
            Minecraft.getInstance().getItemRenderer().renderStatic(BONE_STACK, ItemDisplayContext.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, tileEntityIn.getLevel(), 0);
            matrixStackIn.popPose();
        }
    }

    private void renderItem(@Nonnull StoneAltarBlockEntity tileEntityIn, @Nonnull PoseStack matrixStackIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0.64, 0.5); // translate to middle
        matrixStackIn.scale(0.4f, 0.4f, 0.4f); // adjust item size
        switch (tileEntityIn.getBlockState().getValue(StoneAltarBlock.HORIZONTAL_FACING)) { // rotated based on facing
            case WEST -> matrixStackIn.mulPose(Axis.YP.rotationDegrees(90));
            case SOUTH -> matrixStackIn.mulPose(Axis.YP.rotationDegrees(180));
            case EAST -> matrixStackIn.mulPose(Axis.YP.rotationDegrees(270));
        }
    }
}
