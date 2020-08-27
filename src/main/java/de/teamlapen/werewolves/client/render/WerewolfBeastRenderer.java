package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.entities.werewolf.WerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WerewolfBeastRenderer<T extends WerewolfEntity> extends WerewolfRenderer<T> {

    public WerewolfBeastRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WerewolfBeastModel<>(), 1.0f, Minecraft.getInstance().getResourceManager().getAllResourceLocations("textures/entity/werewolf/beast", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
    }
}
