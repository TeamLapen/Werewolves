package de.teamlapen.werewolves.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import de.teamlapen.werewolves.client.core.ModClient;
import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import staticnet.minecraft.client.renderer.entity.ItemRendererr.getFoilBufferDirect;

import java.awt.*;

import static net.minecraft.client.renderer.ItemRenderer.getCompassFoilBufferDirect;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Redirect(method = "render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;ZLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IILnet/minecraft/client/renderer/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;getCompassFoilBufferDirect(Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/client/renderer/RenderType;Lcom/mojang/blaze3d/matrix/MatrixStack$Entry;)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"))
    private VertexConsumer applyOil_1(MultiBufferSource renderTypeBuffer, RenderType renderType, PoseStack.Pose stackEntry,ItemStack itemStack) {
        if (!WeaponOilHelper.hasOils(itemStack)) return getCompassFoilBufferDirect(renderTypeBuffer, renderType, stackEntry);
        return VertexMultiConsumer.create(new SheetedDecalTextureGenerator(renderTypeBuffer.getBuffer(ModClient.OIL_GLINT_DIRECT), stackEntry.pose(), stackEntry.normal()), renderTypeBuffer.getBuffer(renderType));
    }

    @Redirect(method = "render(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/model/ItemCameraTransforms$TransformType;ZLcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;IILnet/minecraft/client/renderer/model/IBakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;getFoilBufferDirect(Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/IVertexBuilder;"))
    private VertexConsumer applyOil_2(MultiBufferSource renderTypeBuffer, RenderType renderType, boolean p_229113_2_, boolean p_229113_3_, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean p_229111_3_, PoseStack matrixStack) {
        if (!WeaponOilHelper.hasOils(itemStack)) return getFoilBufferDirect(renderTypeBuffer, renderType, true, itemStack.hasFoil());
        matrixStack.pushPose();
        PoseStack.Pose entry = matrixStack.last();
        if (transformType == ItemTransforms.TransformType.GUI) {
            entry.pose().multiply(0.5F);
        } else if (transformType.firstPerson()) {
            entry.pose().multiply(0.75F);
        }
        VertexConsumer builder = VertexMultiConsumer.create(new SheetedDecalTextureGenerator(renderTypeBuffer.getBuffer(ModClient.OIL_GLINT_DIRECT), entry.pose(), entry.normal()), renderTypeBuffer.getBuffer(renderType));
        matrixStack.popPose();
        return builder;
    }

    @Inject(method = "renderGuiItemDecorations(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", shift = At.Shift.BEFORE, ordinal = 0))
    private void renderOil(Font font, ItemStack itemStack, int x, int y, String string, CallbackInfo ci) {
        WeaponOilHelper.oilOpt(itemStack).ifPresent((pair) -> {
            double perc = (float) pair.getRight() / (float) pair.getLeft().getMaxDuration(itemStack);
            if (perc < 1) {
                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableAlphaTest();
                RenderSystem.disableBlend();
                Tesselator tessellator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuilder();
                int percentage = Math.round((float) perc * 13);
                int rgbDurability = Color.lightGray.getRGB();
                this.fillRect(bufferbuilder, x + 2, y + 12, 13, 1, 0, 0, 0, 255);
                this.fillRect(bufferbuilder, x + 2, y + 12, percentage, 1, rgbDurability >> 16 & 255, rgbDurability >> 8 & 255, rgbDurability & 255, 255);
                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }
        });
    }

    @Shadow
    private void fillRect(BufferBuilder p_181565_1_, int p_181565_2_, int p_181565_3_, int p_181565_4_, int p_181565_5_, int p_181565_6_, int p_181565_7_, int p_181565_8_, int p_181565_9_) {

    }
}
