package de.teamlapen.werewolves.player.werewolf.actions;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Registers and holds all skills for werewolf players
 */
@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class WerewolfActions {
    public static final WerewolfAction werewolf_werewolf = getNull();
    public static final AnimalHunterAction animal_hunter = getNull();

    public static void registerDefaultActions(IForgeRegistry<IAction> registry) {
        registry.register(new WerewolfAction().setRegistryName(REFERENCE.MODID, "werewolf_werewolf"));
        registry.register(new AnimalHunterAction().setRegistryName(REFERENCE.MODID, "animal_hunter"));
    }
}
