package de.teamlapen.werewolves.client.render;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nonnull;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, WerewolfBaseModel<AbstractClientPlayerEntity>> {

    private final Map<WerewolfFormUtil.Form, Triple<WerewolfBaseModel<AbstractClientPlayerEntity>, Float, ResourceLocation>> models = Maps.newHashMapWithExpectedSize(WerewolfFormUtil.Form.values().length);

    private ResourceLocation texture;

    public WerewolfPlayerRenderer(EntityRendererManager rendererManager) {
        //noinspection ConstantConditions
        super(rendererManager, null, 0f);
        this.models.put(WerewolfFormUtil.Form.NONE, Triple.of(null, 0f, null));
        this.models.put(WerewolfFormUtil.Form.HUMAN, Triple.of(new WerewolfEarsModel<>(), 0.5f, new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/human/werewolf_ear_claws.png")));
        this.models.put(WerewolfFormUtil.Form.BEAST, Triple.of(new WerewolfBeastModel<>(), 1.3f, new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/beast/beast_1.png")));
        this.models.put(WerewolfFormUtil.Form.SURVIVALIST, Triple.of(new WerewolfSurvivalistModel<>(), 0.5f, new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/survivalist/survivalist_1.png")));
    }

    public boolean render(WerewolfPlayer entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entity.getForm() != WerewolfFormUtil.Form.NONE) {
            this.switchModel(entity.getForm());
            render(((AbstractClientPlayerEntity) entity.getRepresentingPlayer()), entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            return entity.getForm() != WerewolfFormUtil.Form.HUMAN;
        }
        return false;
    }

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

    public void switchModel(WerewolfFormUtil.Form type) {
        this.entityModel = models.get(type).getLeft();
        this.shadowSize = models.get(type).getMiddle();
        this.texture = models.get(type).getRight();
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

    private BipedModel.ArmPose func_217766_a(AbstractClientPlayerEntity p_217766_1_, ItemStack p_217766_2_, ItemStack p_217766_3_, Hand p_217766_4_) {
        BipedModel.ArmPose bipedmodel$armpose = BipedModel.ArmPose.EMPTY;
        ItemStack itemstack = p_217766_4_ == Hand.MAIN_HAND ? p_217766_2_ : p_217766_3_;
        if (!itemstack.isEmpty()) {
            bipedmodel$armpose = BipedModel.ArmPose.ITEM;
            if (p_217766_1_.getItemInUseCount() > 0) {
                UseAction useaction = itemstack.getUseAction();
                if (useaction == UseAction.BLOCK) {
                    bipedmodel$armpose = BipedModel.ArmPose.BLOCK;
                } else if (useaction == UseAction.BOW) {
                    bipedmodel$armpose = BipedModel.ArmPose.BOW_AND_ARROW;
                } else if (useaction == UseAction.SPEAR) {
                    bipedmodel$armpose = BipedModel.ArmPose.THROW_SPEAR;
                } else if (useaction == UseAction.CROSSBOW && p_217766_4_ == p_217766_1_.getActiveHand()) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_CHARGE;
                }
            } else {
                boolean flag3 = p_217766_2_.getItem() == Items.CROSSBOW;
                boolean flag = CrossbowItem.isCharged(p_217766_2_);
                boolean flag1 = p_217766_3_.getItem() == Items.CROSSBOW;
                boolean flag2 = CrossbowItem.isCharged(p_217766_3_);
                if (flag3 && flag) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
                }

                if (flag1 && flag2 && p_217766_2_.getItem().getUseAction(p_217766_2_) == UseAction.NONE) {
                    bipedmodel$armpose = BipedModel.ArmPose.CROSSBOW_HOLD;
                }
            }
        }

        return bipedmodel$armpose;
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull AbstractClientPlayerEntity entity) {
        return this.texture;
    }
}
