package de.teamlapen.werewolves.client.renderer.entities;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.renderer.entities.layer.WerewolfEntityFaceOverlayLayer;
import de.teamlapen.werewolves.common.world.entities.werewolf.WerewolfBaseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class WerewolfRenderer<T extends WerewolfBaseEntity> extends MobRenderer<T, WerewolfRenderState, WerewolfBaseModel> {

    private final Identifier[] textures;

    public WerewolfRenderer(EntityRendererProvider.Context context, WerewolfBaseModel entityModelIn, float shadowSizeIn, Identifier[] textures) {
        super(context, entityModelIn, shadowSizeIn);
        this.addLayer(new WerewolfEntityFaceOverlayLayer(this));
        this.textures = textures;
    }

    public Identifier getWerewolfTexture(int entityId) {
        return this.textures[Math.max(0, entityId) % textures.length];
    }

    @Override
    public WerewolfRenderState createRenderState() {
        return new WerewolfRenderState();
    }

    @Override
    public void extractRenderState(T entity, WerewolfRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        WerewolfRenderState.extract(entity, entity, entity.getForm(), state, partialTicks, this.itemModelResolver);
        state.texture = getWerewolfTexture(state.skinType);
    }

    @Override
    public Identifier getTextureLocation(WerewolfRenderState state) {
        return state.texture;
    }
}
