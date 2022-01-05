package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.inventory.recipes.OilBrewingRecipe;
import de.teamlapen.werewolves.items.*;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@SuppressWarnings("unused")
@ObjectHolder(REFERENCE.MODID)
public class ModItems {

    public static final Item silver_ingot = getNull();
    public static final HoeItem silver_hoe = getNull();
    public static final ShovelItem silver_shovel = getNull();
    public static final AxeItem silver_axe = getNull();
    public static final PickaxeItem silver_pickaxe = getNull();
    public static final SilverSword silver_sword = getNull();
    public static final CrossbowArrowItem crossbow_arrow_silver_bolt = getNull();
    public static final LiverItem liver = getNull();
    public static final Item bone = getNull();
    public static final UnWerewolfInjectionItem injection_un_werewolf = getNull();
    public static final WerewolfToothItem werewolf_tooth = getNull();
    public static final Item silver_nugget = getNull();

    public static final Item werewolf_minion_charm = getNull();
    public static final WerewolfMinionUpgradeItem werewolf_minion_upgrade_simple = getNull();
    public static final WerewolfMinionUpgradeItem werewolf_minion_upgrade_enhanced = getNull();
    public static final WerewolfMinionUpgradeItem werewolf_minion_upgrade_special = getNull();

    public static final SpawnEggItem werewolf_beast_spawn_egg = getNull();
    public static final SpawnEggItem werewolf_survivalist_spawn_egg = getNull();
    public static final SpawnEggItem human_werewolf_spawn_egg = getNull();

    public static final SilverOilItem silver_oil = getNull();
    public static final WerewolfRefinementItem bone_necklace = getNull();
    public static final WerewolfRefinementItem charm_bracelet = getNull();
    public static final WerewolfRefinementItem dream_catcher = getNull();

    @ObjectHolder(de.teamlapen.vampirism.REFERENCE.MODID)
    public static class V {
        public static final Item human_heart = getNull();
        public static final Item injection_empty = getNull();
        public static final Item weak_human_heart = getNull();
        public static final Item oblivion_potion = getNull();
        public static final Item vampire_book = getNull();
    }

    static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(new Item((new Item.Properties()).tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_ingot"));

        registry.register(new HoeItem(WUtils.SILVER_ITEM_TIER, 0, -3.0f, new Item.Properties().tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_hoe"));
        registry.register(new ShovelItem(WUtils.SILVER_ITEM_TIER, 4f, -3.0f, new Item.Properties().tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_shovel"));
        registry.register(new AxeItem(WUtils.SILVER_ITEM_TIER, 4f, -3.0f, new Item.Properties().tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_axe"));
        registry.register(new PickaxeItem(WUtils.SILVER_ITEM_TIER, 4, -3.0f, new Item.Properties().tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_pickaxe"));
        registry.register(new SilverSword(WUtils.SILVER_ITEM_TIER, 4, -3.0f, new Item.Properties().tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "silver_sword"));
        registry.register(new CrossbowArrowItem(new CrossbowArrowItem.ArrowType("silver_bolt", 3, 0xc0c0c0, true, true) {
            @Override
            public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
                if (Helper.isWerewolf(entity)) {
                    entity.addEffect(SilverEffect.createEffect(entity, WerewolvesConfig.BALANCE.UTIL.silverBoltEffectDuration.get() * 20));
                }
            }
        }));

        registry.register(new LiverItem().setRegistryName(REFERENCE.MODID, "liver"));
        registry.register(new Item(new Item.Properties().tab(WUtils.creativeTab)).setRegistryName(REFERENCE.MODID, "bone"));
        registry.register(new UnWerewolfInjectionItem());
        registry.register(new SpawnEggItem(ModEntities.werewolf_beast, 0xffc800, 0xfaab00, new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(REFERENCE.MODID, "werewolf_beast_spawn_egg"));
        registry.register(new SpawnEggItem(ModEntities.werewolf_survivalist, 0xffc800, 0xfae700, new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(REFERENCE.MODID, "werewolf_survivalist_spawn_egg"));
        registry.register(new SpawnEggItem(ModEntities.human_werewolf, 0xffc800, 0xa8a8a8, new Item.Properties().tab(ItemGroup.TAB_MISC)).setRegistryName(REFERENCE.MODID, "human_werewolf_spawn_egg"));
        registry.register(new WerewolfToothItem().setRegistryName(REFERENCE.MODID, "werewolf_tooth"));

        registry.register(new Item(creativeTabProps()).setRegistryName(REFERENCE.MODID, "werewolf_minion_charm"));
        registry.register(new WerewolfMinionUpgradeItem(creativeTabProps(),1, 2).setRegistryName(REFERENCE.MODID,"werewolf_minion_upgrade_simple"));
        registry.register(new WerewolfMinionUpgradeItem( creativeTabProps(),3, 4).setRegistryName(REFERENCE.MODID,"werewolf_minion_upgrade_enhanced"));
        registry.register(new WerewolfMinionUpgradeItem( creativeTabProps(),5, 6).setRegistryName(REFERENCE.MODID,"werewolf_minion_upgrade_special"));

        registry.register(new SilverOilItem(creativeTabProps()).setRegistryName(REFERENCE.MODID, "silver_oil"));
        registry.register(new Item(creativeTabProps()).setRegistryName(REFERENCE.MODID, "silver_nugget"));
        registry.register(new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.AMULET).setRegistryName(REFERENCE.MODID, "bone_necklace"));
        registry.register(new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.RING).setRegistryName(REFERENCE.MODID, "charm_bracelet"));
        registry.register(new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.OBI_BELT).setRegistryName(REFERENCE.MODID, "dream_catcher"));
    }

    static void registerRecipes() {
        BrewingRecipeRegistry.addRecipe(new OilBrewingRecipe(Ingredient.of(new ItemStack(Items.POTION)), ModTags.Items.SILVER_NUGGET, new ItemStack(ModItems.silver_oil)));

    }

    private static Item.Properties creativeTabProps() {
        return new Item.Properties().tab(WUtils.creativeTab);
    }
}
