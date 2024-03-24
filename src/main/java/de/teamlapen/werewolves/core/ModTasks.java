package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.vampirism.core.ModStats;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.entity.player.tasks.TaskBuilder;
import de.teamlapen.vampirism.entity.player.tasks.reward.LordLevelReward;
import de.teamlapen.vampirism.entity.player.tasks.reward.RefinementItemReward;
import de.teamlapen.vampirism.entity.player.tasks.unlock.LordLvlUnlocker;
import de.teamlapen.vampirism.entity.player.tasks.unlock.LvlUnlocker;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.util.REFERENCE;
import io.netty.bootstrap.BootstrapConfig;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;

@SuppressWarnings("unused")
public class ModTasks {

    public static final ResourceKey<Task> WEREWOLF_MINION_BINDING = key("werewolf_minion_binding");
    public static final ResourceKey<Task> WEREWOLF_MINION_UPGRADE_SIMPLE = key("werewolf_minion_upgrade_simple");
    public static final ResourceKey<Task> WEREWOLF_MINION_UPGRADE_ENHANCED = key("werewolf_minion_upgrade_enhanced");
    public static final ResourceKey<Task> WEREWOLF_MINION_UPGRADE_SPECIAL = key("werewolf_minion_upgrade_special");
    public static final ResourceKey<Task> WEREWOLF_LORD_1 = key("werewolf_lord1");
    public static final ResourceKey<Task> WEREWOLF_LORD_2 = key("werewolf_lord2");
    public static final ResourceKey<Task> WEREWOLF_LORD_3 = key("werewolf_lord3");
    public static final ResourceKey<Task> WEREWOLF_LORD_4 = key("werewolf_lord4");
    public static final ResourceKey<Task> WEREWOLF_LORD_5 = key("werewolf_lord5");
    public static final ResourceKey<Task> OBLIVION_POTION = key("oblivion_potion");
    public static final ResourceKey<Task> RANDOM_REFINEMENT_1 = key("random_refinement1");
    public static final ResourceKey<Task> RANDOM_REFINEMENT_2 = key("random_refinement2");
    public static final ResourceKey<Task> RANDOM_REFINEMENT_3 = key("random_refinement3");
    public static final ResourceKey<Task> RANDOM_RARE_REFINEMENT = key("random_rare_refinement");

    private static ResourceKey<Task> key(String path) {
        return ResourceKey.create(VampirismRegistries.Keys.TASK, new ResourceLocation(REFERENCE.MODID, path));
    }

