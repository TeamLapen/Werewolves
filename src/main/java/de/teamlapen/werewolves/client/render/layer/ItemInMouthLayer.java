package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemInMouthLayer<T extends LivingEntity, M extends WerewolfSurvivalistModel<T> & ArmedModel> extends ItemInHandLayer<T, M> {

    private final ItemInHandRenderer itemInHandRenderer;

    public ItemInMouthLayer(RenderLayerParent<T, M> p_234846_, ItemInHandRenderer p_234847_) {
        super(p_234846_, p_234847_);
        this.itemInHandRenderer = p_234847_;
    }

    @Override
    protected void renderArmWithItem(@NotNull LivingEntity entity, ItemStack itemStack, ItemTransforms.@NotNull TransformType transformType, @NotNull HumanoidArm arm, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int p_117191_) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();

            this.getParentModel().translateToMouth(arm, poseStack);
            poseStack.translate(0.0D, 0.0D, 0.125D);
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

            poseStack.translate(0.0D, 0.4D, 0.0D);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90));

            if (arm == HumanoidArm.LEFT) {
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
            this.itemInHandRenderer.renderItem(entity, itemStack, transformType, arm == HumanoidArm.LEFT, poseStack, bufferSource, p_117191_);
            poseStack.popPose();
        }
    }
}
