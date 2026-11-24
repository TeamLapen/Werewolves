package de.teamlapen.werewolves.client.renderer.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.client.core.ModModelRender;
import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.common.world.entities.werewolf.WerewolfAlphaEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class WerewolfAlphaRenderer extends WerewolfRenderer<WerewolfAlphaEntity> {

    public WerewolfAlphaRenderer(EntityRendererProvider.Context context) {
        super(context, new Werewolf4LModel(context.bakeLayer(ModModelRender.WEREWOLF_4L)), 1.0F, Minecraft.getInstance().getResourceManager().listResources("textures/entity/werewolf/beast", s -> s.getPath().endsWith(".png")).keySet().stream().filter(r -> REFERENCE.MODID.equals(r.getNamespace())).toArray(Identifier[]::new));
    }

    @Override
    protected void scale(WerewolfRenderState state, PoseStack stack) {
        stack.scale(1.2f, 1.2f, 1.2f);
    }
}
