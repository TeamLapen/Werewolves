package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.items.IEntityCrossbowArrow;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.items.*;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, REFERENCE.MODID);

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item((new Item.Properties()).tab(WUtils.creativeTab)));
    public static final RegistryObject<HoeItem> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(WUtils.SILVER_ITEM_TIER, 0, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<ShovelItem> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(WUtils.SILVER_ITEM_TIER, 4f, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<AxeItem> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(WUtils.SILVER_ITEM_TIER, 4f, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<PickaxeItem> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(WUtils.SILVER_ITEM_TIER, 4, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
    public static final RegistryObject<SilverSword> SILVER_SWORD = ITEMS.register("silver_sword", () -> new SilverSword(WUtils.SILVER_ITEM_TIER, 4, -3.0f, new Item.Properties().tab(WUtils.creativeTab)));
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
    public static final RegistryObject<Item> SILVER_NUGGET = ITEMS.register("silver_nugget", () -> new Item(new Item.Properties().tab(WUtils.creativeTab)));

    public static final RegistryObject<Item> WEREWOLF_MINION_CHARM = ITEMS.register("werewolf_minion_charm", () -> new Item(creativeTabProps()) {
        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable World level, @Nonnull List<ITextComponent> tooltips, @Nonnull ITooltipFlag flag) {
            super.appendHoverText(stack, level, tooltips, flag);
            tooltips.add(new TranslationTextComponent("item.werewolves.moon_charm.desc").withStyle(TextFormatting.DARK_GRAY));

        }
    });
    public static final RegistryObject<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SIMPLE = ITEMS.register("werewolf_minion_upgrade_simple", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 1, 2));
    public static final RegistryObject<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_ENHANCED = ITEMS.register("werewolf_minion_upgrade_enhanced", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 3, 4));
    public static final RegistryObject<WerewolfMinionUpgradeItem> WEREWOLF_MINION_UPGRADE_SPECIAL = ITEMS.register("werewolf_minion_upgrade_special", () -> new WerewolfMinionUpgradeItem(creativeTabProps(), 5, 6));

    public static final RegistryObject<SpawnEggItem> WEREWOLF_BEAST_SPAWN_EGG = ITEMS.register("werewolf_beast_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.WEREWOLF_BEAST, 0xffc800, 0xfaab00, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> WEREWOLF_SURVIVALIST_SPAWN_EGG = ITEMS.register("werewolf_survivalist_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.WEREWOLF_SURVIVALIST, 0xffc800, 0xfae700, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> HUMAN_WEREWOLF_SPAWN_EGG = ITEMS.register("human_werewolf_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.HUMAN_WEREWOLF, 0xffc800, 0xa8a8a8, new Item.Properties().tab(ItemGroup.TAB_MISC)));
    public static final RegistryObject<SpawnEggItem> ALPHA_WEREWOLF_SPAWN_EGG = ITEMS.register("alpha_werewolf_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.ALPHA_WEREWOLF, 0xffc800, 0xca0f00, new Item.Properties().tab(ItemGroup.TAB_MISC)));

    public static final RegistryObject<WerewolfRefinementItem> BONE_NECKLACE = ITEMS.register("bone_necklace", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.AMULET));
    public static final RegistryObject<WerewolfRefinementItem> CHARM_BRACELET = ITEMS.register("charm_bracelet", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.RING));
    public static final RegistryObject<WerewolfRefinementItem> DREAM_CATCHER = ITEMS.register("dream_catcher", () -> new WerewolfRefinementItem(creativeTabProps(), IRefinementItem.AccessorySlotType.OBI_BELT));

    public static class V {
        public static final RegistryObject<Item> HUMAN_HEART = RegistryObject.of(new ResourceLocation("vampirism", "human_heart"), ForgeRegistries.ITEMS);
        public static final RegistryObject<Item> INJECTION_EMPTY = RegistryObject.of(new ResourceLocation("vampirism", "injection_empty"), ForgeRegistries.ITEMS);
        public static final RegistryObject<Item> WEAK_HUMAN_HEART = RegistryObject.of(new ResourceLocation("vampirism", "weak_human_heart"), ForgeRegistries.ITEMS);
        public static final RegistryObject<Item> OBLIVION_POTION = RegistryObject.of(new ResourceLocation("vampirism", "oblivion_potion"), ForgeRegistries.ITEMS);
        public static final RegistryObject<Item> VAMPIRE_BOOK = RegistryObject.of(new ResourceLocation("vampirism", "vampire_book"), ForgeRegistries.ITEMS);
        public static final RegistryObject<Item> CROSSBOW_ARROW_NORMAL = RegistryObject.of(new ResourceLocation("vampirism", "crossbow_arrow_normal"), ForgeRegistries.ITEMS);

        private static void init(){}
    }

    static void registerItems(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static void remapItems(RegistryEvent.MissingMappings<Item> event) {
        event.getAllMappings().forEach(missingMapping -> {
            switch (missingMapping.key.toString()) {
                case "werewolves:bone":
                    missingMapping.remap(ModItems.CRACKED_BONE.get());
                    break;
                case "werewolves:oil_bottle":
                    missingMapping.remap(de.teamlapen.vampirism.core.ModItems.OIL_BOTTLE.get());
                    break;
            }
        });
    }

    private static Item.Properties creativeTabProps() {
        return new Item.Properties().tab(WUtils.creativeTab);
    }

    static {
        V.init();
    }
}
