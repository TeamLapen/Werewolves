package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.vampirism.core.ModStats;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.player.tasks.TaskBuilder;
import de.teamlapen.vampirism.player.tasks.reward.LordLevelReward;
import de.teamlapen.vampirism.player.tasks.reward.RefinementItemReward;
import de.teamlapen.vampirism.player.tasks.unlock.LordLvlUnlocker;
import de.teamlapen.vampirism.player.tasks.unlock.LvlUnlocker;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class ModTasks {

    public static final Task werewolf_minion_binding = getNull();
    public static final Task werewolf_minion_upgrade_simple = getNull();
    public static final Task werewolf_minion_upgrade_enhanced = getNull();
    public static final Task werewolf_minion_upgrade_special = getNull();

    public static final Task werewolf_lord1 = getNull();
    public static final Task werewolf_lord2 = getNull();
    public static final Task werewolf_lord3 = getNull();
    public static final Task werewolf_lord4 = getNull();
    public static final Task werewolf_lord5 = getNull();

    public static final Task oblivion_potion = getNull();

    public static void registerTasks(IForgeRegistry<Task> registry) {

        //Werewolf minion
        {
            registry.register(werewolf()
                    .setReward(new ItemStack(ModItems.werewolf_minion_charm))
                    .unlockedBy(new LordLvlUnlocker(1))
                    .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 4)
                    .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 6)
                    .addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32))
                    .build(REFERENCE.MODID, "werewolf_minion_binding"));
            registry.register(werewolf()
                    .setReward(new ItemStack(ModItems.werewolf_minion_upgrade_simple))
                    .unlockedBy(new LordLvlUnlocker(2))
                    .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 6)
                    .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 8)
                    .addRequirement("gold", new ItemStack(Items.GOLD_BLOCK, 16))
                    .build(REFERENCE.MODID, "werewolf_minion_upgrade_simple"));
            registry.register(werewolf()
                    .setReward(new ItemStack(ModItems.werewolf_minion_upgrade_enhanced))
                    .unlockedBy(new LordLvlUnlocker(3))
                    .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 8)
                    .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 10)
                    .addRequirement("liver", new ItemStack(ModItems.liver, 16))
                    .addRequirement("tooth", new ItemStack(ModItems.werewolf_tooth))
                    .addRequirement("diamond", new ItemStack(Items.DIAMOND_BLOCK, 3))
                    .build(REFERENCE.MODID, "werewolf_minion_upgrade_enhanced"));
            registry.register(werewolf()
                    .setReward(new ItemStack(ModItems.werewolf_minion_upgrade_special))
                    .unlockedBy(new LordLvlUnlocker(5))
                    .addRequirement("advanced_hunter", ModTags.Entities.ADVANCED_HUNTER, 10)
                    .addRequirement("advanced_vampire", ModTags.Entities.ADVANCED_VAMPIRE, 12)
                    .addRequirement("liver", new ItemStack(ModItems.liver, 32))
                    .addRequirement("tooth", new ItemStack(ModItems.werewolf_tooth, 5))
                    .addRequirement("diamond", new ItemStack(Items.DIAMOND_BLOCK, 8))
                    .build(REFERENCE.MODID, "werewolf_minion_upgrade_special"));
        }
        //Werewolf lord
        {
            registry.register(werewolf()
                    .setReward(new LordLevelReward(1))
                    .unlockedBy(new LvlUnlocker(REFERENCE.HIGHEST_WEREWOLF_LEVEL))
                    .addRequirement("vampire", ModTags.Entities.VAMPIRE, 10)
                    .addRequirement("hunter", ModTags.Entities.HUNTER, 10)
                    .addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 2))
                    .addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32))
                    .addRequirement("village", ModStats.win_village_capture, 3)
                    .setUnique()
                    .build(REFERENCE.MODID, "werewolf_lord1"));
            registry.register(werewolf()
                    .setReward(new LordLevelReward(2))
                    .unlockedBy(new LordLvlUnlocker(1, true))
                    .addRequirement("vampire", ModTags.Entities.VAMPIRE, 20)
                    .addRequirement("hunter", ModTags.Entities.HUNTER, 20)
                    .addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 2))
                    .addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32))
                    .setUnique()
                    .build(REFERENCE.MODID, "werewolf_lord2"));
            registry.register(werewolf()
                    .setReward(new LordLevelReward(3))
                    .unlockedBy(new LordLvlUnlocker(2, true))
                    .addRequirement("vampire", ModTags.Entities.VAMPIRE, 20)
                    .addRequirement("hunter", ModTags.Entities.HUNTER, 20)
                    .addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 3))
                    .addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32))
                    .setUnique()
                    .build(REFERENCE.MODID, "werewolf_lord3"));
            registry.register(werewolf()
                    .setReward(new LordLevelReward(4))
                    .unlockedBy(new LordLvlUnlocker(3, true))
                    .addRequirement("vampire", ModTags.Entities.VAMPIRE, 35)
                    .addRequirement("hunter", ModTags.Entities.HUNTER, 35)
                    .addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 3))
                    .addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 64)).
                    setUnique()
                    .build(REFERENCE.MODID, "werewolf_lord4"));
            registry.register(werewolf()
                    .setReward(new LordLevelReward(5))
                    .unlockedBy(new LordLvlUnlocker(4, true))
                    .addRequirement("vampire", ModTags.Entities.VAMPIRE, 50)
                    .addRequirement("hunter", ModTags.Entities.HUNTER, 50)
                    .addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 4))
                    .addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 64))
                    .addRequirement("village", ModStats.capture_village, 6)
                    .setUnique()
                    .build(REFERENCE.MODID, "werewolf_lord5"));
        }
        //potion of oblivion
        {
            registry.register(TaskBuilder.builder()
                    .setReward(new ItemStack(ModItems.V.oblivion_potion))
                    .addRequirement("poison", PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON))
                    .addRequirement("liver", new ItemStack(ModItems.liver))
                    .addRequirement("heart", new ItemStack(ModItems.V.human_heart))
                    .build(REFERENCE.MODID, "oblivion_potion"));
        }

        //vampire refinement items
        {
            registry.register(werewolf().addRequirement("hunter", ModTags.Entities.ADVANCED_HUNTER, 10).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 2)).setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION)).build("random_refinement1"));
            registry.register(werewolf().addRequirement("alphas", ModEntities.alpha_werewolf, 3).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 2)).setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION, IRefinementSet.Rarity.EPIC)).build("random_refinement2"));
            registry.register(werewolf().addRequirement("trades", Stats.TRADED_WITH_VILLAGER, 15).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 2)).setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION)).build("random_refinement3"));
            registry.register(werewolf().addRequirement("raid", Stats.RAID_WIN, 1).setReward(new RefinementItemReward(WReference.WEREWOLF_FACTION, IRefinementSet.Rarity.RARE)).build("random_rare_refinement"));
        }
    }

    protected static TaskBuilder werewolf() {
        return TaskBuilder.builder().withFaction(WReference.WEREWOLF_FACTION);
    }
}