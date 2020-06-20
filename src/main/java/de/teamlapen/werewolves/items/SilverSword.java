package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class SilverSword extends SwordItem implements ISilverItem {
    public SilverSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
}
