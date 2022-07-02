package de.teamlapen.werewolves.core;

import de.teamlapen.lib.util.WeightedRandomItem;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.core.ModRegistries;
import de.teamlapen.vampirism.entity.minion.management.CollectResourcesTask;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ModMinionTasks {

    public static final DeferredRegister<IMinionTask<?, ?>> TASKS = DeferredRegister.create(ModRegistries.MINION_TASKS, REFERENCE.MODID);

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
                    new WeightedRandomItem<>(new ItemStack(ModItems.V.human_heart.get()), 1))));

    public static class V {
        public final static RegistryObject<IMinionTask<?, ?>> follow_lord = task("follow_lord");
        public final static RegistryObject<IMinionTask<?, ?>> defend_area = task("defend_area");
        public final static RegistryObject<IMinionTask<?, ?>> stay = task("stay");
        public final static RegistryObject<IMinionTask<?, ?>> protect_lord = task("protect_lord");

        private static RegistryObject<IMinionTask<?, ?>> task(String name) {
            return RegistryObject.create(new ResourceLocation("vampirism", name), ModRegistries.MINION_TASKS);
        }
    }

    public static void register(IEventBus bus) {
        TASKS.register(bus);
    }
}
