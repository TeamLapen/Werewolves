package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.items.oil.WeaponOil;
import net.minecraft.item.Item;

public abstract class OilItem extends Item {

    public OilItem(Properties properties) {
        super(properties);
    }

    public abstract WeaponOil getWeaponOil();
}
