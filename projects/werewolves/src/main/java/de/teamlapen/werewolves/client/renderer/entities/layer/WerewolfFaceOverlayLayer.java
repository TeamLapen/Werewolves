package de.teamlapen.werewolves.client.renderer.entities.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.renderer.entities.WerewolfRenderState;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Renders the configurable eyes of werewolf players and minions. The eye type and glowing state are extracted for the rendered form into the {@link WerewolfRenderState}.
 */
public class WerewolfFaceOverlayLayer extends RenderLayer<WerewolfRenderState, WerewolfBaseModel> {

    private final Identifier[] eyeOverlays;

    public WerewolfFaceOverlayLayer(RenderLayerParent<WerewolfRenderState, WerewolfBaseModel> renderer) {
        super(renderer);
        this.eyeOverlays = new Identifier[REFERENCE.EYE_TYPE_COUNT];
        for (int i = 0; i < this.eyeOverlays.length; i++) {
            this.eyeOverlays[i] = WIdentifier.mod("textures/entity/werewolf/eye/eye_" + (i) + ".png");
        }
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int lightCoords, @NotNull WerewolfRenderState state, float yRot, float xRot) {
        if (state.isInvisible) return;
        int eyeType = Math.max(0, Math.min(state.eyeType, eyeOverlays.length - 1));
        Identifier texture = eyeOverlays[eyeType];
        submitNodeCollector.order(1).submitModel(this.getParentModel(), state, poseStack, state.glowingEyes ? RenderTypes.eyes(texture) : RenderTypes.entityCutout(texture), lightCoords, OverlayTexture.NO_OVERLAY, -1, null, state.outlineColor, null);
    }
}
