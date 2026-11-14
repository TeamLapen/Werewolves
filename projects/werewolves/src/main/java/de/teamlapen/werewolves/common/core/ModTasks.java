package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.refinements.IRefinementSet;
import de.teamlapen.faction.api.factions.tasks.Task;
import de.teamlapen.faction.api.util.SafeCast;
import de.teamlapen.faction.common.core.FactionStats;
import de.teamlapen.faction.common.factions.tasks.TaskBuilder;
import de.teamlapen.faction.common.factions.tasks.reward.LordLevelReward;
import de.teamlapen.faction.common.factions.tasks.reward.RefinementItemReward;
import de.teamlapen.faction.common.factions.tasks.unlock.LordLvlUnlocker;
import de.teamlapen.faction.common.factions.tasks.unlock.LvlUnlocker;
import de.teamlapen.vampirism.common.core.ModStats;
import de.teamlapen.vampirism.common.tags.ModEntityTags;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
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
        return ResourceKey.create(FactionRegistries.Keys.TASK, WIdentifier.mod(path));
    }

    public static void createTasks(BootstrapContext<Task> context) {
        var factions = context.lookup(FactionRegistries.Keys.FACTION);
        TaskBuilder.builder()
                .unlockedBy(new LordLvlUnlocker(1))
                .addRequirement(ModEntityTags.ADVANCED_HUNTER, 4)
                .addRequirement(ModEntityTags.ADVANCED_VAMPIRE, 6)
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 32))
                .setReward(new ItemStackTemplate(ModItems.WEREWOLF_MINION_CHARM))
                .build(context::register, WEREWOLF_MINION_BINDING);

        TaskBuilder.builder().setReward(new ItemStackTemplate(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()))
                .unlockedBy(new LordLvlUnlocker(2))
                .addRequirement(ModEntityTags.ADVANCED_HUNTER, 6)
                .addRequirement(ModEntityTags.ADVANCED_VAMPIRE, 8)
                .addRequirement(new ItemStackTemplate(Items.GOLD_BLOCK, 16))
                .build(context::register, WEREWOLF_MINION_UPGRADE_SIMPLE);
        TaskBuilder.builder().setReward(new ItemStackTemplate(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()))
                .unlockedBy(new LordLvlUnlocker(3))
                .addRequirement(ModEntityTags.ADVANCED_HUNTER, 8)
                .addRequirement(ModEntityTags.ADVANCED_VAMPIRE, 10)
                .addRequirement(new ItemStackTemplate(ModItems.LIVER.get(), 16))
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get()))
                .addRequirement(new ItemStackTemplate(Items.DIAMOND_BLOCK, 3))
                .build(context::register, WEREWOLF_MINION_UPGRADE_ENHANCED);
        TaskBuilder.builder().setReward(new ItemStackTemplate(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()))
                .unlockedBy(new LordLvlUnlocker(5))
                .addRequirement(ModEntityTags.ADVANCED_HUNTER, 10)
                .addRequirement(ModEntityTags.ADVANCED_VAMPIRE, 12)
                .addRequirement(new ItemStackTemplate(ModItems.LIVER.get(), 32))
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 5))
                .addRequirement(new ItemStackTemplate(Items.DIAMOND_BLOCK, 8))
                .build(context::register, WEREWOLF_MINION_UPGRADE_SPECIAL);
        TaskBuilder.builder().setReward(new LordLevelReward(1))
                .unlockedBy(new LvlUnlocker(REFERENCE.HIGHEST_WEREWOLF_LEVEL))
                .addRequirement(ModEntityTags.VAMPIRE, 10)
                .addRequirement(ModEntityTags.HUNTER, 10)
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 2))
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 32))
                .addRequirement(FactionStats.WIN_VILLAGE_CAPTURE.get(), 3)
                .build(context::register, WEREWOLF_LORD_1);
        TaskBuilder.builder().setReward(new LordLevelReward(2))
                .unlockedBy(new LordLvlUnlocker(1, true))
                .addRequirement(ModEntityTags.VAMPIRE, 20)
                .addRequirement(ModEntityTags.HUNTER, 20)
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 2))
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 32))
                .build(context::register, WEREWOLF_LORD_2);
        TaskBuilder.builder().setReward(new LordLevelReward(3))
                .unlockedBy(new LordLvlUnlocker(2, true))
                .addRequirement(ModEntityTags.VAMPIRE, 20)
                .addRequirement(ModEntityTags.HUNTER, 20)
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 3))
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 32))
                .build(context::register, WEREWOLF_LORD_3);
        TaskBuilder.builder().setReward(new LordLevelReward(4))
                .unlockedBy(new LordLvlUnlocker(3, true))
                .addRequirement(ModEntityTags.VAMPIRE, 35)
                .addRequirement(ModEntityTags.HUNTER, 35)
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 3))
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 64))
                .build(context::register, WEREWOLF_LORD_4);
        TaskBuilder.builder().setReward(new LordLevelReward(5))
                .unlockedBy(new LordLvlUnlocker(4, true))
                .addRequirement(ModEntityTags.VAMPIRE, 50)
                .addRequirement(ModEntityTags.HUNTER, 50)
                .addRequirement(new ItemStackTemplate(ModItems.WEREWOLF_TOOTH.get(), 4))
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 64))
                .addRequirement(FactionStats.CAPTURE_VILLAGE.get(), 6)
                .build(context::register,WEREWOLF_LORD_5 );
        TaskBuilder.builder().addRequirement(ModEntityTags.ADVANCED_HUNTER, 10)
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 2))
                .setReward(new RefinementItemReward(SafeCast.cast(factions.getOrThrow(ModTags.Factions.IS_WEREWOLF))))
                .build(context::register, RANDOM_REFINEMENT_1);
        TaskBuilder.builder().addRequirement(ModEntities.ALPHA_WEREWOLF.get(), 3)
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 2))
                .setReward(new RefinementItemReward(SafeCast.cast(factions.getOrThrow(ModTags.Factions.IS_WEREWOLF)), IRefinementSet.Rarity.EPIC))
                .build(context::register,RANDOM_REFINEMENT_2);
        TaskBuilder.builder().addRequirement(Stats.TRADED_WITH_VILLAGER, 15)
                .addRequirement(new ItemStackTemplate(Items.GOLD_INGOT, 2))
                .setReward(new RefinementItemReward(SafeCast.cast(factions.getOrThrow(ModTags.Factions.IS_WEREWOLF))))
                .build(context::register, RANDOM_REFINEMENT_3);
        TaskBuilder.builder().addRequirement(Stats.RAID_WIN, 1)
                .setReward(new RefinementItemReward(SafeCast.cast(factions.getOrThrow(ModTags.Factions.IS_WEREWOLF)), IRefinementSet.Rarity.RARE))
                .build(context::register, RANDOM_RARE_REFINEMENT);

        TaskBuilder.builder().setReward(new ItemStackTemplate(ModItems.V.OBLIVION_POTION.get()))
                .addRequirement(new ItemStackTemplate(Items.POTION, DataComponentPatch.builder().set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.POISON)).build()))
                .addRequirement(new ItemStackTemplate(ModItems.LIVER.get()))
                .addRequirement(new ItemStackTemplate(ModItems.V.HUMAN_HEART.get()))
                .build(context::register, OBLIVION_POTION);
    }
}