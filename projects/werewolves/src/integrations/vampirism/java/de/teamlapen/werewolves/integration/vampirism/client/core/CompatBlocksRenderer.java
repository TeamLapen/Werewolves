package de.teamlapen.werewolves.integration.vampirism.client.core;

import de.teamlapen.werewolves.integration.vampirism.client.renderer.blockentities.WolfsbaneDiffuserBESR;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlockEntities;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.ApiStatus;

public class CompatBlocksRenderer {

    @ApiStatus.Internal
    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CompatBlockEntities.WOLFSBANE_DIFFUSER.get(), WolfsbaneDiffuserBESR::new);
    }
}
