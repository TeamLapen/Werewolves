package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.items.*;
import de.teamlapen.werewolves.misc.WerewolvesCreativeTab;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.ModSmithingTemplates;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(REFERENCE.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, REFERENCE.MODID);

    private static final Set<DeferredHolder<Item, ? extends Item>> WEREWOLVES_TAB_ITEMS = new HashSet<>();
    private static final Map<ResourceKey<CreativeModeTab>, Set<DeferredHolder<Item, ? extends Item>>> CREATIVE_TAB_ITEMS = new HashMap<>();

    public static final ResourceKey<CreativeModeTab> CREATIVE_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(REFERENCE.MODID, "default"));
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_TABS.register(CREATIVE_TAB_KEY.location().getPath(), () -> WerewolvesCreativeTab.builder(WEREWOLVES_TAB_ITEMS.stream().map(DeferredHolder::get).collect(Collectors.toSet())).build());

    public static final DeferredItem<Item> SILVER_INGOT = register("silver_ingot", () -> new Item(props()));
    public static final DeferredItem<HoeItem> SILVER_HOE = register("silver_hoe", () -> new HoeItem(WUtils.SILVER_ITEM_TIER, -1, -1.0F, props()));
    public static final DeferredItem<ShovelItem> SILVER_SHOVEL = register("silver_shovel", () -> new ShovelItem(WUtils.SILVER_ITEM_TIER, 1.5F, -3.0F, props()));
    public static final DeferredItem<AxeItem> SILVER_AXE = register("silver_axe", () -> new AxeItem(WUtils.SILVER_ITEM_TIER, 6.0F, -3.1F, props()));
    public static final DeferredItem<PickaxeItem> SILVER_PICKAXE = register("silver_pickaxe", () -> new PickaxeItem(WUtils.SILVER_ITEM_TIER, 1, -2.8F, props()));
    public static final DeferredItem<SilverSwordItem> SILVER_SWORD = register("silver_sword", () -> new SilverSwordItem(WUtils.SILVER_ITEM_TIER, 3, -2.4F, props()));
    public static final DeferredItem<CrossbowArrowItem> CROSSBOW_ARROW_SILVER_BOLT = register("crossbow_arrow_silver_bolt", () -> new CrossbowArrowItem(new CrossbowArrowItem.ArrowType("silver_bolt", 3, 0xc0c0c0, true, true) {
        @Override
        public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
            if (Helper.isWerewolf(entity)) {
                entity.addEffect(SilverEffect.createSilverEffect(entity, WerewolvesConfig.BALANCE.UTIL.silverBoltEffectDuration.get() * 20, 1));
            }
        }
    }));
    public static final DeferredItem<LiverItem> LIVER = register("liver", LiverItem::new);
    public static final DeferredItem<Item> CRACKED_BONE = register("cracked_bone", () -> new Item(props()));
    public static final DeferredItem<UnWerewolfInjectionItem> INJECTION_UN_WEREWOLF = register("injection_un_werewolf", UnWerewolfInjectionItem::new);
    public static final DeferredItem<WerewolfToothItem> WEREWOLF_TOOTH = register("werewolf_tooth", WerewolfToothItem::new);
    public static final DeferredItem<Item> SILVER_NUGGET = register("silver_nugget", () -> new Item(props()));
    public static final DeferredItem<Item> RAW_SILVER = register("raw_silver", () -> new Item(props()));

    public static final DeferredItem<Item> WEREWOLF_MINION_CHARM = register("werewolf_minion_charm", () -> new Item(props()) {
        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltips, @Nonnull TooltipFlag flag) {
            super.appendHoverText(stack, level, tooltips, flag);
            tooltips.add(Component.translatable("item.werewolves.moon_charm.desc").withStyle(ChatFormatting.DARK_GRAY));

        }
    });
    public static final DeferredItem<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SIMPLE = register("werewolf_minion_upgrade_simple", () -> new WerewolfMinionUpgradeItem(props(), 1, 2));
    public static final DeferredItem<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_ENHANCED = register("werewolf_minion_upgrade_enhanced", () -> new WerewolfMinionUpgradeItem(props(), 3, 4));
    public static final DeferredItem<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SPECIAL = register("werewolf_minion_upgrade_special", () -> new WerewolfMinionUpgradeItem(props(), 5, 6));

    public static final DeferredItem<SpawnEggItem> WEREWOLF_BEAST_SPAWN_EGG = register("werewolf_beast_spawn_egg", CreativeModeTabs.SPAWN_EGGS, () -> new DeferredSpawnEggItem(ModEntities.WEREWOLF_BEAST, 0xffc800, 0xfaab00, props()));
    public static final DeferredItem<SpawnEggItem> WEREWOLF_SURVIVALIST_SPAWN_EGG = register("werewolf_survivalist_spawn_egg", CreativeModeTabs.SPAWN_EGGS,() -> new DeferredSpawnEggItem(ModEntities.WEREWOLF_SURVIVALIST, 0xffc800, 0xfae700, props()));
    public static final DeferredItem<SpawnEggItem> HUMAN_WEREWOLF_SPAWN_EGG = register("human_werewolf_spawn_egg", CreativeModeTabs.SPAWN_EGGS,() -> new DeferredSpawnEggItem(ModEntities.HUMAN_WEREWOLF, 0xffc800, 0xa8a8a8, props()));
    public static final DeferredItem<SpawnEggItem> ALPHA_WEREWOLF_SPAWN_EGG = register("alpha_werewolf_spawn_egg", CreativeModeTabs.SPAWN_EGGS,() -> new DeferredSpawnEggItem(ModEntities.ALPHA_WEREWOLF, 0xffc800, 0xca0f00, props()));

    public static final DeferredItem<WerewolfRefinementItem> BONE_NECKLACE = register("bone_necklace", () -> new WerewolfRefinementItem(props(), IRefinementItem.AccessorySlotType.AMULET));
    public static final DeferredItem<WerewolfRefinementItem> CHARM_BRACELET = register("charm_bracelet", () -> new WerewolfRefinementItem(props(), IRefinementItem.AccessorySlotType.RING));
    public static final DeferredItem<WerewolfRefinementItem> DREAM_CATCHER = register("dream_catcher", () -> new WerewolfRefinementItem(props(), IRefinementItem.AccessorySlotType.OBI_BELT));

    public static final DeferredItem<SilverArmorItem> SILVER_HELMET = register("silver_helmet", () -> new SilverArmorItem(ArmorItem.Type.HELMET, props()));
    public static final DeferredItem<SilverArmorItem> SILVER_CHESTPLATE = register("silver_chestplate", () -> new SilverArmorItem(ArmorItem.Type.CHESTPLATE, props()));
    public static final DeferredItem<SilverArmorItem> SILVER_LEGGINGS = register("silver_leggings", () -> new SilverArmorItem(ArmorItem.Type.LEGGINGS, props()));
    public static final DeferredItem<SilverArmorItem> SILVER_BOOTS = register("silver_boots", () -> new SilverArmorItem(ArmorItem.Type.BOOTS, props()));

    public static final DeferredItem<Item> WOLF_BERRIES = register("wolf_berries", () -> new WolfBerries(ModBlocks.WOLF_BERRY_BUSH.get(), props().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build())));

    public static final DeferredItem<SignItem> JACARANDA_SIGN = register("jacaranda_sign", () -> new SignItem(props().stacksTo(16), ModBlocks.JACARANDA_SIGN.get(), ModBlocks.JACARANDA_WALL_SIGN.get()));
    public static final DeferredItem<SignItem> MAGIC_SIGN = register("magic_sign", () -> new SignItem(props().stacksTo(16), ModBlocks.MAGIC_SIGN.get(), ModBlocks.MAGIC_WALL_SIGN.get()));
    public static final DeferredItem<WerewolvesBoatItem> JACARANDA_BOAT = register("jacaranda_boat", () -> new WerewolvesBoatItem(IWerewolvesBoat.BoatType.JACARANDA, false, props().stacksTo(1)));
    public static final DeferredItem<WerewolvesBoatItem> MAGIC_BOAT = register("magic_boat", () -> new WerewolvesBoatItem(IWerewolvesBoat.BoatType.MAGIC, false, props().stacksTo(1)));
    public static final DeferredItem<WerewolvesBoatItem> JACARANDA_CHEST_BOAT = register("jacaranda_chest_boat", () -> new WerewolvesBoatItem(IWerewolvesBoat.BoatType.JACARANDA, true, props().stacksTo(1)));
    public static final DeferredItem<WerewolvesBoatItem> MAGIC_CHEST_BOAT = register("magic_chest_boat", () -> new WerewolvesBoatItem(IWerewolvesBoat.BoatType.MAGIC, true, props().stacksTo(1)));

    public static final DeferredItem<Item> WOLFSBANE_DIFFUSER_CORE = register("wolfsbane_diffuser_core", () -> new Item(props()));
    public static final DeferredItem<Item> WOLFSBANE_DIFFUSER_CORE_IMPROVED = register("wolfsbane_diffuser_core_improved", () -> new Item(props()));
    public static final DeferredItem<Item> PELT = register("pelt", () -> new Item(props()));
    public static final DeferredItem<Item> DARK_PELT = register("dark_pelt", () -> new Item(props()));
    public static final DeferredItem<Item> WHITE_PELT = register("white_pelt", () -> new Item(props()));
    public static final DeferredItem<WolfPeltArmorItem> PELT_HELMET = register("pelt_helmet", () -> new WolfPeltArmorItem(WolfPeltArmorItem.PELT, ArmorItem.Type.HELMET, props()));
    public static final DeferredItem<WolfPeltArmorItem> PELT_CHESTPLATE = register("pelt_chestplate", () -> new WolfPeltArmorItem(WolfPeltArmorItem.PELT, ArmorItem.Type.CHESTPLATE, props()));
    public static final DeferredItem<WolfPeltArmorItem> PELT_LEGGINGS = register("pelt_leggings", () -> new WolfPeltArmorItem(WolfPeltArmorItem.PELT, ArmorItem.Type.LEGGINGS, props()));
    public static final DeferredItem<WolfPeltArmorItem> PELT_BOOTS = register("pelt_boots", () -> new WolfPeltArmorItem(WolfPeltArmorItem.PELT, ArmorItem.Type.BOOTS, props()));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_HELMET = register("dark_pelt_helmet", () -> new WolfPeltArmorItem(WolfPeltArmorItem.DARK_PELT, ArmorItem.Type.HELMET, props()));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_CHESTPLATE = register("dark_pelt_chestplate", () -> new WolfPeltArmorItem(WolfPeltArmorItem.DARK_PELT, ArmorItem.Type.CHESTPLATE, props()));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_LEGGINGS = register("dark_pelt_leggings", () -> new WolfPeltArmorItem(WolfPeltArmorItem.DARK_PELT, ArmorItem.Type.LEGGINGS, props()));
    public static final DeferredItem<WolfPeltArmorItem> DARK_PELT_BOOTS = register("dark_pelt_boots", () -> new WolfPeltArmorItem(WolfPeltArmorItem.DARK_PELT, ArmorItem.Type.BOOTS, props()));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_HELMET = register("white_pelt_helmet", () -> new WolfPeltArmorItem(WolfPeltArmorItem.WHITE_PELT, ArmorItem.Type.HELMET, props()));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_CHESTPLATE = register("white_pelt_chestplate", () -> new WolfPeltArmorItem(WolfPeltArmorItem.WHITE_PELT, ArmorItem.Type.CHESTPLATE, props()));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_LEGGINGS = register("white_pelt_leggings", () -> new WolfPeltArmorItem(WolfPeltArmorItem.WHITE_PELT, ArmorItem.Type.LEGGINGS, props()));
    public static final DeferredItem<WolfPeltArmorItem> WHITE_PELT_BOOTS = register("white_pelt_boots", () -> new WolfPeltArmorItem(WolfPeltArmorItem.WHITE_PELT, ArmorItem.Type.BOOTS, props()));
    public static final DeferredItem<SmithingTemplateItem> WHITE_PELT_UPGRADE_SMITHING_TEMPLATE = register("white_pelt_upgrade_smithing_template", ModSmithingTemplates::createWhitePeltUpgradeTemplate);
    public static final DeferredItem<Item> WOLFSBANE_FINDER = register("wolfsbane_finder", () -> new Item(props().rarity(Rarity.RARE)));


    public static class V {
        public static final DeferredHolder<Item, Item> HUMAN_HEART = item("human_heart");
        public static final DeferredHolder<Item, Item> INJECTION_EMPTY = item("injection_empty");
        public static final DeferredHolder<Item, Item> WEAK_HUMAN_HEART = item("weak_human_heart");
        public static final DeferredHolder<Item, Item> OBLIVION_POTION = item("oblivion_potion");
        public static final DeferredHolder<Item, Item> VAMPIRE_BOOK = item("vampire_book");
        public static final DeferredHolder<Item, Item> CROSSBOW_ARROW_NORMAL = item("crossbow_arrow_normal");
        public static final DeferredHolder<Item, Item> GARLIC_DIFFUSER_CORE = item("garlic_diffuser_core");

        private static DeferredHolder<Item, Item> item(String name) {
            return DeferredHolder.create(ResourceKey.create(Registries.ITEM, new ResourceLocation("vampirism", name)));
        }

        private static void init() {

        }
    }

    static <I extends Item> DeferredItem<I> register(final String name, ResourceKey<CreativeModeTab> tab, final Supplier<? extends I> sup) {
        DeferredItem<I> item = ITEMS.register(name, sup);
        if (tab == CREATIVE_TAB_KEY) {
            WEREWOLVES_TAB_ITEMS.add(item);
        } else {
            CREATIVE_TAB_ITEMS.computeIfAbsent(tab, (a) -> new HashSet<>()).add(item);
        }
        return item;
    }

    static <I extends Item> DeferredItem<I> register(String name, Supplier<? extends I> sup) {
        return register(name, CREATIVE_TAB_KEY, sup);
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
    }

    @ApiStatus.Internal
    public static void registerOtherCreativeTabItems(BuildCreativeModeTabContentsEvent event) {
        CREATIVE_TAB_ITEMS.forEach((tab, items) -> {
            if (event.getTabKey() == tab) {
                items.forEach(item -> event.accept(item.get()));
            }
        });
    }

    private static Item.Properties props() {
        return new Item.Properties();
    }
}
