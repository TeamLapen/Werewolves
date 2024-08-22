package de.teamlapen.werewolves.client.render.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.layer.SurvivalItemInMouthLayer;
import de.teamlapen.werewolves.client.render.layer.WerewolfFormFaceOverlayLayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WerewolfPlayerSurvivalistRenderer extends WerewolfPlayerRenderer<AbstractClientPlayer, WerewolfSurvivalistModel<AbstractClientPlayer>> {

    private final List<ResourceLocation> textures;
    public WerewolfPlayerSurvivalistRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfSurvivalistModel<>(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)), 1F);
        this.textures = WerewolfSurvivalistModel.getSurvivalTextures();

        this.addLayer(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.SURVIVALIST, this));
        this.addLayer(new ArrowLayer<>(context, this));
        this.addLayer(new SurvivalItemInMouthLayer<>(this, context.getItemInHandRenderer()));

    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractClientPlayer player) {
        WerewolfPlayer werewolf = WerewolfPlayer.get(player);
        return textures.get(werewolf.getSkinType(WerewolfForm.SURVIVALIST) % textures.size());
    }

    @Override
    public void renderRightHand(PoseStack stack, MultiBufferSource bufferSource, int p_117773_, AbstractClientPlayer entity) {

    }

    @Override
    public void renderLeftHand(PoseStack stack, MultiBufferSource bufferSource, int p_117816_, AbstractClientPlayer entity) {

    }

    @Override
    protected void setupSwimRotations(AbstractClientPlayer pEntityLiving, PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTicks, float pScale) {
        float f3 = pEntityLiving.isInWater() || pEntityLiving.isInFluidType((fluidType, height) -> pEntityLiving.canSwimInFluidType(fluidType)) ? -10.0F - pEntityLiving.getXRot() : -10.0F;
        float f4 = Mth.lerp(pEntityLiving.getSwimAmount(pPartialTicks), 0.0F, f3);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(f4));
    }
}
