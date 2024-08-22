package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.core.ModArmorMaterials;
import de.teamlapen.werewolves.effects.SilverEffect;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SilverArmorItem extends ArmorItem implements ISilverItem {

    public SilverArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(ModArmorMaterials.SILVER, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pSlotId >= 36 && pSlotId <= 39 && pEntity instanceof LivingEntity living && pEntity.tickCount % 16 == 8) {
            if (Helper.isWerewolf(living)) {
                living.addEffect(SilverEffect.createSilverEffect(living, 20, 1, true));
            }
        }
    }
}