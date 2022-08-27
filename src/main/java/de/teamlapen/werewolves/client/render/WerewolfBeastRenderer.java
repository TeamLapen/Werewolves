package de.teamlapen.werewolves.client.render;

import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WerewolfBeastRenderer<T extends BasicWerewolfEntity> extends WerewolfRenderer<T> {

    public WerewolfBeastRenderer(EntityRendererProvider.@NotNull Context context) {
        super(context, new WerewolfBeastModel<>(context.bakeLayer(ModModelRender.WEREWOLF_BEAST)), 1.0f, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
    }
}
