package de.teamlapen.werewolves.client.render.layer;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.state.WerewolfRenderState;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;

/**
 * Renders held items in the claws of the beast form
 */
public class BeastItemInHandLayer extends ItemInHandLayer<WerewolfRenderState, WerewolfBaseModel> {

    public BeastItemInHandLayer(RenderLayerParent<WerewolfRenderState, WerewolfBaseModel> renderer) {
        super(renderer);
    }
}
