package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.WeaponOil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class WeaponOilHelper {

    public static Set<WeaponOil> getWeaponOils(ItemStack stack) {
        return getList(stack).stream().map(nbt -> ModRegistries.WEAPON_OILS.getValue(new ResourceLocation(nbt.getAsString()))).collect(Collectors.toSet());
    }

    public static void forEach(ItemStack stack, Consumer<WeaponOil> oil) {
        getList(stack).stream().map(nbt -> ModRegistries.WEAPON_OILS.getValue(new ResourceLocation(nbt.getAsString()))).distinct().forEach(oil);
    }

    public static void setWeaponOils(ItemStack stack, Collection<WeaponOil> oils) {
        ListNBT list = new ListNBT();
        for (WeaponOil oil : oils) {
            list.add(StringNBT.valueOf(oil.getRegistryName().toString()));
        }
        saveList(stack, list);
    }

    private static void saveList(ItemStack stack, ListNBT list) {
        stack.getOrCreateTag().put("weapon_oils", list);
    }

    private static ListNBT getList(ItemStack stack) {
        return stack.getOrCreateTag().getList("weapon_oils", 8);
    }
}
