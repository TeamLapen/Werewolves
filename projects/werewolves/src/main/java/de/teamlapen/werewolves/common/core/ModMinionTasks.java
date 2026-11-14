package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.registries.minion_tasks.DeferredMinionTask;
import de.teamlapen.faction.api.registries.minion_tasks.DeferredMinionTaskRegister;
import de.teamlapen.faction.common.core.FactionMinionTaskCategories;
import de.teamlapen.faction.common.core.FactionSkills;
import de.teamlapen.faction.common.factions.minions.management.CollectResourcesTask;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.IEventBus;

public class ModMinionTasks {

    public static final DeferredMinionTaskRegister MINION_TASKS = DeferredMinionTaskRegister.create(REFERENCE.MODID);

    public static final DeferredMinionTask<CollectResourcesTask.State, CollectResourcesTask> COLLECT_BLOOD = MINION_TASKS.registerTask("collect_werewolf", p -> new CollectResourcesTask(p, ModLootTables.MINION_COLLECT_WEREWOLF), p -> p.markGlobal().category(FactionMinionTaskCategories.COLLECT_RESOURCES).factions(ModTags.Factions.IS_WEREWOLF).requires(FactionSkills.MINION_COLLECT));

    static void register(IEventBus bus) {
        MINION_TASKS.register(bus);
    }
}
