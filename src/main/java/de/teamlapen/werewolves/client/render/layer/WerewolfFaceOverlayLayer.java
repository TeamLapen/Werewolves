package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class WerewolfFaceOverlayLayer<T extends LivingEntity, E extends WerewolfBaseModel<T>> extends RenderLayer<T, E> {

    private final ResourceLocation[] eyeOverlays;


    public WerewolfFaceOverlayLayer(RenderLayerParent<T, E> renderer) {
        super(renderer);
        this.eyeOverlays = new ResourceLocation[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < this.eyeOverlays.length; i++) {
            this.eyeOverlays[i] = WResourceLocation.mod("textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource bufferIn, int packedLightIn, @Nonnull T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        IWerewolf werewolf = entity instanceof IWerewolf ? (IWerewolf) entity : WerewolfPlayer.get(((Player) entity));
        int eyeType = Math.max(0, Math.min(getEyeType(werewolf), eyeOverlays.length - 1));
        VertexConsumer vertexBuilderEye = bufferIn.getBuffer(hasGlowingEyes(werewolf) ? RenderType.eyes(eyeOverlays[eyeType]) : RenderType.entityCutoutNoCull(eyeOverlays[eyeType]));
        this.getParentModel().renderToBuffer(matrixStack, vertexBuilderEye, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    public int getEyeType(IWerewolf werewolf) {
        return werewolf.getEyeType();
    }

    public boolean hasGlowingEyes(IWerewolf werewolf) {
        return werewolf.hasGlowingEyes();
    }
}
