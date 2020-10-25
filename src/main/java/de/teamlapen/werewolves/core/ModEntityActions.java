package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.vampirism.api.entity.actions.IEntityAction;
import de.teamlapen.werewolves.entities.action.werewolf.BiteEntityAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEntityActions {
    public static final BiteEntityAction<?> bite = getNull();

    public static void registerEntityActions(IForgeRegistry<IEntityAction> registry) {
        registry.register(new BiteEntityAction<>(EntityActionTier.Default, EntityClassType.values()).setRegistryName(REFERENCE.MODID, "bite"));
    }
}
