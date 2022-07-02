package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.vampirism.items.VampireRefinementItem;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.util.WReference;

import javax.annotation.Nonnull;

public class WerewolfRefinementItem extends VampireRefinementItem implements IRefinementItem {

    public static WerewolfRefinementItem getRefinementItem(IRefinementItem.AccessorySlotType type){
        switch (type){
            case AMULET:
                return ModItems.bone_necklace.get();
            case RING:
                return ModItems.charm_bracelet.get();
            default:
                return ModItems.dream_catcher.get();
        }
    }

    public WerewolfRefinementItem(@Nonnull Properties properties, @Nonnull AccessorySlotType type) {
        super(properties, type);
    }

    @Nonnull
    @Override
    public IFaction<?> getExclusiveFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
