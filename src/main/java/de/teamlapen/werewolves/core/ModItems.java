package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.items.*;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.Keys.ITEMS, REFERENCE.MODID);

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item((new Item.Properties()).tab(WUtils.creativeTab)));
    public static final RegistryObject<HoeItem> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(WUtils.SILVER_ITEM_TIER, -1, -1.0F, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<ShovelItem> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(WUtils.SILVER_ITEM_TIER, 1.5F, -3.0F, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<AxeItem> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(WUtils.SILVER_ITEM_TIER, 6.0F, -3.1F, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(WUtils.SILVER_ITEM_TIER, 1, -2.8F, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<SilverSwordItem> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSwordItem(WUtils.SILVER_ITEM_TIER, 3, -2.4F, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<CrossbowArrowItem> CROSSBOW_ARROW_SILVER_BOLT = ITEMS.register("crossbow_arrow_silver_bolt", () -> new CrossbowArrowItem(new CrossbowArrowItem.ArrowType("silver_bolt", 3, 0xc0c0c0, true, true) {
        @Override
        public void onHitEntity(ItemStack arrow, LivingEntity entity, IEntityCrossbowArrow arrowEntity, Entity shootingEntity) {
            if (Helper.isWerewolf(entity)) {
                entity.addEffect(SilverEffect.createEffect(entity, WerewolvesConfig.BALANCE.UTIL.silverBoltEffectDuration.get() * 20));
            }
        }
    }));
    public static final RegistryObject<LiverItem> LIVER = ITEMS.register("liver", LiverItem::new);
    public static final RegistryObject<Item> CRACKED_BONE = ITEMS.register("cracked_bone", () -> new Item(new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<UnWerewolfInjectionItem> INJECTION_UN_WEREWOLF = ITEMS.register("injection_un_werewolf", UnWerewolfInjectionItem::new);
    public static final RegistryObject<WerewolfToothItem> WEREWOLF_TOOTH = ITEMS.register("werewolf_tooth", WerewolfToothItem::new);
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(creativeTabProps()));
    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver", () -> new Item(creativeTabProps()));

    public static final RegistryObject<Item> WEREWOLF_MINION_CHARM = ITEMS.register("werewolf_minion_charm", () -> new Item(creativeTabProps()) {
        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltips, @NotNull TooltipFlag flag) {
            super.appendHoverText(stack, level, tooltips, flag);
            tooltips.add(Component.translatable("item.werewolves.moon_charm.desc").withStyle(ChatFormatting.DARK_GRAY));

        }
    });
    public static final RegistryObject<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SIMPLE = ITEMS.register("werewolf_minion_upgrade_simple", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 1, 2));
    public static final RegistryObject<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_ENHANCED = ITEMS.register("werewolf_minion_upgrade_enhanced", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 3, 4));
    public static final RegistryObject<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SPECIAL = ITEMS.register("werewolf_minion_upgrade_special", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 5, 6));

    public static final RegistryObject<SpawnEggItem> WEREWOLF_BEAST_SPAWN_EGG = ITEMS.register("werewolf_beast_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.WEREWOLF_BEAST, 0xffc800, 0xfaab00, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> WEREWOLF_SURVIVALIST_SPAWN_EGG = ITEMS.register("werewolf_survivalist_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.WEREWOLF_SURVIVALIST, 0xffc800, 0xfae700, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> HUMAN_WEREWOLF_SPAWN_EGG = ITEMS.register("human_werewolf_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HUMAN_WEREWOLF, 0xffc800, 0xa8a8a8, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> ALPHA_WEREWOLF_SPAWN_EGG = ITEMS.register("alpha_werewolf_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ALPHA_WEREWOLF, 0xffc800, 0xca0f00, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<WerewolfRefinementItem> BONE_NECKLACE = ITEMS.register("bone_necklace", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.AMULET));
    public static final RegistryObject<WerewolfRefinementItem> CHARM_BRACELET = ITEMS.register("charm_bracelet", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.RING));
    public static final RegistryObject<WerewolfRefinementItem> DREAM_CATCHER = ITEMS.register("dream_catcher", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.OBI_BELT));

    public static class V {
        public static final RegistryObject<Item> HUMAN_HEART = item("human_heart");
        public static final RegistryObject<Item> INJECTION_EMPTY = item("injection_empty");
        public static final RegistryObject<Item> WEAK_HUMAN_HEART = item("weak_human_heart");
        public static final RegistryObject<Item> OBLIVION_POTION = item("oblivion_potion");
        public static final RegistryObject<Item> VAMPIRE_BOOK = item("vampire_book");
        public static final RegistryObject<Item> CROSSBOW_ARROW_NORMAL = item("crossbow_arrow_normal");

        private static RegistryObject<Item> item(String name) {
            return RegistryObject.create(new ResourceLocation("vampirism", name), ForgeRegistries.Keys.ITEMS, REFERENCE.MODID);
        }

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static void remapItems(MissingMappingsEvent event) {
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach(missingMapping -> {
            switch (missingMapping.getKey().toString()) {
                case "werewolves:bone" -> missingMapping.remap(ModItems.CRACKED_BONE.get());
                case "werewolves:silver_oil" -> missingMapping.remap(de.teamlapen.vampirism.core.ModItems.OIL_BOTTLE.get());
            }
        });
    }

    private static Item.Properties creativeTabProps() {
        return new Item.Properties().tab(WUtils.creativeTab);
    }
}
