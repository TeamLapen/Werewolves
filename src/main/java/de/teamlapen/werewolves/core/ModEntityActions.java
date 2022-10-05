package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class ModEntityActions {

    public static final DeferredRegister<IEntityAction> ACTIONS = DeferredRegister.create(ModRegistries.ENTITYACTIONS, REFERENCE.MODID);

    static void registerEntityActions(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
