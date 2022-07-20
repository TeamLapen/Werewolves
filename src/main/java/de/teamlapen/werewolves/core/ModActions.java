package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.actions.IAction;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.player.actions.AttackSpeedLordAction;
import de.teamlapen.vampirism.player.actions.SpeedLordAction;
import de.teamlapen.werewolves.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.*;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModActions {

    public static final DeferredRegister<IAction> ACTIONS = DeferredRegister.create(ModRegistries.ACTIONS, REFERENCE.MODID);

    public static final RegistryObject<WerewolfFormAction> human_form = ACTIONS.register("human_form", HumanWerewolfFormAction::new);
    public static final RegistryObject<WerewolfFormAction> beast_form = ACTIONS.register("beast_form", BeastWerewolfFormAction::new);
    public static final RegistryObject<WerewolfFormAction> survival_form = ACTIONS.register("survival_form", SurvivalWerewolfFormAction::new);
    public static final RegistryObject<HowlingAction> howling = ACTIONS.register("howling", HowlingAction::new);
    public static final RegistryObject<RageWerewolfAction> rage = ACTIONS.register("rage", RageWerewolfAction::new);
    public static final RegistryObject<SenseWerewolfAction> sense = ACTIONS.register("sense", SenseWerewolfAction::new);
    public static final RegistryObject<FearAction> fear = ACTIONS.register("fear", FearAction::new);
    public static final RegistryObject<LeapAction> leap = ACTIONS.register("leap", LeapAction::new);
    public static final RegistryObject<HideNameAction> hide_name = ACTIONS.register("hide_name", HideNameAction::new);
    public static final RegistryObject<SpeedLordAction<IWerewolfPlayer>> WEREWOLF_LORD_SPEED = ACTIONS.register("werewolf_lord_speed", () -> new SpeedLordAction<>(WReference.WEREWOLF_FACTION));
    public static final RegistryObject<AttackSpeedLordAction<IWerewolfPlayer>> WEREWOLF_LORD_ATTACK_SPEED = ACTIONS.register("werewolf_lord_attack_speed", () -> new AttackSpeedLordAction<>(WReference.WEREWOLF_FACTION));

    static void registerActions(IEventBus bus) {
        ACTIONS.register(bus);
    }
}