    public static void createTasks(BootstapContext<Task> context) {
        context.register(WEREWOLF_MINION_BINDING, TaskBuilder.builder(WEREWOLF_MINION_BINDING).defaultTitle().setReward(new ItemStack(ModItems.WEREWOLF_MINION_CHARM.get()))
                .unlockedBy(new LordLvlUnlocker(1))
                .addRequirement(ModTags.Entities.ADVANCED_HUNTER, 4)
                .addRequirement(ModTags.Entities.ADVANCED_VAMPIRE, 6)
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 32))
                .build());
        context.register(WEREWOLF_MINION_UPGRADE_SIMPLE, TaskBuilder.builder(WEREWOLF_MINION_UPGRADE_SIMPLE).defaultTitle().setReward(new ItemStack(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()))
                .unlockedBy(new LordLvlUnlocker(2))
                .addRequirement(ModTags.Entities.ADVANCED_HUNTER, 6)
                .addRequirement(ModTags.Entities.ADVANCED_VAMPIRE, 8)
                .addRequirement(new ItemStack(Items.GOLD_BLOCK, 16))
                .build());
        context.register(WEREWOLF_MINION_UPGRADE_ENHANCED, TaskBuilder.builder(WEREWOLF_MINION_UPGRADE_ENHANCED).defaultTitle().setReward(new ItemStack(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()))
                .unlockedBy(new LordLvlUnlocker(3))
                .addRequirement(ModTags.Entities.ADVANCED_HUNTER, 8)
                .addRequirement(ModTags.Entities.ADVANCED_VAMPIRE, 10)
                .addRequirement(new ItemStack(ModItems.LIVER.get(), 16))
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get()))
                .addRequirement(new ItemStack(Items.DIAMOND_BLOCK, 3))
                .build());
        context.register(WEREWOLF_MINION_UPGRADE_SPECIAL, TaskBuilder.builder(WEREWOLF_MINION_UPGRADE_SPECIAL).defaultTitle().setReward(new ItemStack(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()))
                .unlockedBy(new LordLvlUnlocker(5))
                .addRequirement(ModTags.Entities.ADVANCED_HUNTER, 10)
                .addRequirement(ModTags.Entities.ADVANCED_VAMPIRE, 12)
                .addRequirement(new ItemStack(ModItems.LIVER.get(), 32))
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 5))
                .addRequirement(new ItemStack(Items.DIAMOND_BLOCK, 8))
                .build());
        context.register(WEREWOLF_LORD_1, TaskBuilder.builder(WEREWOLF_LORD_1).defaultTitle().setReward(new LordLevelReward(1))
                .unlockedBy(new LvlUnlocker(REFERENCE.HIGHEST_WEREWOLF_LEVEL))
                .addRequirement(ModTags.Entities.VAMPIRE, 10)
                .addRequirement(ModTags.Entities.HUNTER, 10)
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 2))
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 32))
                .addRequirement(ModStats.WIN_VILLAGE_CAPTURE.get(), 3)
                .build());
        context.register(WEREWOLF_LORD_2, TaskBuilder.builder(WEREWOLF_LORD_2).defaultTitle().setReward(new LordLevelReward(2))
                .unlockedBy(new LordLvlUnlocker(1, true))
                .addRequirement(ModTags.Entities.VAMPIRE, 20)
                .addRequirement(ModTags.Entities.HUNTER, 20)
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 2))
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 32))
                .build());
        context.register(WEREWOLF_LORD_3, TaskBuilder.builder(WEREWOLF_LORD_3).defaultTitle().setReward(new LordLevelReward(3))
                .unlockedBy(new LordLvlUnlocker(2, true))
                .addRequirement(ModTags.Entities.VAMPIRE, 20)
                .addRequirement(ModTags.Entities.HUNTER, 20)
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 3))
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 32))
                .build());
        context.register(WEREWOLF_LORD_4, TaskBuilder.builder(WEREWOLF_LORD_4).defaultTitle().setReward(new LordLevelReward(4))
                .unlockedBy(new LordLvlUnlocker(3, true))
                .addRequirement(ModTags.Entities.VAMPIRE, 35)
                .addRequirement(ModTags.Entities.HUNTER, 35)
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 3))
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 64))
                .build());
        context.register(WEREWOLF_LORD_5, TaskBuilder.builder(WEREWOLF_LORD_5).defaultTitle().setReward(new LordLevelReward(5))
                .unlockedBy(new LordLvlUnlocker(4, true))
                .addRequirement(ModTags.Entities.VAMPIRE, 50)
                .addRequirement(ModTags.Entities.HUNTER, 50)
                .addRequirement(new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 4))
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 64))
                .addRequirement(ModStats.CAPTURE_VILLAGE.get(), 6)
                .build());
        context.register(RANDOM_REFINEMENT_1, TaskBuilder.builder(RANDOM_REFINEMENT_1).defaultTitle().addRequirement(ModTags.Entities.ADVANCED_HUNTER, 10)
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 2))
                .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION))
                .build());
        context.register(RANDOM_REFINEMENT_2, TaskBuilder.builder(RANDOM_REFINEMENT_2).defaultTitle().addRequirement(ModEntities.ALPHA_WEREWOLF.get(), 3)
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 2))
                .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION, IRefinementSet.Rarity.EPIC))
                .build());
        context.register(RANDOM_REFINEMENT_3, TaskBuilder.builder(RANDOM_REFINEMENT_3).defaultTitle().addRequirement(Stats.TRADED_WITH_VILLAGER, 15)
                .addRequirement(new ItemStack(Items.GOLD_INGOT, 2))
                .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION))
                .build());
        context.register(RANDOM_RARE_REFINEMENT, TaskBuilder.builder(RANDOM_RARE_REFINEMENT).defaultTitle().addRequirement(Stats.RAID_WIN, 1)
                .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION, IRefinementSet.Rarity.RARE))
                .build());

        context.register(OBLIVION_POTION, TaskBuilder.builder(OBLIVION_POTION).defaultTitle().setReward(new ItemStack(ModItems.V.OBLIVION_POTION.get()))
                .addRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON))
                .addRequirement(new ItemStack(ModItems.LIVER.get()))
                .addRequirement(new ItemStack(ModItems.V.HUMAN_HEART.get()))
                .build());
    }
}