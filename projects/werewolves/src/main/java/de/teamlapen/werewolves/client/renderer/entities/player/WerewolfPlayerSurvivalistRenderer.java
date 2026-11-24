package de.teamlapen.werewolves.client.renderer.entities.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.renderer.entities.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.renderer.entities.layer.SurvivalItemInMouthLayer;
import de.teamlapen.werewolves.client.renderer.entities.layer.WerewolfFaceOverlayLayer;
import de.teamlapen.werewolves.client.renderer.entities.WerewolfRenderState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class WerewolfPlayerSurvivalistRenderer extends WerewolfPlayerRenderer {

    public WerewolfPlayerSurvivalistRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfSurvivalistModel(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)), 1F, WerewolfForm.SURVIVALIST, WerewolfSurvivalistModel.getSurvivalTextures());
        this.addLayer(new WerewolfFaceOverlayLayer(this));
        this.addLayer(new SurvivalItemInMouthLayer(this));
    }

    @Override
    public void renderHand(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, AbstractClientPlayer player, HumanoidArm arm) {
        // the survivalist form has no visible hands in first person
    }

    @Override
    protected void setupSwimRotations(WerewolfRenderState state, PoseStack poseStack) {
        float f3 = state.isInWater ? -10.0F - state.xRot : -10.0F;
        float f4 = Mth.lerp(state.swimAmount, 0.0F, f3);
        poseStack.mulPose(Axis.XP.rotationDegrees(f4));
    }
}
