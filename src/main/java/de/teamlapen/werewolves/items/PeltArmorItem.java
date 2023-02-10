package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.IWerewolfArmor;
import de.teamlapen.werewolves.util.ArmorMaterial;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class PeltArmorItem extends ArmorItem implements IWerewolfArmor {

    public static final ArmorMaterial PELT = new ArmorMaterial("werewolves:pelt", 15, new int[]{0, 0, 0, 0}, 9, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> null);

    public PeltArmorItem(EquipmentSlot pSlot, Properties pProperties) {
        super(PELT, pSlot, pProperties);
    }
}
