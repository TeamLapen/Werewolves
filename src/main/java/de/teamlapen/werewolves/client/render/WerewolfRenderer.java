package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.core.WEntityRenderer;
import de.teamlapen.werewolves.client.model.WerewolfModel;
import de.teamlapen.werewolves.entities.WerewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class WerewolfRenderer extends LivingRenderer<WerewolfEntity, WerewolfModel<WerewolfEntity>> {

    public WerewolfRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new WerewolfModel<>(), 0.7f);
    }

    private ResourceLocation getWerewolfTexture(int entityId){
        return WEntityRenderer.werewolfTextures[entityId % WEntityRenderer.werewolfTextures.length];
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull WerewolfEntity entity) {
        return getWerewolfTexture(entity.getEntityId());
    }
}
