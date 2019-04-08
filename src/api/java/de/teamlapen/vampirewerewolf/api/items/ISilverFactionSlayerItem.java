package de.teamlapen.vampirewerewolf.api.items;

import de.teamlapen.vampirewerewolf.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IFactionSlayerItem;
import net.minecraft.item.ItemStack;

public interface ISilverFactionSlayerItem extends IFactionSlayerItem {

    public default float getDamageMultiplierForFaction(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public default IFaction getSlayedFaction() {
        return VReference.WEREWOLF_FACTION;
    }
}
