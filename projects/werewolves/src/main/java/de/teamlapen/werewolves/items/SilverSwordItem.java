package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class SilverSwordItem extends SwordItem implements ISilverItem {
    public SilverSwordItem(Tier tier, Properties builder) {
        super(tier, builder);
    }
}
