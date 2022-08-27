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
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModTasks {

    public static final DeferredRegister<Task> TASKS = DeferredRegister.create(VampirismRegistries.TASK_ID, REFERENCE.MODID);

    public static final RegistryObject<Task> WEREWOLF_MINION_BINDING = TASKS.register("werewolf_minion_binding", () -> werewolf()
            .setReward(() -> new ItemStack(ModItems.WEREWOLF_MINION_CHARM.get()))
            .unlockedBy(new LordLvlUnlocker(1))
            .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 4)
            .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 6)
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 32))
            .build());
    public static final RegistryObject<Task> WEREWOLF_MINION_UPGRADE_SIMPLE = TASKS.register("werewolf_minion_upgrade_simple", () -> werewolf()
            .setReward(() -> new ItemStack(ModItems.WEREWOLF_MINION_UPGRADE_SIMPLE.get()))
            .unlockedBy(new LordLvlUnlocker(2))
            .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 6)
            .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 8)
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_BLOCK, 16))
            .build());
    public static final RegistryObject<Task> WEREWOLF_MINION_UPGRADE_ENHANCED = TASKS.register("werewolf_minion_upgrade_enhanced", () -> werewolf()
            .setReward(() -> new ItemStack(ModItems.WEREWOLF_MINION_UPGRADE_ENHANCED.get()))
            .unlockedBy(new LordLvlUnlocker(3))
            .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 8)
            .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 10)
            .addRequirement("liver", () -> new ItemStack(ModItems.LIVER.get(), 16))
            .addRequirement("tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get()))
            .addRequirement("diamond", () -> new ItemStack(Items.DIAMOND_BLOCK, 3))
            .build());
    public static final RegistryObject<Task> WEREWOLF_MINION_UPGRADE_SPECIAL = TASKS.register("werewolf_minion_upgrade_special", () -> werewolf()
            .setReward(() -> new ItemStack(ModItems.WEREWOLF_MINION_UPGRADE_SPECIAL.get()))
            .unlockedBy(new LordLvlUnlocker(5))
            .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 10)
            .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 12)
            .addRequirement("liver", () -> new ItemStack(ModItems.LIVER.get(), 32))
            .addRequirement("tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 5))
            .addRequirement("diamond", () -> new ItemStack(Items.DIAMOND_BLOCK, 8))
            .build());

    public static final RegistryObject<Task> WEREWOLF_LORD_1 = TASKS.register("werewolf_lord1", () -> werewolf()
            .setReward(new LordLevelReward(1))
            .unlockedBy(new LvlUnlocker(REFERENCE.HIGHEST_WEREWOLF_LEVEL))
            .addRequirement("vampire", ModTags.Entities.VAMPIRE, 10)
            .addRequirement("hunter", ModTags.Entities.HUNTER, 10)
            .addRequirement("werewolf_tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 2))
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 32))
            .addRequirement("village", ModStats.win_village_capture, 3)
            .setUnique()
            .build());
    public static final RegistryObject<Task> WEREWOLF_LORD_2 = TASKS.register("werewolf_lord2", () -> werewolf()
            .setReward(new LordLevelReward(2))
            .unlockedBy(new LordLvlUnlocker(1, true))
            .addRequirement("vampire", ModTags.Entities.VAMPIRE, 20)
            .addRequirement("hunter", ModTags.Entities.HUNTER, 20)
            .addRequirement("werewolf_tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 2))
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 32))
            .setUnique()
            .build());
    public static final RegistryObject<Task> WEREWOLF_LORD_3 = TASKS.register("werewolf_lord3", () -> werewolf()
            .setReward(new LordLevelReward(3))
            .unlockedBy(new LordLvlUnlocker(2, true))
            .addRequirement("vampire", ModTags.Entities.VAMPIRE, 20)
            .addRequirement("hunter", ModTags.Entities.HUNTER, 20)
            .addRequirement("werewolf_tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 3))
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 32))
            .setUnique()
            .build());
    public static final RegistryObject<Task> WEREWOLF_LORD_4 = TASKS.register("werewolf_lord4", () -> werewolf()
            .setReward(new LordLevelReward(4))
            .unlockedBy(new LordLvlUnlocker(3, true))
            .addRequirement("vampire", ModTags.Entities.VAMPIRE, 35)
            .addRequirement("hunter", ModTags.Entities.HUNTER, 35)
            .addRequirement("werewolf_tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 3))
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 64)).
            setUnique()
            .build());
    public static final RegistryObject<Task> WEREWOLF_LORD_5 = TASKS.register("werewolf_lord5", () -> werewolf()
            .setReward(new LordLevelReward(5))
            .unlockedBy(new LordLvlUnlocker(4, true))
            .addRequirement("vampire", ModTags.Entities.VAMPIRE, 50)
            .addRequirement("hunter", ModTags.Entities.HUNTER, 50)
            .addRequirement("werewolf_tooth", () -> new ItemStack(ModItems.WEREWOLF_TOOTH.get(), 4))
            .addRequirement("gold", () ->  new ItemStack(Items.GOLD_INGOT, 64))
            .addRequirement("village", ModStats.capture_village, 6)
            .setUnique()
            .build());

    public static final RegistryObject<Task> OBLIVION_POTION = TASKS.register("oblivion_potion", () -> TaskBuilder.builder()
            .setReward(() -> new ItemStack(ModItems.V.OBLIVION_POTION.get()))
            .addRequirement("poison", () -> PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON))
            .addRequirement("liver", () -> new ItemStack(ModItems.LIVER.get()))
            .addRequirement("heart", () -> new ItemStack(ModItems.V.HUMAN_HEART.get()))
            .build());

    public static final RegistryObject<Task> RANDOM_REFINEMENT_1 = TASKS.register("random_refinement1", () -> werewolf()
            .addRequirement("hunter", ModTags.Entities.ADVANCED_HUNTER, 10)
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 2))
            .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION))
            .build());
    public static final RegistryObject<Task> RANDOM_REFINEMENT_2 = TASKS.register("random_refinement2", () -> werewolf()
            .addRequirement("alphas", ModEntities.ALPHA_WEREWOLF.get(), 3)
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 2))
            .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION, IRefinementSet.Rarity.EPIC))
            .build());
    public static final RegistryObject<Task> RANDOM_REFINEMENT_3 = TASKS.register("random_refinement3", () -> werewolf()
            .addRequirement("trades", Stats.TRADED_WITH_VILLAGER, 15)
            .addRequirement("gold", () -> new ItemStack(Items.GOLD_INGOT, 2))
            .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION))
            .build());
    public static final RegistryObject<Task> RANDOM_RARE_REFINEMENT = TASKS.register("random_rare_refinement", () -> werewolf()
            .addRequirement("raid", Stats.RAID_WIN, 1)
            .setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION, IRefinementSet.Rarity.RARE))
            .build());

    static void register(IEventBus bus) {
        TASKS.register(bus);
    }

    protected static TaskBuilder werewolf() {
        return TaskBuilder.builder().withFaction(() -> WReference.WEREWOLF_FACTION);
    }
}