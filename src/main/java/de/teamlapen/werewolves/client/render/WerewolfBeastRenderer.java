package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WerewolfBeastRenderer<T extends BasicWerewolfEntity> extends WerewolfRenderer<T> {

    public WerewolfBeastRenderer(EntityRenderDispatcher renderManagerIn) {
        super(renderManagerIn, new WerewolfBeastModel<>(), 1.0f, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
        this.addLayer(new CustomHeadLayer<>(this));
    }
}
