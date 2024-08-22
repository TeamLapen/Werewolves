package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, REFERENCE.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SILVER = register("silver", createReduction(2,6,5,2), 9, SoundEvents.ARMOR_EQUIP_IRON, () -> Ingredient.of(ModItems.SILVER_INGOT.get()), 0.0f, 0.0f);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> PELT = register("pelt", createReduction(1,1,1,1), 9, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ModItems.PELT.get()), 0.0f, 0.0f);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DARK_PELT = register("dark_pelt", createReduction(1,2,2,1), 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ModItems.DARK_PELT.get()), 0.0f, 0.0f);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> WHITE_PELT = register("white_pelt", createReduction(2,3,2,1), 20, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ModItems.WHITE_PELT.get()), 0.0f, 0.0f);

    static void register(IEventBus bus) {
        ARMOR_MATERIALS.register(bus);
    }

    private static EnumMap<ArmorItem.Type, Integer> createReduction(int helmet, int chestplate, int leggings, int boots) {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, boots);
            map.put(ArmorItem.Type.LEGGINGS, leggings);
            map.put(ArmorItem.Type.CHESTPLATE, chestplate);
            map.put(ArmorItem.Type.HELMET, helmet);
        });
    }

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, Map<ArmorItem.Type, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient, float toughness, float knockbackResistance) {
        return ARMOR_MATERIALS.register(name, () -> {
            List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(WResourceLocation.mod(name)));
            return new ArmorMaterial(defense, enchantmentValue, equipSound, repairIngredient, list, toughness, knockbackResistance);
        });
    }
}
