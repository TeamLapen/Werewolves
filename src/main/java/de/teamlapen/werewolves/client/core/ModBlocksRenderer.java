package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.WitchSkullModel;
import de.teamlapen.werewolves.client.render.tiles.StoneAltarTESR;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
public class ModBlocksRenderer {

    static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTiles.STONE_ALTAR.get(), StoneAltarTESR::new);
    }

    static void registerSkulls(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(WUtils.SkullType.WITCH, new WitchSkullModel(event.getEntityModelSet().bakeLayer(ModModelRender.WITCH_SKULL)));
        SkullBlockRenderer.SKIN_BY_TYPE.put(WUtils.SkullType.WITCH, new ResourceLocation("textures/entity/witch.png"));
    }
}
