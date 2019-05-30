package de.teamlapen.werewolves.client.core;

import de.teamlapen.lib.lib.util.InventoryRenderHelper;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.items.ItemCrossbowArrow;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModItemsRender {
    public static void register() {
        registerRenderers();
    }

    private static void registerRenderers() {
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(REFERENCE.MODID);
        renderHelper.registerRender(ModItems.silver_axe, "normal");
        renderHelper.registerRender(ModItems.silver_pickaxe, "normal");
        renderHelper.registerRender(ModItems.silver_sword, "normal");
        renderHelper.registerRender(ModItems.silver_shovel, "normal");
        renderHelper.registerRender(ModItems.silver_hoe, "normal");
        renderHelper.registerRender(ModItems.silver_ingot, "normal");
        renderHelper.registerRender(ModItems.crossbow_arrow, "silver");
        renderHelper.registerRender(ModItems.wolfs_pelt, "normal");
    }

    static void registerColors() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex == 1)
                return ItemCrossbowArrow.getType(stack).color;
            return 0xFFFFFF;
        }, ModItems.crossbow_arrow);
    }
}
