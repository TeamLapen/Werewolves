package de.teamlapen.werewolves.items.oil;

import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class SilverWeaponOil extends WeaponOil {

    public SilverWeaponOil() {
        super(10);
    }

    public boolean canEffect(ItemStack stack, LivingEntity entity) {
        return Helper.isWerewolf(entity);
    }

    public float getAdditionalDamage(ItemStack stack, LivingEntity entity, float damage){
        return damage * 0.2f;
    }
}
