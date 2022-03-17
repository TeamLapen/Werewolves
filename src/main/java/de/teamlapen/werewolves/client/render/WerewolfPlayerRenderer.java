package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends BaseWerewolfRenderer<AbstractClientPlayer> {

    private boolean skipPlayerModel;

    public WerewolfPlayerRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, 0.5f);
    }

    @Override
    public void switchModel(WerewolfForm type) {
        if (this.form == type) return;
        super.switchModel(type);
        this.skipPlayerModel = getWrapper(type).skipPlayerModel;
    }

    private void setModelVisible(@Nonnull AbstractClientPlayer clientPlayer) {
        WerewolfBaseModel<AbstractClientPlayer> playerModel = this.getModel();
        if (clientPlayer.isSpectator()) {
            playerModel.setAllVisible(false);
            playerModel.head.visible = true;
        } else {
            playerModel.setAllVisible(true);
            playerModel.crouching = clientPlayer.isCrouching();
        }
    }

    public boolean renderRightArm(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn) {
        WerewolfForm form = WerewolfPlayer.get(playerIn).getForm();
        if (!form.isHumanLike()) {
            form = WerewolfForm.BEAST;
        }
        this.switchModel(form);
        ModelPart arm = this.getModel().getRightArmModel();
        if (arm != null) {
            if (shouldRenderArm(HumanoidArm.RIGHT, playerIn)) {
                matrixStackIn.pushPose();
                matrixStackIn.scale(1.2f, 1f, 1.2f);
                matrixStackIn.translate(0, 0.2, 0.4);
                this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, arm);
                matrixStackIn.popPose();
            } else {
                return false;
            }
        }
        return !form.isHumanLike();
    }

    public boolean renderLeftArm(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn) {
        WerewolfForm form = WerewolfPlayer.get(playerIn).getForm();
        if (!form.isHumanLike()) {
            form = WerewolfForm.BEAST;
        }
        this.switchModel(form);
        ModelPart arm = this.getModel().getLeftArmModel();
        if (arm != null) {

            if (shouldRenderArm(HumanoidArm.LEFT, playerIn)) {
                matrixStackIn.pushPose();
                matrixStackIn.scale(1.2f, 1f, 1.2f);
                matrixStackIn.translate(0, 0.2, 0.4);
                this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, arm);
                matrixStackIn.popPose();
            } else {
                return false;
            }
        }
        return !form.isHumanLike();
    }

    private boolean shouldRenderArm(HumanoidArm armSide, @Nonnull AbstractClientPlayer player) {
        HumanoidArm side = player.getMainArm();
        ItemStack mainStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offStack = player.getItemInHand(InteractionHand.OFF_HAND);

        if (armSide == side) {
            return mainStack.getItem() != Items.FILLED_MAP;
        } else {
            return !(offStack.getItem() == Items.FILLED_MAP || (offStack.isEmpty() && mainStack.getItem() == Items.FILLED_MAP));
        }
    }

    private void renderItem(PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, AbstractClientPlayer playerIn, @Nonnull ModelPart rendererArmIn) {
        WerewolfBaseModel<AbstractClientPlayer> model = this.getModel();
        this.setModelVisible(playerIn);
        model.attackTime = 0F;
        model.crouching = false;
        model.swimAmount = 0F;
        //model.setupAnim(playerIn, 0, 0, 0, 0, 0);
//        model.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entitySolid(this.getTextureLocation(playerIn))), combinedLightIn, OverlayTexture.NO_OVERLAY, 1,1,1,1);
//        rendererArmIn.xRot = 0F;
        rendererArmIn.render(matrixStackIn, bufferIn.getBuffer(RenderType.entitySolid(this.getTextureLocation(playerIn))), combinedLightIn, OverlayTexture.NO_OVERLAY);
    }

    /**
     * @return if the player model should be renderer
     */
    public boolean render(@Nonnull WerewolfPlayer entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        WerewolfForm form = entity.getForm();
        if (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen) {
            form = ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).getActiveForm();
        }
        this.switchModel(form);
        if (this.model != null && this.skipPlayerModel) {
            this.render(((AbstractClientPlayer) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            return true;
        }
        return false;
    }

    public void renderPost(PlayerModel<AbstractClientPlayer> entityModel, WerewolfPlayer entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        if (this.model != null && !this.skipPlayerModel) {
            this.model.setPlayerModel(entityModel);
            render(((AbstractClientPlayer) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    /**
     * use {@link #render(WerewolfPlayer, float, float, MatrixStack, IRenderTypeBuffer, int)}
     */
    @Deprecated
    @Override
    public void render(@Nonnull AbstractClientPlayer entity, float entityYaw, float partialTicks, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int packedLightIn) {
        if (!entity.isLocalPlayer() || this.entityRenderDispatcher.camera.getEntity() == entity) {
            this.setModelVisible(entity);
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    public WerewolfForm getForm() {
        return form;
    }
}
