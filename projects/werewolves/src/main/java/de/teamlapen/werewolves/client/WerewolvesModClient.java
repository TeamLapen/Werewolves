package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.common.core.ModRegistryManager;
import de.teamlapen.werewolves.client.proxy.ClientProxy;
import de.teamlapen.werewolves.common.proxy.Proxy;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = REFERENCE.MODID, dist = Dist.CLIENT)
public class WerewolvesModClient {

    private static ClientServices SERVICES;

    public WerewolvesModClient(IEventBus modbus, ModContainer modContainer) {
        SERVICES =  new ClientServices();
        SERVICES.register(modbus);
    }

    public static ClientServices services() {
        return SERVICES;
    }

    public static Proxy createProxy() {
        return new ClientProxy();
    }
}
