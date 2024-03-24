package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntityActions {

    public static final DeferredRegister<IEntityAction> ACTIONS = DeferredRegister.create(VampirismRegistries.Keys.ENTITY_ACTION, REFERENCE.MODID);

    static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
