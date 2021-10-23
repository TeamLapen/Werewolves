package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.core.ModWeaponOils;
import de.teamlapen.werewolves.items.oil.WeaponOil;

public class SilverOilItem extends OilItem {

    public SilverOilItem(Properties properties) {
        super(properties);
    }

    @Override
    public WeaponOil getWeaponOil() {
        return ModWeaponOils.silver_oil;
    }
}
