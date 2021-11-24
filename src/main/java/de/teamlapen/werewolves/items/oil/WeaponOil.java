package de.teamlapen.werewolves.items.oil;

import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class WeaponOil extends ForgeRegistryEntry<IWeaponOil> implements IWeaponOil {

    private final int maxDuration;

    public WeaponOil(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        return this.maxDuration;
    }

    @Override
    public int getDuration(ItemStack stack) {
        return WeaponOilHelper.getDuration(stack);
    }
}
