package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.IWeaponOil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;
import java.util.function.BiConsumer;

public class WeaponOilHelper {

    public static IWeaponOil getOil(ItemStack stack) {
        if (!stack.hasTag()) return null;
        CompoundNBT tag = stack.getTag();
        if (!tag.contains("weapon_oil")) return null;
        CompoundNBT oilTag = stack.getTag().getCompound("weapon_oil");
        ResourceLocation loc = new ResourceLocation(oilTag.getString("oil"));
        return ModRegistries.WEAPON_OILS.getValue(loc);
    }

    public static int getDuration(ItemStack stack) {
        if (!stack.hasTag()) return 0;
        CompoundNBT tag = stack.getTag();
        if (!tag.contains("weapon_oil")) return 0;
        CompoundNBT oilTag = stack.getTag().getCompound("weapon_oil");
        return oilTag.getInt("duration");
    }

    public static Optional<Pair<IWeaponOil, Integer>> oilOpt(ItemStack stack) {
        return Optional.ofNullable(stack.getTag()).filter(tag -> tag.contains("weapon_oil")).map(tag -> tag.getCompound("weapon_oil")).map(tag -> Pair.of(ModRegistries.WEAPON_OILS.getValue(new ResourceLocation(tag.getString("oil"))), tag.getInt("duration")));
    }

    public static void setWeaponOils(ItemStack stack, IWeaponOil oil, int duration) {
        CompoundNBT oilTag = new CompoundNBT();
        oilTag.putString("oil", oil.getRegistryName().toString());
        oilTag.putInt("duration", duration);
        stack.getOrCreateTag().put("weapon_oil", oilTag);
    }

    public static boolean hasOils(ItemStack stack) {
        if (!stack.hasTag()) return false;
        return stack.getTag().contains("weapon_oil");
    }

    public static void removeOil(ItemStack stack) {
        stack.getOrCreateTag().remove("weapon_oil");
    }

    public static void executeAndReduce(ItemStack stack, BiConsumer<IWeaponOil, Integer> consumer) {
        oilOpt(stack).ifPresent(oil -> {
            consumer.accept(oil.getLeft(), oil.getRight());
            if (oil.getRight() > 1) {
                setWeaponOils(stack, oil.getLeft(), oil.getRight() - 1);
            } else {
                stack.getTag().remove("weapon_oil");
            }
        });
    }
}
