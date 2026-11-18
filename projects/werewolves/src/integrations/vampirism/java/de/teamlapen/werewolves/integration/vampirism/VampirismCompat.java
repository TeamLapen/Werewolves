package de.teamlapen.werewolves.integration.vampirism;

import de.teamlapen.integration.Integration;
import de.teamlapen.werewolves.integration.vampirism.common.CompatServices;
import net.neoforged.bus.api.IEventBus;

@Integration(modIds = "vampirism")
public class VampirismCompat {

    private static final CompatServices SERVICES = new CompatServices();

    public VampirismCompat(IEventBus bus) {
        SERVICES.register(bus);
    }
}
