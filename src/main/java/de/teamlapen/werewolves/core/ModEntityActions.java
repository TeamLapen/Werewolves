package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModEntityActions {

    public static final DeferredRegister<IEntityAction> ACTIONS = DeferredRegister.create(VampirismRegistries.ENTITY_ACTIONS_ID, REFERENCE.MODID);

    static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
