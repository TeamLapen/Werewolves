package de.teamlapen.werewolves.config;

import de.teamlapen.lib.lib.util.UtilLib;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;

public class ServerConfig {

    public final ForgeConfigSpec.ConfigValue<List<? extends String>> customMeatItems;
    public final ForgeConfigSpec.ConfigValue<List<? extends String>> customRawMeatItems;

    ServerConfig(ForgeConfigSpec.Builder builder) {
        this.customMeatItems = builder.comment("Add edible items that should be considered as meat.", "Format: [\"minecraft:cooked_beef\", \"minecraft:cooked_mutton\"]").defineList("customMeatItems", Collections.emptyList(), string -> string instanceof String && UtilLib.isValidResourceLocation(((String) string)));
        this.customRawMeatItems = builder.comment("Add edible items that should be considered as raw meat. Items should also be included in 'customMeatItems'", "Format: [\"minecraft:beef\", \"minecraft:rabbit\"]").defineList("customRawMeatItems", Collections.emptyList(), string -> string instanceof String && UtilLib.isValidResourceLocation(((String) string)));
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isCustomMeatItems(Item item) {
        return this.customMeatItems.get().contains(item.getRegistryName().toString());
    }

    @SuppressWarnings("ConstantConditions")
    public boolean isCustomRawMeatItems(Item item) {
        return this.customRawMeatItems.get().contains(item.getRegistryName().toString());
    }
}
