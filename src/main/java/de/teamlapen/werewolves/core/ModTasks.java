package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.vampirism.core.ModStats;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.player.tasks.TaskBuilder;
import de.teamlapen.vampirism.player.tasks.reward.LordLevelReward;
import de.teamlapen.vampirism.player.tasks.unlock.LordLvlUnlocker;
import de.teamlapen.vampirism.player.tasks.unlock.LvlUnlocker;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModTasks extends de.teamlapen.vampirism.core.ModTasks {

    public static final Task werewolf_lord1 = getNull();
    public static final Task werewolf_lord2 = getNull();
    public static final Task werewolf_lord3 = getNull();
    public static final Task werewolf_lord4 = getNull();
    public static final Task werewolf_lord5 = getNull();

    public static void registerTasks(IForgeRegistry<Task> registry) {
        //Werewolf lord
        registry.register(TaskBuilder.builder().withFaction(WReference.WEREWOLF_FACTION).unlockedBy(new LvlUnlocker(REFERENCE.HIGHEST_WEREWOLF_LORD_LEVEL)).addRequirement("vampire", ModTags.Entities.VAMPIRE, 10).addRequirement("hunter", ModTags.Entities.HUNTER, 10).addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 2)).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32)).addRequirement("village", ModStats.win_village_capture, 3).setUnique().setReward(new LordLevelReward(1)).build(REFERENCE.MODID,"werewolf_lord1"));
        registry.register(TaskBuilder.builder().withFaction(WReference.WEREWOLF_FACTION).unlockedBy(new LordLvlUnlocker(1, true)).addRequirement("vampire", ModTags.Entities.VAMPIRE, 20).addRequirement("hunter", ModTags.Entities.HUNTER, 20).addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 2)).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32)).setUnique().setReward(new LordLevelReward(2)).build(REFERENCE.MODID,"werewolf_lord2"));
        registry.register(TaskBuilder.builder().withFaction(WReference.WEREWOLF_FACTION).unlockedBy(new LordLvlUnlocker(2, true)).addRequirement("vampire", ModTags.Entities.VAMPIRE, 20).addRequirement("hunter", ModTags.Entities.HUNTER, 20).addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 3)).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 32)).setUnique().setReward(new LordLevelReward(3)).build(REFERENCE.MODID,"werewolf_lord3"));
        registry.register(TaskBuilder.builder().withFaction(WReference.WEREWOLF_FACTION).unlockedBy(new LordLvlUnlocker(3, true)).addRequirement("vampire", ModTags.Entities.VAMPIRE, 35).addRequirement("hunter", ModTags.Entities.HUNTER, 35).addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 3)).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 64)).setUnique().setReward(new LordLevelReward(4)).build(REFERENCE.MODID,"werewolf_lord4"));
        registry.register(TaskBuilder.builder().withFaction(WReference.WEREWOLF_FACTION).unlockedBy(new LordLvlUnlocker(4, true)).addRequirement("vampire", ModTags.Entities.VAMPIRE, 50).addRequirement("hunter", ModTags.Entities.HUNTER, 50).addRequirement("werewolf_tooth", new ItemStack(ModItems.werewolf_tooth, 4)).addRequirement("gold", new ItemStack(Items.GOLD_INGOT, 64)).addRequirement("village", ModStats.capture_village, 6).setUnique().setReward(new LordLevelReward(5)).build(REFERENCE.MODID,"werewolf_lord5"));

        //potion of oblivion
        registry.register(TaskBuilder.builder().addRequirement("poison", PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.POISON)).addRequirement("liver", new ItemStack(ModItems.liver)).addRequirement("heart", new ItemStack(ModItems.human_heart)).setReward(new ItemStack(ModItems.oblivion_potion)).build(REFERENCE.MODID,"oblivion_potion"));
    }
}