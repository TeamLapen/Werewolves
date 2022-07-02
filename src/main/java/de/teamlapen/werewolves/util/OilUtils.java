package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModOils;
import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.IOil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.List;

public class OilUtils {

    @Nonnull
    public static IOil getOil(@Nonnull ItemStack stack) {
        CompoundNBT tag = stack.getTag();
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

    public static void addOilTooltip(ItemStack stack, List<ITextComponent> tooltips) {
        getOil(stack).getDescription(stack, tooltips);
    }
}
