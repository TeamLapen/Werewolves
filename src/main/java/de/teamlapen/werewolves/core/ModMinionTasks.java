package de.teamlapen.werewolves.core;

import de.teamlapen.lib.util.WeightedRandomItem;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.entity.minion.management.CollectResourcesTask;
import de.teamlapen.vampirism.entity.minion.management.DefendAreaTask;
import de.teamlapen.vampirism.entity.minion.management.SimpleMinionTask;
import de.teamlapen.vampirism.entity.minion.management.StayTask;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Arrays;

public class ModMinionTasks {

    public static final DeferredRegister<IMinionTask<?,?>> TASKS = DeferredRegister.create(ModRegistries.MINION_TASKS, REFERENCE.MODID);

    public static final RegistryObject<CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData>> COLLECT_WEREWOLF_ITEMS = TASKS.register("collect_werewolf_items", () -> new CollectResourcesTask<>(WReference.WEREWOLF_FACTION,
            data -> (int) (VampirismConfig.BALANCE.miResourceCooldown.get() * (1f - data.getResourceEfficiencyLevel() / WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES * 0.4f)),
            Arrays.asList(
                    new WeightedRandomItem<>(new ItemStack(ModItems.LIVER.get()), 6),
                    new WeightedRandomItem<>(new ItemStack(Items.PORKCHOP), 2),
                    new WeightedRandomItem<>(new ItemStack(Items.BEEF), 2),
                    new WeightedRandomItem<>(new ItemStack(Items.ROTTEN_FLESH), 1),
                    new WeightedRandomItem<>(new ItemStack(Items.MUTTON), 1),
                    new WeightedRandomItem<>(new ItemStack(ModItems.CRACKED_BONE.get()), 2),
                    new WeightedRandomItem<>(new ItemStack(ModItems.WEREWOLF_TOOTH.get()), 1),
                    new WeightedRandomItem<>(new ItemStack(ModItems.V.HUMAN_HEART.get()), 1)), WerewolfSkills.WEREWOLF_MINION_COLLECT));

    public static class V {
        public final static RegistryObject<SimpleMinionTask> FOLLOW_LORD = v("follow_lord");
        public final static RegistryObject<DefendAreaTask> DEFEND_AREA = v("defend_area");
        public final static RegistryObject<StayTask> STAY = v("stay");
        public final static RegistryObject<SimpleMinionTask> PROTECT_LORD = v("protect_lord");

        private static <T extends IMinionTask<?,?>> RegistryObject<T> v(String id) {
            return RegistryObject.of(new ResourceLocation("vampirism", id), ModRegistries.MINION_TASKS);
        }

        private static void init() {}
    }

    static void registerMinionTasks(IEventBus bus) {
        TASKS.register(bus);
    }

    static {
        V.init();
    }
}
