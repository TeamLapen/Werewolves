package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;

public class WerewolfSurvivalistRenderer<T extends BasicWerewolfEntity> extends WerewolfRenderer<T> {

    public WerewolfSurvivalistRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new WerewolfSurvivalistModel<>(), 1.0f, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
    }
}
