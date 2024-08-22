package de.teamlapen.werewolves.api;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.Supplier;

public class WEnums {
    public static final EnumProxy<MobCategory> WEREWOLF_CATEGORY = new EnumProxy<>(MobCategory.class, WReference.MODID + ":werewolf", 8, false, false, 128);
    public static final EnumProxy<Boat.Type> JACARANDA_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, ModRegistryItems.JACARANDA_PLANKS, WReference.MODID + ":jacaranda", ModRegistryItems.JACARANDA_BOAT, ModRegistryItems.JACARANDA_CHEST_BOAT, (Supplier<Item>)() -> Items.STICK, false);
    public static final EnumProxy<Boat.Type> MAGIC_BOAT_TYPE = new EnumProxy<>(Boat.Type.class, ModRegistryItems.MAGIC_PLANKS, WReference.MODID + ":magic", ModRegistryItems.MAGIC_BOAT, ModRegistryItems.MAGIC_CHEST_BOAT, (Supplier<Item>)() -> Items.STICK, false);

}
