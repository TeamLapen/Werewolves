package de.teamlapen.werewolves.common;

import de.teamlapen.faction.api.event.AddFactionTagEvent;
import de.teamlapen.werewolves.common.misc.WerewolvesCreativeTab;
import de.teamlapen.werewolves.common.world.features.OverworldModifications;
import de.teamlapen.werewolves.common.core.ModFactions;

import de.teamlapen.faction.Services;
import de.teamlapen.werewolves.api.IWerewolvesServices;
import de.teamlapen.werewolves.common.core.ModBlockEntities;
import de.teamlapen.werewolves.common.core.ModEntities;
import de.teamlapen.werewolves.common.core.ModRegistryManager;
import de.teamlapen.werewolves.common.network.ModPacketDispatcher;
import de.teamlapen.werewolves.common.world.ModWorldEventHandler;
import de.teamlapen.werewolves.common.world.entities.ModEntityEventHandler;
import de.teamlapen.werewolves.common.world.entities.player.ModPlayerEventHandler;
import de.teamlapen.werewolves.util.Permissions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.jetbrains.annotations.ApiStatus;

public class CommonServices extends Services implements IWerewolvesServices {

    private final ModRegistryManager registryManager = new ModRegistryManager();
    private final ModEntityEventHandler entityEventHandler = new ModEntityEventHandler();
    private final ModPlayerEventHandler playerEventHandler = new ModPlayerEventHandler();
    private final GeneralEventHandler generalEventHandler = new GeneralEventHandler();

    /**
     * Instantiated by the {@link java.util.ServiceLoader} through {@code META-INF/services}.
     */
    @ApiStatus.Internal
    public CommonServices() {
    }

    @Override
    protected void registerGameBus(IEventBus bus) {
        bus.addListener(Permissions::registerNodes);
        this.registryManager.registerGameBus(bus);
        bus.register(new ModWorldEventHandler());
        bus.register(this.entityEventHandler);
        bus.register(this.playerEventHandler);
        bus.register(this.generalEventHandler);
    }

    @Override
    protected void registerModBus(IEventBus bus) {
        super.registerModBus(bus);
        this.registryManager.registerModBus(bus);
        bus.addListener(FMLLoadCompleteEvent.class, e -> e.enqueueWork(OverworldModifications::addBiomesToOverworldUnsafe));
        bus.addListener(ModPacketDispatcher::registerHandler);
        bus.addListener(AddFactionTagEvent.class, ModFactions::registerFactionTags);
        bus.addListener(ModEntities::registerSpawns);
        bus.addListener(ModEntities::onRegisterEntityTypeAttributes);
        bus.addListener(ModEntities::onModifyEntityTypeAttributes);
        bus.addListener(ModEntities::registerPlayerEventHandler);
        bus.addListener(ModBlockEntities::registerCapabilities);
        bus.addListener(WerewolvesCreativeTab::addToExistingCreativeTab);
    }
}
