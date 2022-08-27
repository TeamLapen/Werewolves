package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.minion.management.CollectResourcesTask;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ModMinionTasks {

    public static final DeferredRegister<IMinionTask<?, ?>> TASKS = DeferredRegister.create(VampirismRegistries.MINION_TASKS_ID, REFERENCE.MODID);

    public static final RegistryObject<CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData>> COLLECT_WEREWOLF_ITEMS = TASKS.register("collect_werewolf_items", () -> new CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData>(WReference.WEREWOLF_FACTION,
            data -> (int) (VampirismConfig.BALANCE.miResourceCooldown.get() * (1f - data.getResourceEfficiencyLevel() / WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES * 0.4f)),
            Arrays.asList(
                    WeightedEntry.wrap(new ItemStack(ModItems.LIVER.get()), 6),
                    WeightedEntry.wrap(new ItemStack(Items.PORKCHOP), 2),
                    WeightedEntry.wrap(new ItemStack(Items.BEEF), 2),
                    WeightedEntry.wrap(new ItemStack(Items.ROTTEN_FLESH), 1),
                    WeightedEntry.wrap(new ItemStack(Items.MUTTON), 1),
                    WeightedEntry.wrap(new ItemStack(ModItems.CRACKED_BONE.get()), 2),
                    WeightedEntry.wrap(new ItemStack(ModItems.WEREWOLF_TOOTH.get()), 1),
                    WeightedEntry.wrap(new ItemStack(ModItems.V.HUMAN_HEART.get()), 1)), ModSkills.MINION_COLLECT::get));

    public static class V {
        public final static RegistryObject<IMinionTask<?, ?>> FOLLOW_LORD = task("follow_lord");
        public final static RegistryObject<IMinionTask<?, ?>> DEFEND_AREA = task("defend_area");
        public final static RegistryObject<IMinionTask<?, ?>> STAY = task("stay");
        public final static RegistryObject<IMinionTask<?, ?>> PROTECT_LORD = task("protect_lord");

        private static void init() {

        }

        private static RegistryObject<IMinionTask<?, ?>> task(String name) {
            return RegistryObject.create(new ResourceLocation("vampirism", name), VampirismRegistries.MINION_TASKS_ID, REFERENCE.MODID);
        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        TASKS.register(bus);
    }
}
