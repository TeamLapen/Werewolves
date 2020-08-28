package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.entities.werewolf.HumanWerewolfEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HumanWerewolfRenderer extends LivingRenderer<HumanWerewolfEntity, PlayerModel<HumanWerewolfEntity>> {

    public HumanWerewolfRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new PlayerModel<>(0.95f, false), 0.5f);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nonnull HumanWerewolfEntity entity) {
        return null;
    }
}
