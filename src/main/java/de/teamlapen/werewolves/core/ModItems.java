package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.inventory.recipes.TagNBTBrewingRecipe;
import de.teamlapen.werewolves.items.*;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.OilUtils;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings("unused")
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, REFERENCE.MODID);

    public static final RegistryObject<Item> silver_ingot = ITEMS.register("silver_ingot", () -> new Item((new Item.Properties()).tab(WUtils.creativeTab)));
    public static final RegistryObject<HoeItem> silver_hoe = ITEMS.register("silver_hoe", () -> new HoeItem(WUtils.SILVER_ITEM_TIER, 1, -1.5f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<ShovelItem> silver_shovel = ITEMS.register("silver_shovel", () -> new ShovelItem(WUtils.SILVER_ITEM_TIER, 6.5f, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<AxeItem> silver_axe = ITEMS.register("silver_axe", () -> new AxeItem(WUtils.SILVER_ITEM_TIER, 5.5f, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<PickaxeItem> silver_pickaxe = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(WUtils.SILVER_ITEM_TIER, 4, -2.8f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<SilverSwordItem> silver_sword = ITEMS.register("silver_sword", () -> new SilverSwordItem(WUtils.SILVER_ITEM_TIER, 4, -2.4f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<CrossbowArrowItem> crossbow_arrow_silver_bolt = ITEMS.register("crossbow_arrow_silver_bolt", () -> new CrossbowArrowItem(new CrossbowArrowItem.ArrowType("silver_bolt", 3, 0xc0c0c0, true, true) {
        @Override
        public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
            if (Helper.isWerewolf(entity)) {
                entity.addEffect(SilverEffect.createEffect(entity, WerewolvesConfig.BALANCE.UTIL.silverBoltEffectDuration.get() * 20));
            }
        }
    }));
    public static final RegistryObject<LiverItem> liver = ITEMS.register("liver", LiverItem::new);
    public static final RegistryObject<Item> cracked_bone = ITEMS.register("cracked_bone", () -> new Item(new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<UnWerewolfInjectionItem> injection_un_werewolf = ITEMS.register("injection_un_werewolf", UnWerewolfInjectionItem::new);
    public static final RegistryObject<WerewolfToothItem> werewolf_tooth = ITEMS.register("werewolf_tooth", WerewolfToothItem::new);
    public static final RegistryObject<Item> silver_nugget = ITEMS.register("silver_nugget", () -> new Item(creativeTabProps()));
    public static final RegistryObject<Item> raw_silver = ITEMS.register("raw_silver", () -> new Item(creativeTabProps()));

    public static final RegistryObject<Item> werewolf_minion_charm = ITEMS.register("werewolf_minion_charm", () -> new Item(creativeTabProps()) {
        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level level, @Nonnull List<Component> tooltips, @Nonnull TooltipFlag flag) {
            super.appendHoverText(stack, level, tooltips, flag);
            tooltips.add(new TranslatableComponent("item.werewolves.moon_charm.desc").withStyle(ChatFormatting.DARK_GRAY));

        }
    });
    public static final RegistryObject<WerewolfMinionUpgradeItem> werewolf_minion_upgrade_simple = ITEMS.register("werewolf_minion_upgrade_simple", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 1, 2));
    public static final RegistryObject<WerewolfMinionUpgradeItem> werewolf_minion_upgrade_enhanced = ITEMS.register("werewolf_minion_upgrade_enhanced", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 3, 4));
    public static final RegistryObject<WerewolfMinionUpgradeItem> werewolf_minion_upgrade_special = ITEMS.register("werewolf_minion_upgrade_special", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 5, 6));

    public static final RegistryObject<SpawnEggItem> werewolf_beast_spawn_egg = ITEMS.register("werewolf_beast_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.werewolf_beast, 0xffc800, 0xfaab00, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> werewolf_survivalist_spawn_egg = ITEMS.register("werewolf_survivalist_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.werewolf_survivalist, 0xffc800, 0xfae700, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> human_werewolf_spawn_egg = ITEMS.register("human_werewolf_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.human_werewolf, 0xffc800, 0xa8a8a8, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> alpha_werewolf_spawn_egg = ITEMS.register("alpha_werewolf_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.alpha_werewolf, 0xffc800, 0xca0f00, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<OilItem> oil_bottle = ITEMS.register("oil_bottle", () -> new OilItem(creativeTabProps().stacksTo(1)));
    public static final RegistryObject<WerewolfRefinementItem> bone_necklace = ITEMS.register("bone_necklace", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.AMULET));
    public static final RegistryObject<WerewolfRefinementItem> charm_bracelet = ITEMS.register("charm_bracelet", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.RING));
    public static final RegistryObject<WerewolfRefinementItem> dream_catcher = ITEMS.register("dream_catcher", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.OBI_BELT));

    public static class V {
        public static final RegistryObject<Item> human_heart = item("human_heart");
        public static final RegistryObject<Item> injection_empty = item("injection_empty");
        public static final RegistryObject<Item> weak_human_heart = item("weak_human_heart");
        public static final RegistryObject<Item> oblivion_potion = item("oblivion_potion");
        public static final RegistryObject<Item> vampire_book = item("vampire_book");
        public static final RegistryObject<Item> crossbow_arrow_normal = item("crossbow_arrow_normal");

        private static RegistryObject<Item> item(String name) {
            return RegistryObject.create(new ResourceLocation("vampirism", name), ForgeRegistries.ITEMS);
        }

        static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static void remapItems(RegistryEvent.MissingMappings<Item> event) {
        event.getAllMappings().forEach(missingMapping -> {
            switch (missingMapping.key.toString()) {
                case "werewolves:bone" -> missingMapping.remap(ModItems.cracked_bone.get());
                case "werewolves:silver_oil" -> missingMapping.remap(ModItems.oil_bottle.get());
            }
        });
    }

    static void registerOilRecipes() {
        BrewingRecipeRegistry.addRecipe(new TagNBTBrewingRecipe(Ingredient.of(Items.GLASS_BOTTLE), Ingredient.of(Items.WHEAT_SEEDS), ModItems.oil_bottle.get().withOil(ModOils.plant_oil.get())));
        BrewingRecipeRegistry.addRecipe(new TagNBTBrewingRecipe(Ingredient.of(OilUtils.setOil(new ItemStack(ModItems.oil_bottle.get()), ModOils.plant_oil.get())), ModTags.Items.SILVER_INGOT, ModItems.oil_bottle.get().withOil(ModOils.silver_oil_1.get())));
        BrewingRecipeRegistry.addRecipe(new TagNBTBrewingRecipe(Ingredient.of(OilUtils.setOil(new ItemStack(ModItems.oil_bottle.get()), ModOils.silver_oil_1.get())), ModTags.Items.SILVER_INGOT, ModItems.oil_bottle.get().withOil(ModOils.silver_oil_2.get())));
    }

    private static Item.Properties creativeTabProps() {
        return new Item.Properties().tab(WUtils.creativeTab);
    }
}
