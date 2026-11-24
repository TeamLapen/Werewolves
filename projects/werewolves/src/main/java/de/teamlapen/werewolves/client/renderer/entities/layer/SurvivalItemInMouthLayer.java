package de.teamlapen.werewolves.client.renderer.entities.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.renderer.entities.WerewolfRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Renders held items in the mouth of the survivalist form
 */
public class SurvivalItemInMouthLayer extends ItemInHandLayer<WerewolfRenderState, WerewolfBaseModel> {

    public SurvivalItemInMouthLayer(RenderLayerParent<WerewolfRenderState, WerewolfBaseModel> renderer) {
        super(renderer);
    }

    @Override
    protected void submitArmWithItem(@NotNull WerewolfRenderState state, @NotNull ItemStackRenderState item, @NotNull ItemStack itemStack, @NotNull HumanoidArm arm, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int lightCoords) {
        if (!item.isEmpty() && this.getParentModel() instanceof WerewolfSurvivalistModel model) {
            poseStack.pushPose();

            model.translateToMouth(arm, poseStack);
            poseStack.translate(0.0D, 0.0D, 0.125D);
            poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            poseStack.translate(0.0D, 0.4D, 0.0D);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90));

            if (arm == HumanoidArm.LEFT) {
                poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            }
            item.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
            poseStack.popPose();
        }
    }
}
