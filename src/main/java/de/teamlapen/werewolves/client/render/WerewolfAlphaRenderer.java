package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.entities.werewolf.WerewolfAlphaEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WerewolfAlphaRenderer extends WerewolfRenderer<WerewolfAlphaEntity> {

    public WerewolfAlphaRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new Werewolf4LModel<>(), 1.0F, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
    }

}
