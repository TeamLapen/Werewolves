package de.teamlapen.werewolves.items;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.vampirism.items.VampireRefinementItem;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class WerewolfRefinementItem extends VampireRefinementItem implements IRefinementItem {

    public static WerewolfRefinementItem getRefinementItem(IRefinementItem.AccessorySlotType type) {
        return switch (type) {
            case AMULET -> ModItems.BONE_NECKLACE.get();
            case RING -> ModItems.CHARM_BRACELET.get();
            default -> ModItems.DREAM_CATCHER.get();
        };
    }

    public WerewolfRefinementItem(@Nonnull Properties properties, @Nonnull AccessorySlotType type) {
        super(properties, type);
    }

    @Nonnull
    @Override
    public IFaction<?> getExclusiveFaction(@Nonnull ItemStack stack) {
        return WReference.WEREWOLF_FACTION;
    }
}
