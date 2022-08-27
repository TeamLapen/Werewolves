package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.api.items.ISilverItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

public class SilverSwordItem extends SwordItem implements ISilverItem {
    public SilverSwordItem(@NotNull Tier tier, int attackDamageIn, float attackSpeedIn, @NotNull Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }
}
