package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.werewolf.IWerewolf;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class WerewolfFaceOverlayLayer<T extends LivingEntity> extends LayerRenderer<T, WerewolfBaseModel<T>> {

    private final ResourceLocation[] eyeOverlays;

    public WerewolfFaceOverlayLayer(LivingRenderer<T, WerewolfBaseModel<T>> renderer, ResourceLocation[] overlays) {
        super(renderer);
        this.eyeOverlays = overlays;
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer bufferIn, int packedLightIn, @Nonnull T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IWerewolf werewolf = entity instanceof IWerewolf ? (IWerewolf) entity : WerewolfPlayer.get(((PlayerEntity) entity));
        int eyeType = Math.max(0, Math.min(getEyeType(werewolf), eyeOverlays.length - 1));
        RenderType renderType = hasGlowingEyes(werewolf) ? RenderType.eyes(eyeOverlays[eyeType]) : RenderType.entityCutoutNoCull(eyeOverlays[eyeType]);
        IVertexBuilder vertexBuilderEye = bufferIn.getBuffer(renderType);
        int packerOverlay = LivingRenderer.getOverlayCoords(entity, 0);
        this.getParentModel().getModelRenderer().render(matrixStack, vertexBuilderEye, packedLightIn, packerOverlay);
    }

    public int getEyeType(IWerewolf werewolf) {
        return werewolf.getEyeType();
    }

    public boolean hasGlowingEyes(IWerewolf werewolf) {
        return werewolf.hasGlowingEyes();
    }
}
