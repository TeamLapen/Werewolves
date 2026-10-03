package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.mixin.client.ItemInHandRendererMixin;
import de.teamlapen.werewolves.mixin.client.PlayerItemInHandLayerAccessor;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BeastItemInHandLayer<T extends Player, M extends WerewolfBeastModel<T> & ArmedModel & HeadedModel> extends PlayerItemInHandLayer<T,M> {

    public BeastItemInHandLayer(RenderLayerParent<T, M> renderer, ItemInHandRenderer itemRenderer) {
        super(renderer, itemRenderer);
    }

    public void renderArmWithSpyglass(@NotNull LivingEntity entity, @NotNull ItemStack itemStack, @NotNull HumanoidArm arm, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int p_174523_) {
        poseStack.pushPose();
        ModelPart modelpart = this.getParentModel().getHead();
        float f = modelpart.xRot;

        modelpart.xRot = Mth.clamp(modelpart.xRot, (-(float)Math.PI / 6F), ((float)Math.PI / 2F));
        this.getParentModel().translateToHead(poseStack);

        modelpart.xRot = f;
        boolean flag = arm == HumanoidArm.LEFT;
        poseStack.translate((flag ? -2.5F : 2.5F) / 16.0F, -0.0625D, 0.0D);
        poseStack.scale(0.7f,0.7f,0.7f);
        ((PlayerItemInHandLayerAccessor)this).getItemInHandRenderer().renderItem(entity, itemStack, ItemDisplayContext.HEAD, false, poseStack, bufferSource, p_174523_);
        poseStack.popPose();
    }

    @Override
    protected void renderArmWithItem(@NotNull LivingEntity pLivingEntity, @NotNull ItemStack pItemStack, @NotNull ItemDisplayContext pDisplayContext, @NotNull HumanoidArm pArm, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        super.renderArmWithItem(pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight);

    }
}
