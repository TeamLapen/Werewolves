package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.entities.werewolf.WerewolfAlphaEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class WerewolfAlphaRenderer extends MobRenderer<WerewolfAlphaEntity, Werewolf4LModel<WerewolfAlphaEntity>> {

    public WerewolfAlphaRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new Werewolf4LModel<>(), 2.0F);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull WerewolfAlphaEntity werewolf) {
        return new ResourceLocation(REFERENCE.MODID,"textures/entity/werewolf/beast/beast_0.png");
    }
}
