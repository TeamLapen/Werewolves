package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.entities.werewolf.TransformedWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class TransformedWerewolfRenderer extends MobRenderer<TransformedWerewolfEntity, WerewolfBeastModel<TransformedWerewolfEntity>> {

    private final ResourceLocation[] textures;

    public TransformedWerewolfRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new WerewolfBeastModel<>(), 0.7f);
        textures = Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/werewolf", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new);
    }

    private ResourceLocation getWerewolfTexture(int entityId) {
        return textures[entityId % textures.length];
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull TransformedWerewolfEntity entity) {
        return getWerewolfTexture(entity.getEntityId());
    }
}
