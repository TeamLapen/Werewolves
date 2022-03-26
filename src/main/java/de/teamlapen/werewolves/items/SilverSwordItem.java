package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SilverSwordItem extends SwordItem implements ISilverItem {
    public SilverSwordItem(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
}
