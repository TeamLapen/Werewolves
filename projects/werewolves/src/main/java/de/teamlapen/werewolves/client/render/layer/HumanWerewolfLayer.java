package de.teamlapen.werewolves.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.render.state.WerewolvesRenderStates;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * Renders the werewolf ears and claws of the human form.
 * The texture is set by {@link WerewolvesRenderStates#HUMAN_FORM_TEXTURE} if the human form should be rendered
 */
public class HumanWerewolfLayer<S extends HumanoidRenderState, M extends HumanoidModel<S>> extends RenderLayer<S, M> {

    private final WerewolfEarsModel<S> model;

    public HumanWerewolfLayer(RenderLayerParent<S, M> renderer, WerewolfEarsModel<S> model) {
        super(renderer);
        this.model = model;
    }

    @Override
    public void submit(@NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, int lightCoords, @NotNull S state, float yRot, float xRot) {
        Identifier texture = state.getRenderData(WerewolvesRenderStates.HUMAN_FORM_TEXTURE);
        if (texture != null) {
            coloredCutoutModelCopyLayerRender(this.model, texture, poseStack, submitNodeCollector, lightCoords, state, -1, 1);
        }
    }
}
