package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.werewolves.entities.player.werewolf.actions.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModActions {

    public static final DeferredRegister<IAction<?>> ACTIONS = DeferredRegister.create(VampirismRegistries.ACTIONS_ID, REFERENCE.MODID);

    public static final RegistryObject<WerewolfFormAction> HUMAN_FORM = ACTIONS.register("human_form", HumanWerewolfFormAction::new);
    public static final RegistryObject<WerewolfFormAction> BEAST_FORM = ACTIONS.register("beast_form", BeastWerewolfFormAction::new);
    public static final RegistryObject<WerewolfFormAction> SURVIVAL_FORM = ACTIONS.register("survival_form", SurvivalWerewolfFormAction::new);
    public static final RegistryObject<HowlingAction> HOWLING = ACTIONS.register("howling", HowlingAction::new);
    public static final RegistryObject<RageWerewolfAction> RAGE = ACTIONS.register("rage", RageWerewolfAction::new);
    public static final RegistryObject<SenseWerewolfAction> SENSE = ACTIONS.register("sense", SenseWerewolfAction::new);
    public static final RegistryObject<FearAction> FEAR = ACTIONS.register("fear", FearAction::new);
    public static final RegistryObject<LeapAction> LEAP = ACTIONS.register("leap", LeapAction::new);
    public static final RegistryObject<HideNameAction> HIDE_NAME = ACTIONS.register("hide_name", HideNameAction::new);

    static void register(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
