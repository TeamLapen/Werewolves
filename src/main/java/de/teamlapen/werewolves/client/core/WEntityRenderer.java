package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.WerewolfRenderer;
import de.teamlapen.werewolves.entities.WerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class WEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void registerEntityRenderer() {
        render = new WerewolfPlayerRenderer(Minecraft.getInstance().getRenderManager());
        RenderingRegistry.registerEntityRenderingHandler(WerewolfEntity.class, WerewolfRenderer::new);
    }
}
