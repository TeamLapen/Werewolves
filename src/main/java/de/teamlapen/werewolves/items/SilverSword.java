package de.teamlapen.werewolves.items;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.SwordItem;

import net.minecraft.world.item.Item.Properties;

public class SilverSword extends SwordItem implements ISilverItem {
    public SilverSword(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
}
