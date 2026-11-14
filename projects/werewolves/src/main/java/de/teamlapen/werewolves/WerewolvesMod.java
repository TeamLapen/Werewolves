package de.teamlapen.werewolves;

import de.teamlapen.werewolves.api.WerewolvesApi;
import de.teamlapen.werewolves.client.WerewolvesModClient;
import de.teamlapen.werewolves.common.CommonServices;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.proxy.Proxy;
import de.teamlapen.werewolves.server.proxy.ServerProxy;
import de.teamlapen.werewolves.util.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.UnknownNullability;

@Mod(REFERENCE.MODID)
public class WerewolvesMod {

    public static final Proxy proxy = FMLEnvironment.getDist() == Dist.CLIENT ? WerewolvesModClient.getProxy() : new ServerProxy();
    @UnknownNullability
    private static CommonServices SERVICES;
    @UnknownNullability
    private static ModConfig CONFIG;

    public WerewolvesMod(IEventBus modEventBus, ModContainer modContainer) {

        CONFIG = new ModConfig(modContainer);
        CONFIG.register(modEventBus);
        SERVICES = (CommonServices) WerewolvesApi.services();
        SERVICES.register(modEventBus);
    }

    public static CommonServices services() {
        return SERVICES;
    }

    public static ModConfig config() {
        return CONFIG;
    }
//
//
//    private void registerCapabilities(@NotNull RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTiles.STONE_ALTAR.get(), (o, side) -> new InvWrapper(o));
//    }
}
