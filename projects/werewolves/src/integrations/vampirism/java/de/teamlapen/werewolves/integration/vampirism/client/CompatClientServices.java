package de.teamlapen.werewolves.integration.vampirism.client;

import de.teamlapen.faction.Services;
import de.teamlapen.werewolves.integration.vampirism.client.core.CompatBlocksRenderer;
import net.neoforged.bus.api.IEventBus;

public class CompatClientServices extends Services {

    @Override
    protected void registerModBus(IEventBus bus) {
        bus.addListener(CompatBlocksRenderer::registerBlockEntityRenderers);
    }
}
