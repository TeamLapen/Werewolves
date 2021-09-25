package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class WerewolfEntityFaceOverlayLayer<T extends WerewolfBaseEntity, M extends WerewolfBaseModel<T>> extends LayerRenderer<T,M> {

    private final ResourceLocation[] eyeOverlays;

    public WerewolfEntityFaceOverlayLayer(IEntityRenderer<T, M> entityRendererIn) {
        super(entityRendererIn);
        eyeOverlays = new ResourceLocation[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < eyeOverlays.length; i++) {
            eyeOverlays[i] = new ResourceLocation(REFERENCE.MODID + ":textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn, @Nonnull WerewolfBaseEntity werewolf, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        int s = werewolf.getEyeType();
        int eyeType = Math.max(0, s) % (eyeOverlays.length);
        RenderType renderType = RenderType.eyes(eyeOverlays[eyeType]);
        IVertexBuilder vertexBuilderEye = bufferIn.getBuffer(renderType);
        int packerOverlay = LivingRenderer.getOverlayCoords(werewolf, 0);
        this.getParentModel().getModelRenderer().render(matrixStack, vertexBuilderEye, packedLightIn, packerOverlay);
    }
}
