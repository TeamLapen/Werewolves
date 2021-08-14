package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.client.render.layer.WerewolfEntityFaceOverlayLayer;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WerewolfRenderer<T extends BasicWerewolfEntity> extends MobRenderer<T, WerewolfBaseModel<T>> {

    private final ResourceLocation[] textures;

    public WerewolfRenderer(EntityRendererManager renderManagerIn, WerewolfBaseModel<T> entityModelIn, float shadowSizeIn, ResourceLocation[] textures) {
        super(renderManagerIn, entityModelIn, shadowSizeIn);
        this.addLayer(new WerewolfEntityFaceOverlayLayer<>(this));
        this.textures = textures;
    }

    public ResourceLocation getWerewolfTexture(int entityId) {
        return this.textures[entityId % textures.length];
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull T entity) {
        return this.getWerewolfTexture(entity.getEntityTextureType());
    }
}
