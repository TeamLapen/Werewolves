package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.mixin.client.LayerRendererAccessor;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class WerewolfPlayerFaceOverlayLayer extends LayerRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>> {

    private final ResourceLocation[] eyeOverlays;

    public WerewolfPlayerFaceOverlayLayer(WerewolfPlayerRenderer entityRendererIn) {
        super(entityRendererIn);
        eyeOverlays = new ResourceLocation[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < eyeOverlays.length; i++) {
            eyeOverlays[i] = new ResourceLocation(REFERENCE.MODID + ":textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn, @Nonnull AbstractClientPlayerEntity player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        WerewolfPlayer werewolf = WerewolfPlayer.get(player);
        int eyeType = Math.max(0, Math.min(werewolf.getEyeType(((WerewolfPlayerRenderer) ((LayerRendererAccessor<?, ?>) this).getRenderer()).getForm()), eyeOverlays.length - 1));
        RenderType renderType = werewolf.getGlowingEyes(werewolf.getForm()) ? RenderType.getEyes(eyeOverlays[eyeType]) : RenderType.getEntityCutoutNoCull(eyeOverlays[eyeType]);
        IVertexBuilder vertexBuilderEye = bufferIn.getBuffer(renderType);
        int packerOverlay = LivingRenderer.getPackedOverlay(player, 0);
        this.getEntityModel().getModelRenderer().render(matrixStack, vertexBuilderEye, packedLightIn, packerOverlay);
    }
}
