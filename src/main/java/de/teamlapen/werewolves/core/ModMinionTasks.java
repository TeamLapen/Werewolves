package de.teamlapen.werewolves.core;

import de.teamlapen.lib.util.WeightedRandomItem;
import de.teamlapen.vampirism.api.entity.minion.IMinionTask;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.minion.management.CollectResourcesTask;
import de.teamlapen.vampirism.entity.minion.management.DefendAreaTask;
import de.teamlapen.vampirism.entity.minion.management.SimpleMinionTask;
import de.teamlapen.vampirism.entity.minion.management.StayTask;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Arrays;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModMinionTasks {

    public static final CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData> collect_werewolf_items = getNull();

    @ObjectHolder(de.teamlapen.vampirism.REFERENCE.MODID)
    public static class V {
        public final static SimpleMinionTask follow_lord = getNull();
        public final static DefendAreaTask defend_area = getNull();
        public final static StayTask stay = getNull();
        public final static SimpleMinionTask protect_lord = getNull();
    }

    public static void register(IForgeRegistry<IMinionTask<?,?>> registry) {
        registry.register(new CollectResourcesTask<WerewolfMinionEntity.WerewolfMinionData>(WReference.WEREWOLF_FACTION,
                data -> (int) (VampirismConfig.BALANCE.miResourceCooldown.get() * (1f - data.getResourceEfficiencyLevel() / WerewolfMinionEntity.WerewolfMinionData.MAX_LEVEL_RESOURCES * 0.4f)),
                Arrays.asList(
                        new WeightedRandomItem<>(new ItemStack(ModItems.liver), 6),
                        new WeightedRandomItem<>(new ItemStack(Items.PORKCHOP), 2),
                        new WeightedRandomItem<>(new ItemStack(Items.BEEF), 2),
                        new WeightedRandomItem<>(new ItemStack(Items.ROTTEN_FLESH), 1),
                        new WeightedRandomItem<>(new ItemStack(Items.MUTTON), 1),
                        new WeightedRandomItem<>(new ItemStack(ModItems.bone), 2),
                        new WeightedRandomItem<>(new ItemStack(ModItems.werewolf_tooth),1),
                        new WeightedRandomItem<>(new ItemStack(ModItems.V.human_heart), 1)))
                .setRegistryName(REFERENCE.MODID, "collect_werewolf_items"));
    }
}
