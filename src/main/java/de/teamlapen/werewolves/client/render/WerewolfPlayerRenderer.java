package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.player.WerewolfForm;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>> {

    private static class WerewolfModelWrapper {
        private final WerewolfBaseModel<AbstractClientPlayerEntity> model;
        private final ResourceLocation texture;
        private final float shadow;
        private final boolean skipPlayerModel;

        public WerewolfModelWrapper(WerewolfBaseModel<AbstractClientPlayerEntity> model, ResourceLocation texture, float shadow, boolean skipPlayerModel) {
            this.model = model;
            this.texture = texture;
            this.shadow = shadow;
            this.skipPlayerModel = skipPlayerModel;
        }
    }

    private static final Map<WerewolfForm, WerewolfModelWrapper> MODELS = new HashMap<>();

    static {
        addModel(WerewolfForm.NONE, new WerewolfModelWrapper(null, null, 0, false));
        addModel(WerewolfForm.HUMAN, new WerewolfModelWrapper(new WerewolfEarsModel<>(), new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/human/werewolf_ear_claws.png"), 0.5f, false));
        addModel(WerewolfForm.BEAST, new WerewolfModelWrapper(new WerewolfBeastModel<>(), new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/beast/beast_1.png"), 1.3f, true));
        addModel(WerewolfForm.SURVIVALIST, new WerewolfModelWrapper(new WerewolfSurvivalistModel<>(), new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/survivalist/survivalist_1.png"), 0.5f, true));
    }

    public static void addModel(WerewolfForm form, WerewolfModelWrapper render) {
        MODELS.put(form, render);
    }

    private ResourceLocation texture;
    private boolean skipPlayerModel;

    public WerewolfPlayerRenderer(EntityRendererManager rendererManager) {
        //noinspection ConstantConditions
        super(rendererManager, null, 0f);
    }

    public void switchModel(WerewolfForm type) {
        WerewolfModelWrapper werewolfModelWrapper = MODELS.get(type);
        this.entityModel = werewolfModelWrapper.model;
        this.shadowSize = werewolfModelWrapper.shadow;
        this.texture = werewolfModelWrapper.texture;
        this.skipPlayerModel = werewolfModelWrapper.skipPlayerModel;
    }

    /**
     * @returns if the player model should be renderer
     */
    public boolean render(WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.switchModel(entity.getForm());
        if (this.entityModel != null) {
            render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            return skipPlayerModel;
        }
        return false;
    }

    /**
     * use {@link #render(WerewolfPlayer, float, float, MatrixStack, IRenderTypeBuffer, int)}
     */
    @Deprecated
    @Override
    public void render(AbstractClientPlayerEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

        if (!entity.isUser() || this.renderManager.info.getRenderViewEntity() == entity) {
            if (entity.isCrouching()) {
//                y = y - 0.125D;
            }
            this.setModelVisible(entity);
            super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        }
    }

    private void setModelVisible(AbstractClientPlayerEntity clientPlayer) {
        WerewolfBaseModel<AbstractClientPlayerEntity> playerModel = this.getEntityModel();
        if (clientPlayer.isSpectator()) {
            playerModel.setVisible(false);
        } else {
            playerModel.setVisible(true);
            playerModel.setSneak(clientPlayer.isCrouching());
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull AbstractClientPlayerEntity entity) {
        return this.texture;
    }

//    private BipedModel.ArmPose func_217766_a(AbstractClientPlayerEntity p_217766_1_, ItemStack p_217766_2_, ItemStack p_217766_3_, Hand p_217766_4_) {
//        BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
//        ItemStack itemstack = p_217766_4_ == Hand.MAIN_HAND ? p_217766_2_ : p_217766_3_;
//        if (!itemstack.isEmpty()) {
//            bipedmodel$armpose = BipedModel.ArmPose.ITEM;
//            if (p_217766_1_.getItemInUseCount() > 0) {
//                UseAction useaction = itemstack.getUseAction();
//                if (useaction == UseAction.BLOCK) {
//                    bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
//                } else if (useaction == UseAction.BOW) {
//                    bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
//                } else if (useaction == UseAction.SPEAR) {
//                    bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
//                } else if (useaction == UseAction.CROSSBOW && p_217766_4_ == p_217766_1_.getActiveHand()) {
//                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
//                }
//            } else {
//                boolean flag3 = p_217766_2_.getItem() == Items.CROSSBOW;
//                boolean flag = CrossbowItem.isCharged(p_217766_2_);
//                boolean flag1 = p_217766_3_.getItem() == Items.CROSSBOW;
//                boolean flag2 = CrossbowItem.isCharged(p_217766_3_);
//                if (flag3 && flag) {
//                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
//                }
//
//                if (flag1 && flag2 && p_217766_2_.getItem().getUseAction(p_217766_2_) == UseAction.NONE) {
//                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
//                }
//            }
//        }
//
//        return bipedmodel$armpose;
//    }
}
