package de.teamlapen.werewolves.integration.vampirism.common;

import de.teamlapen.faction.Services;
import de.teamlapen.werewolves.integration.vampirism.common.core.*;
import de.teamlapen.werewolves.integration.vampirism.data.CompatDataProvider;
import net.neoforged.bus.api.IEventBus;

public class CompatServices extends Services {

    @Override
    protected void registerModBus(IEventBus bus) {
        CompatOils.register(bus);
        CompatItems.register(bus);
        CompatMenus.register(bus);
        CompatBlocks.register(bus);
        CompatBlockEntities.register(bus);
        bus.addListener(CompatEventHandler::appendCreativeTab);
        bus.addListener(CompatDataProvider::onGatherData);
        bus.addListener(CompatDataMaps::registerDataMaps);
    }
}
