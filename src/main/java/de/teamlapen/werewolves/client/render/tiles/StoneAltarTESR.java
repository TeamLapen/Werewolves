package de.teamlapen.werewolves.client.render.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.tileentity.StoneAltarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class StoneAltarTESR extends TileEntityRenderer<StoneAltarTileEntity> {
    private static final ItemStack LIVER_STACK = new ItemStack(ModItems.liver, 1);
    private static final ItemStack BONE_STACK = new ItemStack(ModItems.bone, 1);

    public StoneAltarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull StoneAltarTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!tileEntityIn.getItem(0).isEmpty()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.3, 0.67, 0.24);
            matrixStackIn.scale(0.4f, 0.4f, 0.4f);
            matrixStackIn.mulPose(new Quaternion(-0.4f, 0.1f, 0, 0.9f));
            Minecraft.getInstance().getItemRenderer().renderStatic(LIVER_STACK, ItemCameraTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
        if (!tileEntityIn.getItem(1).isEmpty()) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.7, 0.67, 0.2);
            matrixStackIn.scale(0.4f, 0.4f, 0.4f);
            matrixStackIn.mulPose(new Quaternion(-0.5f, -0.2f, -0.1f, 0.9f));
            Minecraft.getInstance().getItemRenderer().renderStatic(BONE_STACK, ItemCameraTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
    }
}
