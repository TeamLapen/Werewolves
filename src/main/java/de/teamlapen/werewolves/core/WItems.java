package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.werewolves.items.CrossbowArrowItem;
import de.teamlapen.werewolves.potions.DrownsyEffect;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class WItems {

    public static final Item silver_ingot = getNull();
    public static final Item silver_hoe = getNull();
    public static final Item silver_shovel = getNull();
    public static final Item silver_axe = getNull();
    public static final Item silver_pickaxe = getNull();
    public static final Item silver_sword = getNull();
    public static final Item wolfsbane = getNull();
    public static final Item crossbow_arrow_silver_bolt = getNull();

    static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new Item((new Item.Properties()).group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID,"silver_ingot"));

        registry.register(new HoeItem(WUtils.SILVER_ITEM_TIER,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_hoe"));
        registry.register(new ShovelItem(WUtils.SILVER_ITEM_TIER,4f,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_shovel"));
        registry.register(new AxeItem(WUtils.SILVER_ITEM_TIER,4f,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_axe"));
        registry.register(new PickaxeItem(WUtils.SILVER_ITEM_TIER,4,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_pickaxe"));
        registry.register(new SwordItem(WUtils.SILVER_ITEM_TIER,4,-3.0f,new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_sword"));
        registry.register(new CrossbowArrowItem(new CrossbowArrowItem.ArrowType("silver_bolt",3,0xc0c0c0,true,true) {
            @Override
            public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
                if(Helper.isWerewolf(entity)) {
                    //TODO effecs & more damage
                }
            }
        }));
    }
}
