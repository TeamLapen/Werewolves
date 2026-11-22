package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.world.items.AccessorySlotType;
import de.teamlapen.faction.common.components.FactionRestriction;
import de.teamlapen.faction.common.world.items.RefinementItem;
import de.teamlapen.library.api.world.items.IItemWithTier;
import de.teamlapen.faction.common.world.items.consume.FactionFoodList;
import de.teamlapen.werewolves.common.tags.ModBlockTags;
import de.teamlapen.werewolves.common.tags.ModFactionsTags;
import de.teamlapen.werewolves.common.tags.ModItemTags;
import net.minecraft.world.item.component.Consumables;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.items.*;
import de.teamlapen.werewolves.common.misc.WerewolvesCreativeTab;
import de.teamlapen.werewolves.util.ModSmithingTemplates;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ModItems {
    public static final ToolMaterial SILVER = new ToolMaterial(ModBlockTags.INCORRECT_FOR_SILVER_TOOL, 250,6.0f,2f,14, ModItemTags.SILVER_REPAIRABLE);

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(REFERENCE.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, REFERENCE.MODID);

    public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, WIdentifier.mod("default"));
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register(CREATIVE_TAB_KEY.identifier().getPath(), () -> WerewolvesCreativeTab.builder()
            .withTabsBefore(WIdentifier.loc(REFERENCE.VMODID, REFERENCE.VMODID), WIdentifier.loc(REFERENCE.VMODID, REFERENCE.VMODID + "_hunter"), WIdentifier.loc(REFERENCE.VMODID, REFERENCE.VMODID + "_vampire"))
            .build());

    public static final DeferredItem<Item> SILVER_INGOT = ITEMS.registerItem("silver_ingot", Item::new);
    public static final DeferredItem<HoeItem> SILVER_HOE = ITEMS.registerItem("silver_hoe", (props) -> new HoeItem(SILVER, -1, -1.0f, props));
    public static final DeferredItem<ShovelItem> SILVER_SHOVEL = ITEMS.registerItem("silver_shovel", (props) -> new ShovelItem(SILVER, 1.5f, -3.0f, props));
    public static final DeferredItem<AxeItem> SILVER_AXE = ITEMS.registerItem("silver_axe", (props) -> new AxeItem(SILVER, 6.0f, -3.1f, props));
    public static final DeferredItem<Item> SILVER_PICKAXE = ITEMS.registerItem("silver_pickaxe", (props) -> new Item(props.pickaxe(SILVER, 1, -2.8f)));
    public static final DeferredItem<SilverSwordItem> SILVER_SWORD = ITEMS.registerItem("silver_sword", (props) -> new SilverSwordItem(props, SILVER, 3, -2.4f));
    private static final FoodProperties LIVER_FOOD = new FoodProperties.Builder().nutrition(6).saturationModifier(0.4f).build();
    public static final DeferredItem<Item> LIVER = ITEMS.registerItem("liver", props -> new Item(props.factions$factionFood(new FactionFoodList(LIVER_FOOD, List.of()), Consumables.defaultFood().build())));
    public static final DeferredItem<Item> CRACKED_BONE = ITEMS.registerItem("cracked_bone", Item::new);
    public static final DeferredItem<WerewolfToothItem> WEREWOLF_TOOTH = ITEMS.registerItem("werewolf_tooth", WerewolfToothItem::new);
    public static final DeferredItem<Item> SILVER_NUGGET = ITEMS.registerItem("silver_nugget", Item::new);
    public static final DeferredItem<Item> RAW_SILVER = ITEMS.registerItem("raw_silver", Item::new);

    public static final DeferredItem<Item> WEREWOLF_MINION_CHARM = ITEMS.registerItem("werewolf_minion_charm", (props) -> new Item(props) {

        @Override
        public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
            super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
            builder.accept(Component.translatable("item.werewolves.moon_charm.desc").withStyle(ChatFormatting.DARK_GRAY));
        }
    });
    public static final DeferredItem<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SIMPLE = ITEMS.registerItem("werewolf_minion_upgrade_simple", (props) -> new WerewolfMinionUpgradeItem(props, 1, 2));
    public static final DeferredItem<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_ENHANCED = ITEMS.registerItem("werewolf_minion_upgrade_enhanced", (props) -> new WerewolfMinionUpgradeItem(props, 3, 4));
    public static final DeferredItem<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SPECIAL = ITEMS.registerItem("werewolf_minion_upgrade_special", (props) -> new WerewolfMinionUpgradeItem(props, 5, 6));

    public static final DeferredItem<SpawnEggItem> WEREWOLF_BEAST_SPAWN_EGG = ITEMS.registerItem("werewolf_beast_spawn_egg", SpawnEggItem::new, x -> x.spawnEgg(ModEntities.WEREWOLF_BEAST.get()));
    public static final DeferredItem<SpawnEggItem> WEREWOLF_SURVIVALIST_SPAWN_EGG = ITEMS.registerItem("werewolf_survivalist_spawn_egg", SpawnEggItem::new,(x) -> x.spawnEgg(ModEntities.WEREWOLF_SURVIVALIST.get()));
    public static final DeferredItem<SpawnEggItem> HUMAN_WEREWOLF_SPAWN_EGG = ITEMS.registerItem("human_werewolf_spawn_egg",SpawnEggItem::new,(x) -> x.spawnEgg(ModEntities.HUMAN_WEREWOLF.get()));
    public static final DeferredItem<SpawnEggItem> ALPHA_WEREWOLF_SPAWN_EGG = ITEMS.registerItem("alpha_werewolf_spawn_egg", SpawnEggItem::new,(x) -> x.spawnEgg(ModEntities.ALPHA_WEREWOLF.get()));

    public static final DeferredItem<RefinementItem> BONE_NECKLACE = ITEMS.registerItem("bone_necklace", (props) -> new RefinementItem(props, AccessorySlotType.AMULET), p -> FactionRestriction.builder(ModFactionsTags.IS_WEREWOLF).apply(p));
    public static final DeferredItem<RefinementItem> CHARM_BRACELET = ITEMS.registerItem("charm_bracelet", (props) -> new RefinementItem(props, AccessorySlotType.RING), p -> FactionRestriction.builder(ModFactionsTags.IS_WEREWOLF).apply(p));
    public static final DeferredItem<RefinementItem> DREAM_CATCHER = ITEMS.registerItem("dream_catcher", (props) -> new RefinementItem(props, AccessorySlotType.OBI_BELT), p -> FactionRestriction.builder(ModFactionsTags.IS_WEREWOLF).apply(p));

    public static final DeferredItem<SilverArmorItem> SILVER_HELMET = ITEMS.registerItem("silver_helmet", (props) -> new SilverArmorItem(ArmorType.HELMET, props.durability(ArmorType.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<SilverArmorItem> SILVER_CHESTPLATE = ITEMS.registerItem("silver_chestplate", (props) -> new SilverArmorItem(ArmorType.CHESTPLATE, props.durability(ArmorType.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<SilverArmorItem> SILVER_LEGGINGS = ITEMS.registerItem("silver_leggings", (props) -> new SilverArmorItem(ArmorType.LEGGINGS, props.durability(ArmorType.CHESTPLATE.getDurability(15))));
    public static final DeferredItem<SilverArmorItem> SILVER_BOOTS = ITEMS.registerItem("silver_boots", (props) -> new SilverArmorItem( ArmorType.BOOTS, props.durability(ArmorType.CHESTPLATE.getDurability(15))));

    public static final DeferredItem<Item> WOLF_BERRIES = ITEMS.registerItem("wolf_berries", (props) -> new WolfBerries(ModBlocks.WOLF_BERRY_BUSH.get(), props.food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.1f).build())));

    public static final DeferredItem<SignItem> JACARANDA_SIGN = ITEMS.registerItem("jacaranda_sign", (props) -> new SignItem(ModBlocks.JACARANDA_SIGN.get(), ModBlocks.JACARANDA_WALL_SIGN.get(), props.stacksTo(16)));
    public static final DeferredItem<SignItem> MAGIC_SIGN = ITEMS.registerItem("magic_sign", (props) -> new SignItem(ModBlocks.MAGIC_SIGN.get(), ModBlocks.MAGIC_WALL_SIGN.get(), props.stacksTo(16)));
    public static final DeferredItem<HangingSignItem> JACARANDA_HANGING_SIGN = ITEMS.registerItem("jacaranda_hanging_sign", (props) -> new HangingSignItem(ModBlocks.JACARANDA_HANGING_SIGN.get(), ModBlocks.JACARANDA_WALL_HANGING_SIGN.get(), props.stacksTo(16)));
    public static final DeferredItem<HangingSignItem> MAGIC_HANGING_SIGN = ITEMS.registerItem("magic_hanging_sign", (props) -> new HangingSignItem(ModBlocks.MAGIC_HANGING_SIGN.get(), ModBlocks.MAGIC_WALL_HANGING_SIGN.get(), props.stacksTo(16)));
    public static final DeferredItem<BoatItem> JACARANDA_BOAT = ITEMS.registerItem("jacaranda_boat", (props) -> new BoatItem(ModEntities.JACARANDA_BOAT.get(), props.stacksTo(1)));
    public static final DeferredItem<BoatItem> MAGIC_BOAT = ITEMS.registerItem("magic_boat", (props) -> new BoatItem(ModEntities.MAGIC_BOAT.get(), props.stacksTo(1)));
    public static final DeferredItem<BoatItem> JACARANDA_CHEST_BOAT = ITEMS.registerItem("jacaranda_chest_boat", (props) -> new BoatItem(ModEntities.JACARANDA_CHEST_BOAT.get(), props.stacksTo(1)));
    public static final DeferredItem<BoatItem> MAGIC_CHEST_BOAT = ITEMS.registerItem("magic_chest_boat", (props) -> new BoatItem(ModEntities.MAGIC_CHEST_BOAT.get(), props.stacksTo(1)));

    public static final DeferredItem<Item> PELT = ITEMS.registerItem("pelt", Item::new);
    public static final DeferredItem<Item> DARK_PELT = ITEMS.registerItem("dark_pelt", Item::new);
    public static final DeferredItem<Item> WHITE_PELT = ITEMS.registerItem("white_pelt", Item::new);
    public static final DeferredItem<WolfPeltArmorItem> PELT_HELMET = ITEMS.registerItem("pelt_helmet", (props) -> new WolfPeltArmorItem(ModArmorMaterials.PELT,props, ArmorType.HELMET, IItemWithTier.Tier.NORMAL));
    public static final DeferredItem<WolfPeltArmorItem> PELT_CHESTPLATE = ITEMS.registerItem("pelt_chestplate", (props) -> new WolfPeltArmorItem(ModArmorMaterials.PELT,props, ArmorType.CHESTPLATE, IItemWithTier.Tier.NORMAL));
    public static final DeferredItem<WolfPeltArmorItem> PELT_LEGGINGS = ITEMS.registerItem("pelt_leggings", (props) -> new WolfPeltArmorItem(ModArmorMaterials.PELT, props, ArmorType.LEGGINGS, IItemWithTier.Tier.NORMAL));
    public static final DeferredItem<WolfPeltArmorItem> PELT_BOOTS = ITEMS.registerItem("pelt_boots", (props) -> new WolfPeltArmorItem(ModArmorMaterials.PELT, props, ArmorType.BOOTS, IItemWithTier.Tier.NORMAL));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_HELMET = ITEMS.registerItem("dark_pelt_helmet", (props) -> new WolfPeltArmorItem(ModArmorMaterials.DARK_PELT, props, ArmorType.HELMET, IItemWithTier.Tier.ENHANCED));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_CHESTPLATE = ITEMS.registerItem("dark_pelt_chestplate", (props) -> new WolfPeltArmorItem(ModArmorMaterials.DARK_PELT, props, ArmorType.CHESTPLATE, IItemWithTier.Tier.ENHANCED));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_LEGGINGS = ITEMS.registerItem("dark_pelt_leggings", (props) -> new WolfPeltArmorItem(ModArmorMaterials.DARK_PELT, props, ArmorType.LEGGINGS, IItemWithTier.Tier.ENHANCED));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_BOOTS = ITEMS.registerItem("dark_pelt_boots", (props) -> new WolfPeltArmorItem(ModArmorMaterials.DARK_PELT, props, ArmorType.BOOTS, IItemWithTier.Tier.ENHANCED));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_HELMET = ITEMS.registerItem("white_pelt_helmet", (props) -> new WolfPeltArmorItem(ModArmorMaterials.WHITE_PELT, props, ArmorType.HELMET, IItemWithTier.Tier.ULTIMATE));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_CHESTPLATE = ITEMS.registerItem("white_pelt_chestplate", (props) -> new WolfPeltArmorItem(ModArmorMaterials.WHITE_PELT, props, ArmorType.CHESTPLATE, IItemWithTier.Tier.ULTIMATE));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_LEGGINGS = ITEMS.registerItem("white_pelt_leggings", (props) -> new WolfPeltArmorItem(ModArmorMaterials.WHITE_PELT, props,ArmorType.LEGGINGS, IItemWithTier.Tier.ULTIMATE));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_BOOTS = ITEMS.registerItem("white_pelt_boots", (props) -> new WolfPeltArmorItem(ModArmorMaterials.WHITE_PELT, props, ArmorType.BOOTS, IItemWithTier.Tier.ULTIMATE));
    public static final DeferredItem<SmithingTemplateItem> WHITE_PELT_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerItem("white_pelt_upgrade_smithing_template", ModSmithingTemplates::createWhitePeltUpgradeTemplate);
    public static final DeferredItem<Item> WOLFSBANE_FINDER = ITEMS.registerItem("wolfsbane_finder", (props) -> new Item(props.rarity(Rarity.RARE)));

    public static final DeferredItem<BlockItem> SILVER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.SILVER_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SILVER_ORE = ITEMS.registerSimpleBlockItem(ModBlocks.DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<BlockItem> WOLFSBANE = ITEMS.registerSimpleBlockItem(ModBlocks.WOLFSBANE);
    public static final DeferredItem<BlockItem> SILVER_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.SILVER_BLOCK);
    public static final DeferredItem<BlockItem> RAW_SILVER_BLOCK = ITEMS.registerSimpleBlockItem(ModBlocks.RAW_SILVER_BLOCK);
    public static final DeferredItem<BlockItem> STONE_ALTAR = ITEMS.registerSimpleBlockItem(ModBlocks.STONE_ALTAR);
    public static final DeferredItem<BlockItem> STONE_ALTAR_FIRE_BOWL = ITEMS.registerSimpleBlockItem(ModBlocks.STONE_ALTAR_FIRE_BOWL);
    public static final DeferredItem<BlockItem> DAFFODIL = ITEMS.registerSimpleBlockItem(ModBlocks.DAFFODIL);
    public static final DeferredItem<BlockItem> JACARANDA_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_LEAVES);
    public static final DeferredItem<BlockItem> MAGIC_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_LEAVES);
    public static final DeferredItem<BlockItem> JACARANDA_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_PLANKS);
    public static final DeferredItem<BlockItem> MAGIC_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_PLANKS);
    public static final DeferredItem<BlockItem> STRIPPED_JACARANDA_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_JACARANDA_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_JACARANDA_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_JACARANDA_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAGIC_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_MAGIC_LOG);
    public static final DeferredItem<BlockItem> JACARANDA_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_LOG);
    public static final DeferredItem<BlockItem> MAGIC_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_LOG);
    public static final DeferredItem<BlockItem> MAGIC_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_WOOD);
    public static final DeferredItem<BlockItem> JACARANDA_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_SAPLING);
    public static final DeferredItem<BlockItem> MAGIC_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_SAPLING);
    public static final DeferredItem<BlockItem> MAGIC_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_DOOR);
    public static final DeferredItem<BlockItem> JACARANDA_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_DOOR);
    public static final DeferredItem<BlockItem> JACARANDA_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_TRAPDOOR);
    public static final DeferredItem<BlockItem> MAGIC_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_TRAPDOOR);
    public static final DeferredItem<BlockItem> JACARANDA_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_STAIRS);
    public static final DeferredItem<BlockItem> MAGIC_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_STAIRS);
    public static final DeferredItem<BlockItem> STRIPPED_MAGIC_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_MAGIC_WOOD);
    public static final DeferredItem<BlockItem> JACARANDA_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_WOOD);
    public static final DeferredItem<BlockItem> JACARANDA_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> MAGIC_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> JACARANDA_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_BUTTON);
    public static final DeferredItem<BlockItem> MAGIC_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_BUTTON);
    public static final DeferredItem<BlockItem> JACARANDA_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_SLAB);
    public static final DeferredItem<BlockItem> MAGIC_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_SLAB);
    public static final DeferredItem<BlockItem> JACARANDA_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_FENCE_GATE);
    public static final DeferredItem<BlockItem> MAGIC_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_FENCE_GATE);
    public static final DeferredItem<BlockItem> JACARANDA_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.JACARANDA_FENCE);
    public static final DeferredItem<BlockItem> MAGIC_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.MAGIC_FENCE);

    static void register(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
    }

}
