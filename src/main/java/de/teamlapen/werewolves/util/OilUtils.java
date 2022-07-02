package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class OilUtils {

    @Nonnull
    public static IOil getOil(@Nonnull ItemStack stack) {
        CompoundTag tag = stack.getTag();
        IOil oil = null;
        if (tag != null && tag.contains("oil")) {
            String oilStr = stack.getTag().getString("oil");
            oil = ModRegistries.WEAPON_OILS.getValue(new ResourceLocation(oilStr));
        }
        return oil != null ? oil : ModOils.empty.get();
    }

    public static ItemStack setOil(@Nonnull ItemStack stack, @Nonnull IOil oil) {
        stack.getOrCreateTag().putString("oil", oil.getRegistryName().toString());
        return stack;
    }

    public static void addOilTooltip(ItemStack stack, List<Component> tooltips) {
        getOil(stack).getDescription(stack, tooltips);
    }
}
