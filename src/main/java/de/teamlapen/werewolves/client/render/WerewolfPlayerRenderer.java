package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class WerewolfPlayerRenderer extends LivingRenderer<AbstractClientPlayerEntity, WerewolfBeastModel<AbstractClientPlayerEntity>> {

    public WerewolfPlayerRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new WerewolfBeastModel<>(), 1.3f);
    }

    @Override
    public void doRender(AbstractClientPlayerEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!entity.isUser() || this.renderManager.info.getRenderViewEntity() == entity) {
            if (entity.shouldRenderSneaking()) {
                y = y - 0.125D;
            }
            this.setModelVisible(entity);
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
        }
    }

    private void setModelVisible(AbstractClientPlayerEntity clientPlayer) {
        WerewolfBeastModel<AbstractClientPlayerEntity> playerModel = this.getEntityModel();
        if (clientPlayer.isSpectator()) {
            playerModel.setVisible(false);
        } else {
            playerModel.setVisible(true);
            playerModel.setSneak(clientPlayer.shouldRenderSneaking());
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

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull AbstractClientPlayerEntity entity) {
        return new ResourceLocation(REFERENCE.MODID, "textures/entity/werewolf/beast_1.png");
    }
}
