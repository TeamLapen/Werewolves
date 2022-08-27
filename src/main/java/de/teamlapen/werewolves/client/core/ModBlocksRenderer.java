package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.tiles.StoneAltarTESR;
import de.teamlapen.werewolves.core.ModTiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ModBlocksRenderer {

    public static void registerBlockEntityRenderers(EntityRenderersEvent.@NotNull RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTiles.STONE_ALTAR.get(), StoneAltarTESR::new);
    }
}
