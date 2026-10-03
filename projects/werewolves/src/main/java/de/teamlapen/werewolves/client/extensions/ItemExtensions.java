package de.teamlapen.werewolves.client.extensions;

import de.teamlapen.werewolves.client.model.armor.WolfHeadModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ItemExtensions {

    public static final IClientItemExtensions WOLF_PELT = new IClientItemExtensions() {
        @Override
        public @NotNull Model getGenericArmorModel(@NotNull LivingEntity livingEntity, @NotNull ItemStack itemStack, @NotNull EquipmentSlot equipmentSlot, @NotNull HumanoidModel<?> original) {
            return equipmentSlot == EquipmentSlot.HEAD ? WolfHeadModel.getInstance(original) : IClientItemExtensions.super.getGenericArmorModel(livingEntity, itemStack, equipmentSlot, original);
        }
    };
}
