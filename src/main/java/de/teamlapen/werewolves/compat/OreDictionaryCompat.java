package de.teamlapen.werewolves.compat;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;

import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {

    public static void registerOres() {
        OreDictionary.registerOre("oreSilver", ModBlocks.silver_ore);
        OreDictionary.registerOre("ingotSilver", ModItems.silver_ingot);
    }
}
