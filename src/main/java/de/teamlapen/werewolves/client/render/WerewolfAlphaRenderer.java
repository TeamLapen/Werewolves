package de.teamlapen.werewolves.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.entities.werewolf.WerewolfAlphaEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;

public class WerewolfAlphaRenderer extends WerewolfRenderer<WerewolfAlphaEntity> {

    public WerewolfAlphaRenderer(EntityRenderDispatcher rendererManager) {
        super(rendererManager, new Werewolf4LModel<>(), 1.0F, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.endsWith(".png")).stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(ResourceLocation[]::new));
    }

    @Override
    protected void scale(WerewolfAlphaEntity entity, PoseStack stack, float p_225620_3_) {
        stack.scale(1.2f,1.2f,1.2f);
    }
}
