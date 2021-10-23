package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.items.oil.WeaponOil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class WeaponOilHelper {

    public static Set<WeaponOil> getWeaponOils(ItemStack stack) {
        return getList(stack).keySet();
    }

    public static Map<WeaponOil, Integer> getWeaponOilsWDuration(ItemStack stack) {
        return getList(stack);
    }

    public static void forEach(ItemStack stack, BiConsumer<WeaponOil, Integer> oilConsumer) {
        getList(stack).forEach(oilConsumer);
    }

    public static void forEachReduced(ItemStack stack, BiConsumer<WeaponOil, Integer> oilConsumer) {
        Map<WeaponOil, Integer> maps = getList(stack);
        maps.forEach(oilConsumer);
        Map<WeaponOil, Integer> newMap = new HashMap<>();
        for (Map.Entry<WeaponOil, Integer> entry : maps.entrySet()) {
            if (entry.getValue() > 1) {
                newMap.put(entry.getKey(), entry.getValue()-1);
            }
        }
        setWeaponOils(stack, newMap);
    }

    public static void setWeaponOils(ItemStack stack, Map<WeaponOil, Integer> oils) {
        ListNBT list = new ListNBT();
        oils.forEach((oil, duration) -> {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("id", oil.getRegistryName().toString());
            nbt.putInt("duration", duration);
            list.add(nbt);
        });
        saveList(stack, list);
    }

    private static void saveList(ItemStack stack, ListNBT list) {
        stack.getOrCreateTag().put("weapon_oils", list);
    }

    private static Map<WeaponOil, Integer> getList(ItemStack stack) {
        ListNBT list = stack.getOrCreateTag().getList("weapon_oils", 10);
        Map<WeaponOil, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            CompoundNBT nbt = list.getCompound(i);
            WeaponOil oil = ModRegistries.WEAPON_OILS.getValue(new ResourceLocation(nbt.getString("id")));
            int duration = nbt.getInt("duration");
            if (oil == null) continue;
            map.put(oil, duration);
        }
        return map;
    }
}
