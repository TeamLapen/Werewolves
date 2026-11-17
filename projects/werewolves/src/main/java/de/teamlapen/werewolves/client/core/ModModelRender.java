package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.client.model.Werewolf4LModel;
import de.teamlapen.werewolves.client.model.WerewolfBeastModel;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.model.WerewolfSurvivalistModel;
import de.teamlapen.werewolves.client.model.armor.WolfHeadModel;
import de.teamlapen.werewolves.client.render.*;
import de.teamlapen.werewolves.common.core.ModEntities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.ApiStatus;

public class ModModelRender {

    public static final ModelLayerLocation WEREWOLF_BEAST = new ModelLayerLocation(WIdentifier.mod("werewolf_beast"), "main");
    public static final ModelLayerLocation WEREWOLF_SURVIVALIST = new ModelLayerLocation(WIdentifier.mod("werewolf_survivalist"), "main");
    public static final ModelLayerLocation WEREWOLF_4L = new ModelLayerLocation(WIdentifier.mod( "werewolf_four_legged"), "main");
    public static final ModelLayerLocation EARS_CLAWS = new ModelLayerLocation(WIdentifier.mod("ears_claws"), "main");
    public static final ModelLayerLocation EARS_CLAWS_SLIM = new ModelLayerLocation(WIdentifier.mod("ears_claws"), "slim");
    public static final ModelLayerLocation WOLF_HEAD = new ModelLayerLocation(WIdentifier.mod("wolf_head"), "main");
    public static final ModelLayerLocation JACARANDA_BOAT = new ModelLayerLocation(WIdentifier.mod("boat/jacaranda"), "main");
    public static final ModelLayerLocation JACARANDA_CHEST_BOAT = new ModelLayerLocation(WIdentifier.mod("chest_boat/jacaranda"), "main");
    public static final ModelLayerLocation MAGIC_BOAT = new ModelLayerLocation(WIdentifier.mod("boat/magic"), "main");
    public static final ModelLayerLocation MAGIC_CHEST_BOAT = new ModelLayerLocation(WIdentifier.mod("chest_boat/magic"), "main");

    @ApiStatus.Internal
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.JACARANDA_BOAT.get(), context -> new BoatRenderer(context, JACARANDA_BOAT));
        event.registerEntityRenderer(ModEntities.JACARANDA_CHEST_BOAT.get(), context -> new BoatRenderer(context, JACARANDA_CHEST_BOAT));
        event.registerEntityRenderer(ModEntities.MAGIC_BOAT.get(), context -> new BoatRenderer(context, MAGIC_BOAT));
        event.registerEntityRenderer(ModEntities.MAGIC_CHEST_BOAT.get(), context -> new BoatRenderer(context, MAGIC_CHEST_BOAT));
        event.registerEntityRenderer(ModEntities.ALPHA_WEREWOLF.get(), WerewolfAlphaRenderer::new);
        event.registerEntityRenderer(ModEntities.WEREWOLF_BEAST.get(), WerewolfBeastRenderer::new);
        event.registerEntityRenderer(ModEntities.WEREWOLF_SURVIVALIST.get(), WerewolfSurvivalistRenderer::new);
        event.registerEntityRenderer(ModEntities.HUMAN_WEREWOLF.get(), HumanWerewolfRenderer::new);
        event.registerEntityRenderer(ModEntities.WOLF.get(), WolfRenderer::new);
        event.registerEntityRenderer(ModEntities.TASK_MASTER_WEREWOLF.get(), WerewolfTaskMasterRenderer::new);
        event.registerEntityRenderer(ModEntities.WEREWOLF_MINION.get(), WerewolfMinionRenderer::new);
    }

    @ApiStatus.Internal
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EARS_CLAWS, WerewolfEarsModel::createDefaultLayer);
        event.registerLayerDefinition(EARS_CLAWS_SLIM, WerewolfEarsModel::createSlimLayer);
        event.registerLayerDefinition(WEREWOLF_4L, Werewolf4LModel::createBodyLayer);
        event.registerLayerDefinition(WEREWOLF_BEAST, WerewolfBeastModel::createBodyLayer);
        event.registerLayerDefinition(WEREWOLF_SURVIVALIST, WerewolfSurvivalistModel::createBodyLayer);
        event.registerLayerDefinition(WOLF_HEAD, WolfHeadModel::createBodyLayer);
        LayerDefinition boat = BoatModel.createBoatModel();
        LayerDefinition chestBoat = BoatModel.createChestBoatModel();
        event.registerLayerDefinition(JACARANDA_BOAT, () -> boat);
        event.registerLayerDefinition(JACARANDA_CHEST_BOAT, () -> chestBoat);
        event.registerLayerDefinition(MAGIC_BOAT, () -> boat);
        event.registerLayerDefinition(MAGIC_CHEST_BOAT, () -> chestBoat);
    }

}
