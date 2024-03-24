package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.entities.player.werewolf.actions.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModActions {

    public static final DeferredRegister<IAction<?>> ACTIONS = DeferredRegister.create(VampirismRegistries.Keys.ACTION, REFERENCE.MODID);

    public static final DeferredHolder<IAction<?>, WerewolfFormAction> HUMAN_FORM = ACTIONS.register("human_form", HumanWerewolfFormAction::new);
    public static final DeferredHolder<IAction<?>, WerewolfFormAction> BEAST_FORM = ACTIONS.register("beast_form", BeastWerewolfFormAction::new);
    public static final DeferredHolder<IAction<?>, WerewolfFormAction> SURVIVAL_FORM = ACTIONS.register("survival_form", SurvivalWerewolfFormAction::new);
    public static final DeferredHolder<IAction<?>, HowlingAction> HOWLING = ACTIONS.register("howling", HowlingAction::new);
    public static final DeferredHolder<IAction<?>, RageWerewolfAction> RAGE = ACTIONS.register("rage", RageWerewolfAction::new);
    public static final DeferredHolder<IAction<?>, SenseWerewolfAction> SENSE = ACTIONS.register("sense", SenseWerewolfAction::new);
    public static final DeferredHolder<IAction<?>, FearAction> FEAR = ACTIONS.register("fear", FearAction::new);
    public static final DeferredHolder<IAction<?>, LeapAction> LEAP = ACTIONS.register("leap", LeapAction::new);
    public static final DeferredHolder<IAction<?>, HideNameAction> HIDE_NAME = ACTIONS.register("hide_name", HideNameAction::new);

    static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
