package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBaseModel;
import de.teamlapen.werewolves.entities.WerewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class WerewolfRenderer<T extends WerewolfEntity> extends MobRenderer<T, WerewolfBaseModel<T>> {

    private final ResourceLocation[] textures;

    public WerewolfRenderer(EntityRendererManager renderManagerIn, WerewolfBaseModel<T> entityModelIn, float shadowSizeIn, ResourceLocation[] textures) {
        super(renderManagerIn, entityModelIn, shadowSizeIn);
        this.textures = textures;
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull T entity) {
        return textures[entity.getEntityId() % textures.length];
    }
}
