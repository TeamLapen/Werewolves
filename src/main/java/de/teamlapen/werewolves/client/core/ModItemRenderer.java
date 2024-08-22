package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.items.CrossbowArrowItem;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.jetbrains.annotations.ApiStatus;

public class ModItemRenderer {

    @ApiStatus.Internal
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                return ((CrossbowArrowItem) stack.getItem()).getType().color;
            }
            return -1;
        }, ModItems.CROSSBOW_ARROW_SILVER_BOLT.get());
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                if (stack.getItem() instanceof IRefinementItem refinementItem) {
                    IRefinementSet set = refinementItem.getRefinementSet(stack);
                    if (set != null) {
                        return set.getColor() | 0xFF000000;
                    }
                }
            }
            return -1;
        }, ModItems.DREAM_CATCHER.get(), ModItems.CHARM_BRACELET.get(), ModItems.BONE_NECKLACE.get());
    }
}
