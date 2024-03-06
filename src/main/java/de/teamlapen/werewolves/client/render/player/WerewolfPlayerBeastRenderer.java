package de.teamlapen.werewolves.client.render.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.layer.BeastItemInHandLayer;
import de.teamlapen.werewolves.client.render.layer.WerewolfFormFaceOverlayLayer;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WerewolfPlayerBeastRenderer extends WerewolfPlayerRenderer<AbstractClientPlayer, WerewolfBeastModel<AbstractClientPlayer>> {

    private final List<ResourceLocation> textures;
    public WerewolfPlayerBeastRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfBeastModel<>(context.bakeLayer(ModModelRender.WEREWOLF_BEAST)), 1F);
        this.textures = WerewolfBeastModel.getBeastTextures();

        this.addLayer(new WerewolfFormFaceOverlayLayer<>(WerewolfForm.BEAST, this));
        this.addLayer(new BeastItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new ArrowLayer<>(context, this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AbstractClientPlayer player) {
        WerewolfPlayer werewolf = WerewolfPlayer.get(player);
        return textures.get(werewolf.getSkinType(WerewolfForm.BEAST) % textures.size());
    }

    @Override
    public void renderRightHand(PoseStack stack, MultiBufferSource bufferSource, int p_117773_, AbstractClientPlayer entity) {
        //noinspection UnstableApiUsage
        if (!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonArm(stack, bufferSource, p_117773_, entity, HumanoidArm.RIGHT)) {
            this.renderHand(stack, bufferSource, p_117773_, entity, this.model.getRightArmModel());
        }
    }

    @Override
    public void renderLeftHand(PoseStack stack, MultiBufferSource bufferSource, int p_117816_, AbstractClientPlayer entity) {
        //noinspection UnstableApiUsage
        if (!net.minecraftforge.client.ForgeHooksClient.renderSpecificFirstPersonArm(stack, bufferSource, p_117816_, entity, HumanoidArm.LEFT)) {
            this.renderHand(stack, bufferSource, p_117816_, entity, this.model.getLeftArmModel());
        }
    }

    @Override
    protected void setupSwimRotations(AbstractClientPlayer pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        float f3 = pEntityLiving.isInWater() || pEntityLiving.isInFluidType((fluidType, height) -> pEntityLiving.canSwimInFluidType(fluidType)) ? -70.0F - pEntityLiving.getXRot() : -70.0F;
        float f4 = Mth.lerp(pEntityLiving.getSwimAmount(pPartialTicks), 0.0F, f3);
        pMatrixStack.mulPose(Axis.XP.rotationDegrees(f4));
        if (pEntityLiving.isVisuallySwimming()) {
            pMatrixStack.translate(0.0F, -1.0F, 0.3F);
        }
    }
}