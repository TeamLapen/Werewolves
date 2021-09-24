package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.entities.werewolf.HumanWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class HumanWerewolfRenderer extends MobRenderer<HumanWerewolfEntity, PlayerModel<HumanWerewolfEntity>> {

    private final ResourceLocation[] textures;

    public HumanWerewolfRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new PlayerModel<>(0.0f, false), 0.5f);
        this.textures = Minecraft.getInstance().getResourceManager().listResources("textures/entity/human", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new);
    }

    public ResourceLocation getHumanTexture(int entityId) {
        return this.textures[entityId % textures.length];
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull HumanWerewolfEntity entity) {
        return this.getHumanTexture(entity.getSkinType());
    }
}
