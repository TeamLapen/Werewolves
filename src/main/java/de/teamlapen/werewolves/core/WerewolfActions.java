package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.player.werewolf.actions.BiteAction;
import de.teamlapen.werewolves.player.werewolf.actions.HowlingAction;
import de.teamlapen.werewolves.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WerewolfActions {

    public static final WerewolfFormAction werewolf_form = getNull();
    public static final HowlingAction howling = getNull();
    public static final BiteAction bite = getNull();

    static void registerActions(IForgeRegistry<IAction> registry) {
        registry.register(new WerewolfFormAction().setRegistryName(REFERENCE.MODID, "werewolf_form"));
        registry.register(new HowlingAction().setRegistryName(REFERENCE.MODID, "howling"));
        registry.register(new BiteAction().setRegistryName(REFERENCE.MODID, "bite"));
    }
}
