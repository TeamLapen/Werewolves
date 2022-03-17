package de.teamlapen.werewolves.items;

import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.util.OilUtils;
import net.minecraft.world.item.ItemStack;

public interface IOilItem {

    default IOil getOil(ItemStack stack) {
        return OilUtils.getOil(stack);
    }
}
