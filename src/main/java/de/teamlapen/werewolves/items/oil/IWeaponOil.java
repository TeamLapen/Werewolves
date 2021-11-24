package de.teamlapen.werewolves.items.oil;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IWeaponOil extends IForgeRegistryEntry<IWeaponOil> {

    /**
     * whether the entity should be effected by the oil
     */
    boolean canEffect(ItemStack stack, LivingEntity entity);

    /**
     * calculates the bonus damage for the entity
     */
    float getAdditionalDamage(ItemStack stack, LivingEntity entity, float damage);

    int getMaxDuration(ItemStack stack);

    int getDuration(ItemStack stack);
}
