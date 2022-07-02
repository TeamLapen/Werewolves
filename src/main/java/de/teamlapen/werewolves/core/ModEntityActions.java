package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.werewolves.entities.werewolf.action.BiteEntityAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityActions {

    public static final DeferredRegister<IEntityAction> ACTIONS = DeferredRegister.create(ModRegistries.ENTITYACTIONS, REFERENCE.MODID);

    public static final RegistryObject<BiteEntityAction<?>> bite = ACTIONS.register("bite", () -> new BiteEntityAction<>(EntityActionTier.Default, EntityClassType.values()));

    public static void registerEntityActions(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
