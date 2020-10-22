package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.player.werewolf.actions.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WerewolfActions {

    public static final WerewolfFormAction werewolf_form = getNull();
    public static final HowlingAction howling = getNull();
    public static final BiteAction bite = getNull();
    public static final RageWerewolfAction rage = getNull();
    public static final SenseWerewolfAction sense = getNull();
    public static final FearAction fear = getNull();
    public static final LeapAction leap = getNull();
    public static final HideNameAction hide_name = getNull();
    public static final SummonWolfAction wolf_pack = getNull();
    public static final SixthSenseAction sixth_sense = getNull();

    static void registerActions(IForgeRegistry<IAction> registry) {
        registry.register(new WerewolfFormAction().setRegistryName(REFERENCE.MODID, "werewolf_form"));
        registry.register(new HowlingAction().setRegistryName(REFERENCE.MODID, "howling"));
        registry.register(new BiteAction().setRegistryName(REFERENCE.MODID, "bite"));
        registry.register(new RageWerewolfAction().setRegistryName(REFERENCE.MODID, "rage"));
        registry.register(new SenseWerewolfAction().setRegistryName(REFERENCE.MODID, "sense"));
        registry.register(new FearAction().setRegistryName(REFERENCE.MODID, "fear"));
        registry.register(new LeapAction().setRegistryName(REFERENCE.MODID, "leap"));
        registry.register(new HideNameAction().setRegistryName(REFERENCE.MODID, "hide_name"));
        registry.register(new SummonWolfAction().setRegistryName(REFERENCE.MODID, "wolf_pack"));
        registry.register(new SixthSenseAction().setRegistryName(REFERENCE.MODID, "sixth_sense"));
    }
}
