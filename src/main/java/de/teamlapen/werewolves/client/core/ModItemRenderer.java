package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.items.CrossbowArrowItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModItemRenderer {

    public static void registerColorsUnsafe() {
        ItemColors colors = Minecraft.getInstance().getItemColors();
        colors.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                return ((CrossbowArrowItem) stack.getItem()).getType().color;
            }
            return 0xFFFFFF;
        }, ModItems.CROSSBOW_ARROW_SILVER_BOLT.get());
        colors.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                if (stack.getItem() instanceof IRefinementItem) {
                    IRefinementSet set = ((IRefinementItem) stack.getItem()).getRefinementSet(stack);
                    if (set != null) {
                        return set.getColor();
                    }
                }
            }
            return 0xFFFFFF;
        }, ModItems.DREAM_CATCHER.get(), ModItems.CHARM_BRACELET.get(), ModItems.BONE_NECKLACE.get());
    }
}
