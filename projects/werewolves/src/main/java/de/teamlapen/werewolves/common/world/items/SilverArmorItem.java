package de.teamlapen.werewolves.common.world.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.common.core.ModArmorMaterials;
import de.teamlapen.werewolves.common.world.effects.SilverEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class SilverArmorItem extends Item implements ISilverItem {

    public SilverArmorItem(ArmorType type, Item.Properties properties) {
        super(properties.humanoidArmor(ModArmorMaterials.SILVER, type));
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        super.inventoryTick(itemStack, level, owner, slot);
        if (owner instanceof LivingEntity livingEntity && slot != null && slot.isArmor()) {
            if (Helper.isWerewolf(livingEntity)) {
                livingEntity.addEffect(SilverEffect.createSilverEffect(livingEntity, 20, 1, true));
            }
        }
    }
}