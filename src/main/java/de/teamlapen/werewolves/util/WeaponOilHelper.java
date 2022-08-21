package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.api.items.oil.IOil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.Optional;

public class WeaponOilHelper {

    public static IOil getOil(ItemStack stack) {
        if (!stack.hasTag()) return null;
        CompoundTag tag = stack.getTag();
        if (!tag.contains("weapon_oil")) return null;
        CompoundTag oilTag = stack.getTag().getCompound("weapon_oil");
        ResourceLocation loc = new ResourceLocation(oilTag.getString("oil"));
        return RegUtil.getOil(loc);
    }

    public static Optional<IOil> getOilOpt(ItemStack stack) {
        return Optional.ofNullable(getOil(stack));
    }

    public static int getDuration(ItemStack stack) {
        if (!stack.hasTag()) return 0;
        CompoundTag tag = stack.getTag();
        if (!tag.contains("weapon_oil")) return 0;
        CompoundTag oilTag = stack.getTag().getCompound("weapon_oil");
        return oilTag.getInt("duration");
    }

    public static Optional<Pair<IOil, Integer>> oilOpt(ItemStack stack) {
        return Optional.ofNullable(stack.getTag()).filter(tag -> tag.contains("weapon_oil")).map(tag -> tag.getCompound("weapon_oil")).map(tag -> Pair.of(RegUtil.getOil(new ResourceLocation(tag.getString("oil"))), tag.getInt("duration")));
    }

    public static ItemStack setWeaponOils(ItemStack stack, IOil oil, int duration) {
        CompoundTag oilTag = new CompoundTag();
        oilTag.putString("oil", RegUtil.id(oil).toString());
        oilTag.putInt("duration", duration);
        stack.getOrCreateTag().put("weapon_oil", oilTag);
        return stack;
    }

    public static boolean hasOils(ItemStack stack) {
        if (!stack.hasTag()) return false;
        return stack.getTag().contains("weapon_oil");
    }

    public static void removeOil(ItemStack stack) {
        stack.getOrCreateTag().remove("weapon_oil");
    }

    public static void executeAndReduce(ItemStack stack, TriConsumer<ItemStack, IOil, Integer> consumer) {
        oilOpt(stack).ifPresent(oil -> {
            consumer.accept(stack, oil.getLeft(), oil.getRight());
            if (oil.getRight() > 1) {
                setWeaponOils(stack, oil.getLeft(), oil.getRight() - 1);
            } else {
                stack.getTag().remove("weapon_oil");
            }
        });
    }
}
