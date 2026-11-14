package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.actions.IAction;
import de.teamlapen.faction.api.factions.actions.ILastingAction;
import de.teamlapen.faction.api.registries.actions.DeferredAction;
import de.teamlapen.faction.api.registries.actions.DeferredActionRegister;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModActions {

    public static final DeferredActionRegister<IWerewolfPlayer> ACTIONS = DeferredActionRegister.create(REFERENCE.MODID);

    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>,WerewolfFormAction> HUMAN_FORM = ACTIONS.registerAction("human_form", HumanWerewolfFormAction::new);
    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, WerewolfFormAction> BEAST_FORM = ACTIONS.registerAction("beast_form", BeastWerewolfFormAction::new);
    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, WerewolfFormAction> SURVIVAL_FORM = ACTIONS.registerAction("survival_form", SurvivalWerewolfFormAction::new);
    public static final DeferredAction<IWerewolfPlayer, IAction<IWerewolfPlayer>, HowlingAction> HOWLING = ACTIONS.registerAction("howling", HowlingAction::new);
    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, RageWerewolfAction> RAGE = ACTIONS.registerAction("rage", RageWerewolfAction::new);
    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, SenseWerewolfAction> SENSE = ACTIONS.registerAction("sense", SenseWerewolfAction::new);
    public static final DeferredAction<IWerewolfPlayer, IAction<IWerewolfPlayer>, FearAction> FEAR = ACTIONS.registerAction("fear", FearAction::new);
    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, LeapAction> LEAP = ACTIONS.registerAction("leap", LeapAction::new);
    public static final DeferredAction<IWerewolfPlayer, ILastingAction<IWerewolfPlayer>, HideNameAction> HIDE_NAME = ACTIONS.registerAction("hide_name", HideNameAction::new);

    static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
