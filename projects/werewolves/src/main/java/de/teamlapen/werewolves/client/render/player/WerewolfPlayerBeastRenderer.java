package de.teamlapen.werewolves.client.render.player;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.layer.BeastItemInHandLayer;
import de.teamlapen.werewolves.client.render.layer.WerewolfFaceOverlayLayer;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;

public class WerewolfPlayerBeastRenderer extends WerewolfPlayerRenderer {

    public WerewolfPlayerBeastRenderer(EntityRendererProvider.Context context) {
        super(context, new WerewolfBeastModel(context.bakeLayer(ModModelRender.WEREWOLF_BEAST)), 1F, WerewolfForm.BEAST, WerewolfBeastModel.getBeastTextures());
        this.addLayer(new WerewolfFaceOverlayLayer(this));
        this.addLayer(new BeastItemInHandLayer(this));
    }

    @Override
    protected void setupSwimRotations(WerewolfRenderState state, PoseStack poseStack) {
        float f3 = state.isInWater ? -70.0F - state.xRot : -70.0F;
        float f4 = Mth.lerp(state.swimAmount, 0.0F, f3);
        poseStack.mulPose(Axis.XP.rotationDegrees(f4));
        if (state.isVisuallySwimming) {
            poseStack.translate(0.0F, -1.0F, 0.3F);
        }
    }
}
