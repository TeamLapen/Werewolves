package de.teamlapen.werewolves.client.core;

import de.teamlapen.vampirism.api.entity.player.refinement.IRefinementSet;
import de.teamlapen.vampirism.api.items.IRefinementItem;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.items.CrossbowArrowItem;
import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.util.OilUtils;
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
        }, ModItems.crossbow_arrow_silver_bolt);
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
        }, ModItems.dream_catcher, ModItems.charm_bracelet, ModItems.bone_necklace);
        colors.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                IOil oil = OilUtils.getOil(stack);
                return oil.getColor();
            }
            return 0xFFFFFF;
        }, ModItems.oil_bottle);
    }
}
