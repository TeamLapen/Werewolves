package de.teamlapen.werewolves.entity.actions.werewolves;

import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class EntityActions {

    public static void registerDefaultActions(IForgeRegistry<IEntityAction> registry) {
    }
}
