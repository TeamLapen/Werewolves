package de.teamlapen.werewolves.common.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.stream.Stream;

public class ArmorUtil {

    public static Stream<ItemStack> armorItems(LivingEntity entity) {
        return Arrays.stream(EquipmentSlot.values()).filter(EquipmentSlot::isArmor).map(entity::getItemBySlot);
    }
}
