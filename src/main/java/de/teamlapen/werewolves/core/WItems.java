package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.potions.DrownsyEffect;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class WItems {

    public static final Item silver_ingot = getNull();

    static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new Item((new Item.Properties()).group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID,"silver_ingot"));

        registry.register(new HoeItem(WUtils.SILVER_ITEM_TIER,-3.0f,new Item.Properties().group(WUtils.creativeTab)){
            @Nonnull
            @Override
            public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
                DrownsyEffect.addDrownsyPotion(context.getPlayer());
                return super.onItemUse(context);
            }
        }.setRegistryName(REFERENCE.MODID, "silver_hoe"));
        registry.register(new ShovelItem(WUtils.SILVER_ITEM_TIER,4f,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_shovel"));
        registry.register(new AxeItem(WUtils.SILVER_ITEM_TIER,4f,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_axe"));
        registry.register(new PickaxeItem(WUtils.SILVER_ITEM_TIER,4,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_pickaxe"));
        registry.register(new SwordItem(WUtils.SILVER_ITEM_TIER,4,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_sword"));
    }
}
