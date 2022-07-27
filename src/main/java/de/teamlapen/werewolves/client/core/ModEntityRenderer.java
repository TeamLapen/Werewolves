package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.*;
import de.teamlapen.werewolves.core.ModEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.apache.logging.log4j.LogManager;

@OnlyIn(Dist.CLIENT)
public class ModEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void registerEntityRenderer() {
        render = new WerewolfPlayerRenderer(Minecraft.getInstance().getEntityRenderDispatcher());
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WEREWOLF_BEAST.get(), WerewolfBeastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WEREWOLF_SURVIVALIST.get(), WerewolfSurvivalistRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HUMAN_WEREWOLF.get(), HumanWerewolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WOLF.get(), WolfRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.TASK_MASTER_WEREWOLF.get(), WerewolfTaskMasterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WEREWOLF_MINION.get(), safeFactory(WerewolfMinionRenderer::new));
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ALPHA_WEREWOLF.get(), WerewolfAlphaRenderer::new);
    }

    private static <T extends Entity> IRenderFactory<? super T> safeFactory(IRenderFactory<? super T> f) {
        return (IRenderFactory<T>) manager -> {
            try {
                return f.createRenderFor(manager);
            } catch (Exception e) {
                LogManager.getLogger().error("Failed to instantiate entity renderer", e);
                System.exit(0);
                throw e;
            }
        };
    }
}
