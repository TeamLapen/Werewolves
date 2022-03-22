package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.werewolf.IWerewolf;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class WerewolfFaceOverlayLayer<T extends LivingEntity> extends RenderLayer<T, WerewolfBaseModel<T>> {

    private final ResourceLocation[] eyeOverlays;


    public WerewolfFaceOverlayLayer(LivingEntityRenderer<T, WerewolfBaseModel<T>> renderer, ResourceLocation[] overlays) {
        super(renderer);
        this.eyeOverlays = overlays;
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource bufferIn, int packedLightIn, @Nonnull T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IWerewolf werewolf = entity instanceof IWerewolf ? (IWerewolf) entity : WerewolfPlayer.get(((Player) entity));
        int eyeType = Math.max(0, Math.min(getEyeType(werewolf), eyeOverlays.length - 1));
        RenderType renderType = hasGlowingEyes(werewolf) ? RenderType.eyes(eyeOverlays[eyeType]) : RenderType.entityCutoutNoCull(eyeOverlays[eyeType]);
        VertexConsumer vertexBuilderEye = bufferIn.getBuffer(renderType);
        int packerOverlay = LivingEntityRenderer.getOverlayCoords(entity, 0);
        this.getParentModel().getModelRenderer().render(matrixStack, vertexBuilderEye, packedLightIn, packerOverlay);
    }

    public int getEyeType(IWerewolf werewolf) {
        return werewolf.getEyeType();
    }

    public boolean hasGlowingEyes(IWerewolf werewolf) {
        return werewolf.hasGlowingEyes();
    }
}
