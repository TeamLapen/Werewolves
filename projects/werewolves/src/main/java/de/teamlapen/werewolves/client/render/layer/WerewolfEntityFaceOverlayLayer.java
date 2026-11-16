package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Renders the (always glowing) eyes of werewolf entities
 */
public class WerewolfEntityFaceOverlayLayer extends RenderLayer<WerewolfRenderState, WerewolfBaseModel> {

    private final Identifier[] eyeOverlays;

    public WerewolfEntityFaceOverlayLayer(RenderLayerParent<WerewolfRenderState, WerewolfBaseModel> entityRendererIn) {
        super(entityRendererIn);
        eyeOverlays = new Identifier[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < eyeOverlays.length; i++) {
            eyeOverlays[i] = WIdentifier.mod("textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int lightCoords, @NotNull WerewolfRenderState state, float yRot, float xRot) {
        if (state.isInvisible) return;
        int eyeType = Math.max(0, state.eyeType) % (eyeOverlays.length);
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, poseStack, RenderTypes.eyes(eyeOverlays[eyeType]), lightCoords, LivingEntityRenderer.getOverlayCoords(state, 0), -1, null, state.outlineColor, null);
    }
}
