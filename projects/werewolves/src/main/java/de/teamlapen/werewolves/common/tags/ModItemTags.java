package de.teamlapen.werewolves.common.tags;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> SILVER_ORE = common("ores/silver");
    public static final TagKey<Item> SILVER_INGOT = common("ingots/silver");
    public static final TagKey<Item> SILVER_NUGGET = common("nuggets/silver");
    public static final TagKey<Item> RAW_MEAT = common("rawmeats");
    public static final TagKey<Item> COOKED_MEAT = common("cookedmeats");
    public static final TagKey<Item> RAW_FISH = werewolves("raw_fish");
    public static final TagKey<Item> COOKED_FISH = werewolves("cooked_fish");
    public static final TagKey<Item> RAW_FOOD = werewolves("raw_food");
    public static final TagKey<Item> COOKED_FOOD = werewolves("cooked_food");
    public static final TagKey<Item> WEREWOLF_FOOD = werewolves("werewolf_food");
    public static final TagKey<Item> SILVER_TOOL = werewolves("tools/silver");
    public static final TagKey<Item> SILVER_ARMOR = werewolves("armor/silver");
    public static final TagKey<Item> SILVER_ITEM = werewolves("type/silver");
    public static final TagKey<Item> RAW_MATERIALS_SILVER = common("raw_materials/silver");
    public static final TagKey<Item> STORAGE_BLOCKS_SILVER = common("storage_blocks/silver");
    public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = common("storage_blocks/raw_silver");
    public static final TagKey<Item> MAGIC_LOG = common("magic_log");
    public static final TagKey<Item> JACARANDA_LOG = common("jacaranda_log");
    public static final TagKey<Item> WOLF_PELT_ARMOR_NORMAL = werewolves("armor/wolf_pelt/normal");
    public static final TagKey<Item> WOLF_PELT_ARMOR_ENHANCED = werewolves("armor/wolf_pelt/enhanced");
    public static final TagKey<Item> WOLF_PELT_ARMOR_ULTIMATE = werewolves("armor/wolf_pelt/ultimate");
    public static final TagKey<Item> WOLF_PELT_ARMOR = werewolves("armor/wolf_pelt");
    public static final TagKey<Item> WOLF_PELT = werewolves("pelt");
    public static final TagKey<Item> WOLF_DARK_PELT = werewolves("dark_pelt");
    public static final TagKey<Item> WOLF_WHITE_PELT = werewolves("white_pelt");
    public static final TagKey<Item> SILVER_REPAIRABLE = werewolves("repairable/silver");


    private static TagKey<Item> mc(Identifier id) {
        return ItemTags.create(id);
    }

    private static TagKey<Item> werewolves(String id) {
        return ItemTags.create(WIdentifier.mod(id));
    }

    private static TagKey<Item> common(String id) {
        return ItemTags.create(WIdentifier.common(id));
    }
}
