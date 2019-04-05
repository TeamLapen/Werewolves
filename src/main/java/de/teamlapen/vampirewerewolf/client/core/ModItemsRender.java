package de.teamlapen.vampirewerewolf.client.core;

import de.teamlapen.lib.lib.util.InventoryRenderHelper;
import de.teamlapen.vampirewerewolf.core.ModItems;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModItemsRender {
    public static void register() {
        registerRenderers();
    }

    private static void registerRenderers() {
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(REFERENCE.MODID);
        renderHelper.registerRender(ModItems.item_silver_axe, "normal");
        renderHelper.registerRender(ModItems.item_silver_pickaxe, "normal");
        renderHelper.registerRender(ModItems.item_silver_sword, "normal");
        renderHelper.registerRender(ModItems.item_silver_shovel, "normal");
        renderHelper.registerRender(ModItems.item_silver_hoe, "normal");
    }
}
