package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public class ModArmorMaterials {

    public static class Asset {
        public static final ResourceKey<EquipmentAsset> SILVER = createId("silver");
        public static final ResourceKey<EquipmentAsset> PELT_NORMAL = createId("pelt");
        public static final ResourceKey<EquipmentAsset> PELT_ENHANCED = createId("pelt_enhanced");
        public static final ResourceKey<EquipmentAsset> PELT_ULTIMATE = createId("pelt_ultimate");

        private static ResourceKey<EquipmentAsset> createId(String id) {
            return ResourceKey.create(EquipmentAssets.ROOT_ID, WIdentifier.mod(id));
        }
    }
    public static final ArmorMaterial SILVER = register(20, createReduction(2,6,5,2), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f, ModTags.Items.SILVER_INGOT, Asset.SILVER);
    public static final ArmorMaterial PELT = register(18, createReduction(1,1,1,1), 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, ModTags.Items.WOLF_PELT, Asset.PELT_NORMAL);
    public static final ArmorMaterial DARK_PELT = register(33, createReduction(1,2,2,1), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, ModTags.Items.WOLF_DARK_PELT, Asset.PELT_ENHANCED);
    public static final ArmorMaterial WHITE_PELT = register(40, createReduction(2,3,3,2), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0f, 0.0f, ModTags.Items.WOLF_WHITE_PELT,Asset.PELT_ULTIMATE);

    private static EnumMap<ArmorType, Integer> createReduction(int helmet, int chestplate, int leggings, int boots) {
        return Util.make(new EnumMap<>(ArmorType.class), (map) -> {
            map.put(ArmorType.BOOTS, boots);
            map.put(ArmorType.LEGGINGS, leggings);
            map.put(ArmorType.CHESTPLATE, chestplate);
            map.put(ArmorType.HELMET, helmet);
        });
    }

    private static ArmorMaterial register(int durability, Map<ArmorType, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, TagKey<Item> repairIngredient, ResourceKey<EquipmentAsset> equipmentAsset) {
        return new ArmorMaterial(durability, defense, enchantmentValue, equipSound, toughness, knockbackResistance, repairIngredient, equipmentAsset);
    }
}
