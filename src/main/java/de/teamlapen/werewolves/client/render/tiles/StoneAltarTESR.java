package de.teamlapen.werewolves.client.render.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.entity.StoneAltarTileEntity;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class StoneAltarTESR extends TileEntityRenderer<StoneAltarTileEntity> {
    private static final ItemStack LIVER_STACK = new ItemStack(ModItems.liver, 1);
    private static final ItemStack BONE_STACK = new ItemStack(ModItems.cracked_bone, 1);

    public StoneAltarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull StoneAltarTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!tileEntityIn.getItem(0).isEmpty()) {
            renderItem(tileEntityIn, matrixStackIn);
            matrixStackIn.translate(-0.5, 0, 0.5); // translate to final location
            matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 22.5f, true)); // rotate final
            matrixStackIn.mulPose(new Quaternion(new Vector3f(-1, 0, 0), 90f, true)); // rotate to flat
            Minecraft.getInstance().getItemRenderer().renderStatic(LIVER_STACK, ItemCameraTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
        if (!tileEntityIn.getItem(1).isEmpty()) {
            renderItem(tileEntityIn, matrixStackIn);
            matrixStackIn.translate(0.5, 0, 0.5); // translate to final location
            matrixStackIn.mulPose(new Quaternion(new Vector3f(0, -1, 0), 22.5f, true)); // rotate final
            matrixStackIn.mulPose(new Quaternion(new Vector3f(-1, 0, 0), 90f, true)); // rotate to flat
            Minecraft.getInstance().getItemRenderer().renderStatic(BONE_STACK, ItemCameraTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
    }

    private void renderItem(@Nonnull StoneAltarTileEntity tileEntityIn, @Nonnull MatrixStack matrixStackIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0.64, 0.5); // translate to middle
        matrixStackIn.scale(0.4f, 0.4f, 0.4f); // adjust item size
        switch (tileEntityIn.getBlockState().getValue(StoneAltarBlock.HORIZONTAL_FACING)) { // rotated based on facing
            case WEST:
                matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 90, true));
                break;
            case SOUTH:
                matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 180, true));
                break;
            case EAST:
                matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 270, true));
                break;
        }
    }
}
