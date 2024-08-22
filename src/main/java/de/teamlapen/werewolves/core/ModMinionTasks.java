package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.minion.management.CollectResourcesTask;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;

public class ModMinionTasks {

    public static final DeferredRegister<IMinionTask<?, ?>> TASKS = DeferredRegister.create(VampirismRegistries.Keys.MINION_TASK, REFERENCE.MODID);

    public static final DeferredHolder<IMinionTask<?,?>, CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData>> COLLECT_WEREWOLF_ITEMS = TASKS.register("collect_werewolf_items", () -> new CollectResourcesTask<>(WReference.WEREWOLF_FACTION,
            data -> (int) (VampirismConfig.BALANCE.miResourceCooldown.get() * (1f - data.getResourceEfficiencyLevel() / WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES * 0.4f)),
            Arrays.asList(
                    WeightedEntry.wrap(new ItemStack(ModItems.LIVER.get()), 6),
                    WeightedEntry.wrap(new ItemStack(Items.PORKCHOP), 2),
                    WeightedEntry.wrap(new ItemStack(Items.BEEF), 2),
                    WeightedEntry.wrap(new ItemStack(Items.ROTTEN_FLESH), 1),
                    WeightedEntry.wrap(new ItemStack(Items.MUTTON), 1),
                    WeightedEntry.wrap(new ItemStack(ModItems.CRACKED_BONE.get()), 2),
                    WeightedEntry.wrap(new ItemStack(ModItems.WEREWOLF_TOOTH.get()), 1),
                    WeightedEntry.wrap(new ItemStack(ModItems.V.HUMAN_HEART.get()), 1)), ModSkills.MINION_COLLECT));

    public static class V {
        public final static DeferredHolder<IMinionTask<?,?>, IMinionTask<?, ?>> FOLLOW_LORD = task("follow_lord");
        public final static DeferredHolder<IMinionTask<?,?>, IMinionTask<?, ?>> DEFEND_AREA = task("defend_area");
        public final static DeferredHolder<IMinionTask<?,?>, IMinionTask<?, ?>> STAY = task("stay");
        public final static DeferredHolder<IMinionTask<?,?>, IMinionTask<?, ?>> PROTECT_LORD = task("protect_lord");

        private static void init() {

        }

        private static DeferredHolder<IMinionTask<?,?>, IMinionTask<?, ?>> task(String name) {
            return DeferredHolder.create(ResourceKey.create(VampirismRegistries.Keys.MINION_TASK, WResourceLocation.v(name)));
        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        TASKS.register(bus);
    }
}
