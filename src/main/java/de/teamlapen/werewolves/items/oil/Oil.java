package de.teamlapen.werewolves.items.oil;

import de.teamlapen.werewolves.api.items.oil.IOil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.List;

public class Oil extends ForgeRegistryEntry<IOil> implements IOil {

    private final int color;
    private Component desc;

    public Oil(int color) {
        this.color = color;
    }

    @Override
    public boolean canEffect(ItemStack stack, LivingEntity entity) {
        return false;
    }

    @Override
    public float getAdditionalDamage(ItemStack stack, LivingEntity entity, float damage) {
        return 0;
    }

    @Override
    public boolean canBeAppliedTo(ItemStack stack) {
        return false;
    }

    @Override
    public int getMaxDuration(ItemStack stack) {
        return 0;
    }

    @Override
    public String getName(String item) {
        return item + this.getRegistryName().toString().replace(':', '_');
    }

    @Override
    public void getDescription(ItemStack stack, List<Component> tooltips) {
    }

    @Override
    public int getColor() {
        return this.color;
    }
}
