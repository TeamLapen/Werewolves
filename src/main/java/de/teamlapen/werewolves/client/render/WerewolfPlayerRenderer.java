package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public abstract class WerewolfPlayerRenderer<E extends PlayerModel<AbstractClientPlayer>> extends PlayerRenderer {

    public static Optional<String> getWerewolfRenderer(AbstractClientPlayer player) {
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            WerewolfForm form = werewolf.getForm();
            if (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen && ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).isRenderForm()) {
                form = ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).getActiveForm();
            }
            if (form == WerewolfForm.BEAST) {
                return Optional.of(REFERENCE.MODID+":beast");
            } else if (form == WerewolfForm.SURVIVALIST) {
                return Optional.of(REFERENCE.MODID+":survivalist");
            }
        }
        return Optional.empty();
    }

    public WerewolfPlayerRenderer(EntityRendererProvider.Context context, E model, float shadowRadius) {
        super(context, false);
        this.layers.clear();
        this.model = model;
        this.shadowRadius = shadowRadius;
        this.addLayer(new ArrowLayer<>(context, this));
        this.addLayer(new BeeStingerLayer<>(this));
        this.addLayer(new SpinAttackEffectLayer<>(this, context.getModelSet()));

    }

    @Override
    public @NotNull Vec3 getRenderOffset(@NotNull AbstractClientPlayer pEntity, float pPartialTicks) {
        return Vec3.ZERO;
    }

    @SuppressWarnings("unchecked")
    public RenderLayerParent<AbstractClientPlayer, E> cast() {
        return ((RenderLayerParent<AbstractClientPlayer, E>) this);
    }

    public void addWerewolfLayer(RenderLayer<AbstractClientPlayer, E> pLayer) {
        this.layers.add((RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>) pLayer);
    }

    @Override
    public abstract ResourceLocation getTextureLocation(AbstractClientPlayer pEntity);

    @Override
    public abstract void renderRightHand(PoseStack stack, MultiBufferSource bufferSource, int p_117773_, AbstractClientPlayer entity);

    @Override
    public abstract void renderLeftHand(PoseStack stack, MultiBufferSource bufferSource, int p_117816_, AbstractClientPlayer entity);

    protected void renderHand(PoseStack stack, MultiBufferSource bufferSource, int p_117778_, AbstractClientPlayer entity, ModelPart arm) {
        PlayerModel<AbstractClientPlayer> model = this.getModel();
        model.attackTime = 0.0F;
        model.crouching = false;
        model.swimAmount = 0.0F;
        model.setupAnim(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        arm.xRot = 0.0F;
        arm.render(stack, bufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), p_117778_, OverlayTexture.NO_OVERLAY);
    }

    @Override
    protected void setupRotations(AbstractClientPlayer pEntityLiving, @NotNull PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        float f = pEntityLiving.getSwimAmount(pPartialTicks);
        if (pEntityLiving.isFallFlying()) {
            super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        } else if (f > 0) {
            super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
            if (pEntityLiving.isVisuallySwimming()) {
                pMatrixStack.translate(0,1,-0.3);
            }
            float f3 = pEntityLiving.isInWater() || pEntityLiving.isInFluidType((fluidType, height) -> pEntityLiving.canSwimInFluidType(fluidType)) ? -90.0F - pEntityLiving.getXRot() : -90.0F;
            float f4 = Mth.lerp(f, 0.0F, f3);
            pMatrixStack.mulPose(Axis.XN.rotationDegrees(f4));
            setupSwimRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        } else {
            super.setupRotations(pEntityLiving, pMatrixStack, pAgeInTicks, pRotationYaw, pPartialTicks);
        }
    }

    protected abstract void setupSwimRotations(AbstractClientPlayer pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks);
}
