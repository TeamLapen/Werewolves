package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.TransformedWerewolfRenderer;
import de.teamlapen.werewolves.client.render.WerewolfBeastRenderer;
import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import de.teamlapen.werewolves.client.render.WerewolfSurvivalistRenderer;
import de.teamlapen.werewolves.entities.PermanentWerewolfEntity;
import de.teamlapen.werewolves.entities.TransformedWerewolfEntity;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

@OnlyIn(Dist.CLIENT)
public class WEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void registerEntityRenderer() {
        render = new WerewolfPlayerRenderer(Minecraft.getInstance().getRenderManager());
        RenderingRegistry.registerEntityRenderingHandler(TransformedWerewolfEntity.class, TransformedWerewolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(PermanentWerewolfEntity.Beast.class, WerewolfBeastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(PermanentWerewolfEntity.Survivalist.class, WerewolfSurvivalistRenderer::new);
    }
}
