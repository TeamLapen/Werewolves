package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WerewolfSurvivalistRenderer<T extends BasicWerewolfEntity> extends WerewolfRenderer<T> {

    public WerewolfSurvivalistRenderer(EntityRendererProvider.@NotNull Context context) {
        super(context, new WerewolfSurvivalistModel<>(context.bakeLayer(ModModelRender.WEREWOLF_SURVIVALIST)), 1.0f, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/survivalist", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
    }
}
