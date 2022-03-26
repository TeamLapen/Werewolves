package de.teamlapen.werewolves.client.render.tiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class StoneAltarTESR implements BlockEntityRenderer<StoneAltarBlockEntity> {
    private static final ItemStack LIVER_STACK = new ItemStack(ModItems.liver, 1);
    private static final ItemStack BONE_STACK = new ItemStack(ModItems.cracked_bone, 1);

    public StoneAltarTESR(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(@Nonnull StoneAltarBlockEntity tileEntityIn, float partialTicks, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (!tileEntityIn.getItem(0).isEmpty()) {
            renderItem(tileEntityIn, matrixStackIn);
            matrixStackIn.translate(-0.5, 0, 0.5); // translate to final location
            matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 22.5f, true)); // rotate final
            matrixStackIn.mulPose(new Quaternion(new Vector3f(-1, 0, 0), 90f, true)); // rotate to flat
            Minecraft.getInstance().getItemRenderer().renderStatic(LIVER_STACK, ItemTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
            matrixStackIn.popPose();
        }
        if (!tileEntityIn.getItem(1).isEmpty()) {
            renderItem(tileEntityIn, matrixStackIn);
            matrixStackIn.translate(0.5, 0, 0.5); // translate to final location
            matrixStackIn.mulPose(new Quaternion(new Vector3f(0, -1, 0), 22.5f, true)); // rotate final
            matrixStackIn.mulPose(new Quaternion(new Vector3f(-1, 0, 0), 90f, true)); // rotate to flat
            Minecraft.getInstance().getItemRenderer().renderStatic(BONE_STACK, ItemTransforms.TransformType.GUI, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, 0);
            matrixStackIn.popPose();
        }
    }

    private void renderItem(@Nonnull StoneAltarBlockEntity tileEntityIn, @Nonnull PoseStack matrixStackIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5, 0.64, 0.5); // translate to middle
        matrixStackIn.scale(0.4f, 0.4f, 0.4f); // adjust item size
        switch (tileEntityIn.getBlockState().getValue(StoneAltarBlock.HORIZONTAL_FACING)) { // rotated based on facing
            case WEST -> matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 90, true));
            case SOUTH -> matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 180, true));
            case EAST -> matrixStackIn.mulPose(new Quaternion(new Vector3f(0, 1, 0), 270, true));
        }
    }
}
