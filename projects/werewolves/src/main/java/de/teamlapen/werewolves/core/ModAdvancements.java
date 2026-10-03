package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.advancements.criterion.WerewolfActionCriterionTrigger;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAdvancements {
    private static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<CriterionTrigger<?>, WerewolfActionCriterionTrigger> TRIGGER_VAMPIRE_ACTION = TRIGGERS.register("werewolf_action", WerewolfActionCriterionTrigger::new);

    static void register(IEventBus bus) {
        TRIGGERS.register(bus);
    }

}
