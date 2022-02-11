package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.IOil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public class OilUtils {

    public static IOil getOil(@Nonnull ItemStack stack) {
        CompoundNBT tag = stack.getTag();
        if (tag == null || !tag.contains("oil")) return null;
        String oil = stack.getTag().getString("oil");
        return ModRegistries.WEAPON_OILS.getValue(new ResourceLocation(oil));
    }

    public static Optional<IOil> getOilOpt(@Nonnull ItemStack stack) {
        return Optional.ofNullable(getOil(stack));
    }

    public static ItemStack setOil(@Nonnull ItemStack stack, @Nonnull IOil oil) {
        stack.getOrCreateTag().putString("oil", oil.getRegistryName().toString());
        return stack;
    }

    public static void addOilTooltip(ItemStack stack, List<ITextComponent> tooltips) {
        getOilOpt(stack).ifPresent(oil -> {
            oil.getDescription(stack, tooltips);
        });
    }
}
