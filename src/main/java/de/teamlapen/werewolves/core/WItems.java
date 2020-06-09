package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.WerewolfvesMod;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.item.*;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WItems {

    public static final Item silver_ingot = getNull();

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new Item((new Item.Properties()).group(WerewolfvesMod.creativeTab)).setRegistryName(REFERENCE.MODID,"silver_ingot"));

        registry.register(new HoeItem(WerewolfvesMod.SILVER_ITEM_TIER,-3.0f,new Item.Properties().group(WerewolfvesMod.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_hoe"));
        registry.register(new ShovelItem(WerewolfvesMod.SILVER_ITEM_TIER,4f,-3.0f,new Item.Properties().group(WerewolfvesMod.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_shovel"));
        registry.register(new AxeItem(WerewolfvesMod.SILVER_ITEM_TIER,4f,-3.0f,new Item.Properties().group(WerewolfvesMod.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_axe"));
        registry.register(new PickaxeItem(WerewolfvesMod.SILVER_ITEM_TIER,4,-3.0f,new Item.Properties().group(WerewolfvesMod.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_pickaxe"));
        registry.register(new SwordItem(WerewolfvesMod.SILVER_ITEM_TIER,4,-3.0f,new Item.Properties().group(WerewolfvesMod.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_sword"));
    }
}
