package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.items.CrossbowArrowItem;
import de.teamlapen.werewolves.items.LiverItem;
import de.teamlapen.werewolves.items.SilverSword;
import de.teamlapen.werewolves.items.WerewolvesItem;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class ModItems extends de.teamlapen.vampirism.core.ModItems {

    public static final WerewolvesItem silver_ingot = getNull();
    public static final HoeItem silver_hoe = getNull();
    public static final ShovelItem silver_shovel = getNull();
    public static final AxeItem silver_axe = getNull();
    public static final PickaxeItem silver_pickaxe = getNull();
    public static final SilverSword silver_sword = getNull();
    public static final CrossbowArrowItem crossbow_arrow_silver_bolt = getNull();
    public static final LiverItem liver = getNull();
    public static final WerewolvesItem bone = getNull();

    static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new WerewolvesItem("silver_ingot", (new Item.Properties()).group(WUtils.creativeTab)));

        registry.register(new HoeItem(WUtils.SILVER_ITEM_TIER, -3.0f, new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_hoe"));
        registry.register(new ShovelItem(WUtils.SILVER_ITEM_TIER, 4f, -3.0f, new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_shovel"));
        registry.register(new AxeItem(WUtils.SILVER_ITEM_TIER, 4f, -3.0f, new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_axe"));
        registry.register(new PickaxeItem(WUtils.SILVER_ITEM_TIER, 4, -3.0f, new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_pickaxe"));
        registry.register(new SilverSword(WUtils.SILVER_ITEM_TIER, 4, -3.0f, new Item.Properties().group(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_sword"));
        registry.register(new CrossbowArrowItem(new CrossbowArrowItem.ArrowType("silver_bolt", 3, 0xc0c0c0, true, true) {
            @Override
            public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
                if (Helper.isWerewolf(entity)) {
                    entity.addPotionEffect(new EffectInstance(ModEffects.silver, WerewolvesConfig.BALANCE.UTIL.silverBoltEffectDuration.get() * 20));
                }
            }
        }));

        registry.register(new LiverItem());
        registry.register(new WerewolvesItem("bone", new Item.Properties().group(WUtils.creativeTab)));
    }
}
