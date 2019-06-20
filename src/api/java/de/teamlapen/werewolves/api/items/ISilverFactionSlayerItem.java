package de.teamlapen.werewolves.api.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IFactionSlayerItem;
import de.teamlapen.werewolves.api.WReference;
import net.minecraft.item.ItemStack;

public interface ISilverFactionSlayerItem extends IFactionSlayerItem {

    public default float getDamageMultiplierForFaction(ItemStack stack) {
        return 1.5F;
    }

    @Override
    public default IFaction getSlayedFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
