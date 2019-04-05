package de.teamlapen.vampirewerewolf.core;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import de.teamlapen.vampirewerewolf.VampireWerewolfMod;
import de.teamlapen.vampirewerewolf.items.*;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class ModItems {

    public static final ItemModSword item_silver_sword = getNull();
    public static final ItemModAxe item_silver_axe = getNull();
    public static final ItemModShovel item_silver_shovel = getNull();
    public static final ItemModPickaxe item_silver_pickaxe = getNull();
    public static final ItemModHoe item_silver_hoe = getNull();

    static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new ItemModSword("item_silver_sword", VampireWerewolfMod.TOOL_SILVER));
        registry.register(new ItemModAxe("item_silver_axe", VampireWerewolfMod.TOOL_SILVER));
        registry.register(new ItemModShovel("item_silver_shovel", VampireWerewolfMod.TOOL_SILVER));
        registry.register(new ItemModPickaxe("item_silver_pickaxe", VampireWerewolfMod.TOOL_SILVER));
        registry.register(new ItemModHoe("item_silver_hoe", VampireWerewolfMod.TOOL_SILVER));
    }

}
