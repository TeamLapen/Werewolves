package de.teamlapen.vampirewerewolf.compat;

import de.teamlapen.vampirewerewolf.core.ModBlocks;
import de.teamlapen.vampirewerewolf.core.ModItems;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {

    public static void registerOres() {
        OreDictionary.registerOre("oreSilver", ModBlocks.silver_ore);
        OreDictionary.registerOre("ingotSilver", ModItems.silver_ingot);
    }
}
