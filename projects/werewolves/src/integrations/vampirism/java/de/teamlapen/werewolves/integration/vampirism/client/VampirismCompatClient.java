package de.teamlapen.werewolves.integration.vampirism.client;

import de.teamlapen.integration.Integration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;

@Integration(modIds = "vampirism", dist = Dist.CLIENT)
public class VampirismCompatClient {

    private static final CompatClientServices SERVICES = new CompatClientServices();

    public VampirismCompatClient(IEventBus bus) {
        SERVICES.register(bus);
    }
}
