package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WerewolfSurvivalistRenderer<T extends BasicWerewolfEntity> extends WerewolfRenderer<T> {

    public WerewolfSurvivalistRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new WerewolfSurvivalistModel<>(), 1.0f, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
    }
}
