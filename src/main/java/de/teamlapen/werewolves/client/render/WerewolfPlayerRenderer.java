package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends BaseWerewolfRenderer<AbstractClientPlayerEntity> {

    private boolean skipPlayerModel;

    public WerewolfPlayerRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, 0f);
    }

    @Override
    public void switchModel(WerewolfForm type) {
        if (this.form == type) return;
        super.switchModel(type);
        this.skipPlayerModel = getWrapper(type).skipPlayerModel;
    }

    private void setModelVisible(AbstractClientPlayerEntity clientPlayer) {
        WerewolfBaseModel<AbstractClientPlayerEntity> playerModel = this.getModel();
        if (clientPlayer.isSpectator()) {
            playerModel.setAllVisible(false);
            playerModel.head.visible = true;
        } else {
            playerModel.setAllVisible(true);
            playerModel.crouching = clientPlayer.isCrouching();
        }
    }

    public boolean renderRightArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn) {
        WerewolfForm form = WerewolfPlayer.get(playerIn).getForm();
        if (form == WerewolfForm.SURVIVALIST) return true;
        this.switchModel(form);
        ModelRenderer arm = this.getModel().getRightArmModel();
        if (arm != null) {
            if (form == WerewolfForm.BEAST) {
                matrixStackIn.pushPose();
                matrixStackIn.scale(1.2f, 1f, 1.2f);
                matrixStackIn.translate(0, 0.2, 0.4);
                this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, arm);
                matrixStackIn.popPose();
            }
        }
        return !form.isHumanLike();
    }

    public void renderLeftArm(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn) {
        WerewolfForm form = WerewolfPlayer.get(playerIn).getForm();
        if (!form.isHumanLike()) {
            form = WerewolfForm.BEAST;
        }
        this.switchModel(form);
        ModelRenderer arm = this.getModel().getLeftArmModel();
        if (arm != null) {
            matrixStackIn.scale(1.2f,1f,1.2f);
            matrixStackIn.translate(0,0.2,-0.4);
            this.renderItem(matrixStackIn, bufferIn, combinedLightIn, playerIn, arm);
        }
    }

    private void renderItem(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, AbstractClientPlayerEntity playerIn, ModelRenderer rendererArmIn) {
        WerewolfBaseModel<AbstractClientPlayerEntity> model = this.getModel();
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
    public boolean render(WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        WerewolfForm form = entity.getForm();
        if (Minecraft.getInstance().screen instanceof WerewolfPlayerAppearanceScreen) {
            form = ((WerewolfPlayerAppearanceScreen) Minecraft.getInstance().screen).getActiveForm();
        }
        this.switchModel(form);
        if (this.model != null && this.skipPlayerModel) {
                this.render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
                return true;
        }
        return false;
    }

    public void renderPost(PlayerModel<AbstractClientPlayerEntity> entityModel, WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (this.model != null && !this.skipPlayerModel) {
            this.model.setPlayerModel(entityModel);
            render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    /**
     * use {@link #render(WerewolfPlayer, float, float, MatrixStack, IRenderTypeBuffer, int)}
     */
    @Deprecated
    @Override
    public void render(AbstractClientPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (!entity.isLocalPlayer() || this.entityRenderDispatcher.camera.getEntity() == entity) {
            this.setModelVisible(entity);
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    public WerewolfForm getForm() {
        return form;
    }
}
