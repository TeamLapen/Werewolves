package de.teamlapen.werewolves.api.items;

import de.teamlapen.werewolves.api.items.oil.IOil;
import net.minecraft.world.item.ItemStack;

public interface IOilItem {

    IOil getOil(ItemStack stack);
}
