package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.render.*;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ModModelRender {

    public static final ModelLayerLocation WEREWOLF_BEAST = new ModelLayerLocation(new ResourceLocation(REFERENCE.MODID, "werewolf_beast"), "main");
    public static final ModelLayerLocation WEREWOLF_SURVIVALIST = new ModelLayerLocation(new ResourceLocation(REFERENCE.MODID, "werewolf_survivalist"), "main");
    public static final ModelLayerLocation WEREWOLF_4L = new ModelLayerLocation(new ResourceLocation(REFERENCE.MODID, "werewolf_four_legged"), "main");
    public static final ModelLayerLocation EARS_CLAWS = new ModelLayerLocation(new ResourceLocation(REFERENCE.MODID, "ears_claws"), "main");

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.alpha_werewolf, WerewolfAlphaRenderer::new);
        event.registerEntityRenderer(ModEntities.werewolf_beast, WerewolfBeastRenderer::new);
        event.registerEntityRenderer(ModEntities.werewolf_survivalist, WerewolfSurvivalistRenderer::new);
        event.registerEntityRenderer(ModEntities.human_werewolf, HumanWerewolfRenderer::new);
        event.registerEntityRenderer(ModEntities.wolf, WolfRenderer::new);
        event.registerEntityRenderer(ModEntities.task_master_werewolf, WerewolfTaskMasterRenderer::new);
        event.registerEntityRenderer(ModEntities.werewolf_minion, WerewolfMinionRenderer::new);
    }

    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EARS_CLAWS, WerewolfEarsModel::createBodyLayer);
        event.registerLayerDefinition(WEREWOLF_4L, Werewolf4LModel::createBodyLayer);
        event.registerLayerDefinition(WEREWOLF_BEAST, WerewolfBeastModel::createBodyLayer);
        event.registerLayerDefinition(WEREWOLF_SURVIVALIST, WerewolfSurvivalistModel::createBodyLayer);
    }
}
