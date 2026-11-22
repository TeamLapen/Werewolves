package de.teamlapen.werewolves.integration.vampirism.common;

import de.teamlapen.faction.Services;
import de.teamlapen.werewolves.integration.vampirism.common.core.*;
import de.teamlapen.werewolves.integration.vampirism.data.CompatDataProvider;
import de.teamlapen.werewolves.common.world.attachments.WerewolfVillagerAttachment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

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
        bus.addListener(CompatPacks::addPackFinders);
        bus.addListener(FMLCommonSetupEvent.class, event -> WerewolfVillagerAttachment.addPreventWerewolfCheck(CompatEventHandler::isVampireVillager));
    }

    @Override
    protected void registerGameBus(IEventBus bus) {
        bus.addListener(CompatEventHandler::onEntityInteract);
    }
}
