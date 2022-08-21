package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.werewolves.entities.werewolf.action.BiteEntityAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityActions {

    public static final DeferredRegister<IEntityAction> ACTIONS = DeferredRegister.create(VampirismRegistries.ENTITY_ACTIONS_ID, REFERENCE.MODID);

    public static final RegistryObject<BiteEntityAction<?>> BITE = ACTIONS.register("bite", () -> new BiteEntityAction<>(EntityActionTier.Default, EntityClassType.values()));

    static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
