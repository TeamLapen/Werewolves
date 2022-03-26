package de.teamlapen.werewolves.api.items.oil;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public interface IOil extends IForgeRegistryEntry<IOil> {

    /**
     * whether the entity should be effected by the oil
     */
    boolean canEffect(ItemStack stack, LivingEntity entity);

    /**
     * calculates the bonus damage for the entity
     */
    float getAdditionalDamage(ItemStack stack, LivingEntity entity, float damage);

    String getName(String item);

    int getMaxDuration(ItemStack stack);

    void getDescription(ItemStack stack, List<Component> tooltips);

    boolean canBeAppliedTo(ItemStack stack);

    int getColor();
}
