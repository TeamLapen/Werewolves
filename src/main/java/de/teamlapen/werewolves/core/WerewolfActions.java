package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.player.werewolf.actions.HowlingAction;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WerewolfActions {

    public static final WerewolfAction werewolf_form = getNull();
    public static final HowlingAction howling = getNull();

    static void registerActions(IForgeRegistry<IAction> registry) {
        registry.register(new WerewolfAction().setRegistryName(REFERENCE.MODID, "werewolf_form"));
        registry.register(new HowlingAction().setRegistryName(REFERENCE.MODID, "howling"));
    }
}
