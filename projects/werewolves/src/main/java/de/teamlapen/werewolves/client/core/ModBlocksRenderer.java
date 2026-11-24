package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.renderer.blocks.StoneAltarRenderer;
import de.teamlapen.werewolves.common.core.ModBlockEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.ApiStatus;

public class ModBlocksRenderer {

    @ApiStatus.Internal
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.STONE_ALTAR.get(), StoneAltarRenderer::new);
    }
}
