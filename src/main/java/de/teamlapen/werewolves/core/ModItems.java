package de.teamlapen.werewolves.core;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.vampirism.inventory.HunterWeaponCraftingManager;
import de.teamlapen.werewolves.items.ItemCrossbowArrow;
import de.teamlapen.werewolves.items.ItemSilverAxe;
import de.teamlapen.werewolves.items.ItemSilverHoe;
import de.teamlapen.werewolves.items.ItemSilverPickaxe;
import de.teamlapen.werewolves.items.ItemSilverShovel;
import de.teamlapen.werewolves.items.ItemSilverSword;
import de.teamlapen.werewolves.items.ItemWerewolfBase;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class ModItems {

    public static final ItemSilverSword silver_sword = getNull();
    public static final ItemSilverAxe silver_axe = getNull();
    public static final ItemSilverShovel silver_shovel = getNull();
    public static final ItemSilverPickaxe silver_pickaxe = getNull();
    public static final ItemSilverHoe silver_hoe = getNull();
    public static final ItemWerewolfBase silver_ingot = getNull();
    public static final ItemCrossbowArrow crossbow_arrow = getNull();
    // TODO icon
    public static final ItemWerewolfBase wolfs_pelt = getNull();

    static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new ItemSilverSword());
        registry.register(new ItemSilverAxe());
        registry.register(new ItemSilverShovel());
        registry.register(new ItemSilverPickaxe());
        registry.register(new ItemSilverHoe());
        registry.register(new ItemWerewolfBase("silver_ingot"));
        registry.register(new ItemCrossbowArrow());
        registry.register(new ItemWerewolfBase("wolfs_pelt"));
    }

    static void registerCraftingRecipes() {
        HunterWeaponCraftingManager weaponCraftingManager = HunterWeaponCraftingManager.getInstance();
        weaponCraftingManager.addRecipe(ItemCrossbowArrow.setType(new ItemStack(crossbow_arrow, 2), ItemCrossbowArrow.EnumArrowType.SILVER), 1, (ISkill) null, 1, "    ", " Y  ", " Z  ", " W  ", 'Y', silver_ingot, 'Z', Items.STICK, 'W', Items.FEATHER);
    }

}
