package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.HumanWerewolfRenderer;
import de.teamlapen.werewolves.client.render.WerewolfBeastRenderer;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.WerewolfSurvivalistRenderer;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class WEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void registerEntityRenderer() {
        render = new WerewolfPlayerRenderer(Minecraft.getInstance().getRenderManager());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.werewolf_beast, WerewolfBeastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.werewolf_survivalist, WerewolfSurvivalistRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.human_werewolf, HumanWerewolfRenderer::new);
    }
}
