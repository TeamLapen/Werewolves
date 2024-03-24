package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.tiles.StoneAltarTESR;
import de.teamlapen.werewolves.client.render.tiles.WolfsbaneDiffuserBESR;
import de.teamlapen.werewolves.core.ModTiles;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.ApiStatus;

public class ModBlocksRenderer {

    @ApiStatus.Internal
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTiles.STONE_ALTAR.get(), StoneAltarTESR::new);
        event.registerBlockEntityRenderer(ModTiles.WOLFSBANE_DIFFUSER.get(), WolfsbaneDiffuserBESR::new);
    }
}
