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

    public static final RegistryObject<CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData>> collect_werewolf_items = TASKS.register("collect_werewolf_items", () -> new CollectResourcesTask<>(WReference.WEREWOLF_FACTION,
            data -> (int) (VampirismConfig.BALANCE.miResourceCooldown.get() * (1f - data.getResourceEfficiencyLevel() / WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES * 0.4f)),
            Arrays.asList(
                    new WeightedRandomItem<>(new ItemStack(ModItems.liver.get()), 6),
                    new WeightedRandomItem<>(new ItemStack(Items.PORKCHOP), 2),
                    new WeightedRandomItem<>(new ItemStack(Items.BEEF), 2),
                    new WeightedRandomItem<>(new ItemStack(Items.ROTTEN_FLESH), 1),
                    new WeightedRandomItem<>(new ItemStack(Items.MUTTON), 1),
                    new WeightedRandomItem<>(new ItemStack(ModItems.cracked_bone.get()), 2),
                    new WeightedRandomItem<>(new ItemStack(ModItems.werewolf_tooth.get()), 1),
                    new WeightedRandomItem<>(new ItemStack(ModItems.V.human_heart.get()), 1)), WerewolfSkills.WEREWOLF_MINION_COLLECT));

    public static class V {
        public final static RegistryObject<SimpleMinionTask> follow_lord = v("follow_lord");
        public final static RegistryObject<DefendAreaTask> defend_area = v("defend_area");
        public final static RegistryObject<StayTask> stay = v("stay");
        public final static RegistryObject<SimpleMinionTask> protect_lord = v("protect_lord");

        private static <T extends IMinionTask<?,?>> RegistryObject<T> v(String id) {
            return RegistryObject.of(new ResourceLocation("vampirism", id), ModRegistries.MINION_TASKS);
        }
    }

    static void registerMinionTasks(IEventBus bus) {
        TASKS.register(bus);
    }
}
