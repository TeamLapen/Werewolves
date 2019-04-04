package de.teamlapen.vampirewerewolf.player.werewolf.actions;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Registers and holds all skills for werewolve players
 */
@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class WerewolfActions {
    public static final WerewolfAction werewolf_werewolf = UtilLib.getNull();

    public static void registerDefaultActions(IForgeRegistry<IAction> registry) {
        registry.register(new WerewolfAction().setRegistryName("vampirewerewolf", "werewolf_werewolf"));
    }
}
