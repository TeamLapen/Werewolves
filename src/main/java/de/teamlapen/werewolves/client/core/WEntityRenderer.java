package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.*;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.wolf, WolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.task_master_werewolf, WerewolfTaskMasterRenderer::new);
    }
}
