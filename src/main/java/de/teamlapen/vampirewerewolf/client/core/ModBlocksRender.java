package de.teamlapen.vampirewerewolf.client.core;

import de.teamlapen.lib.lib.util.InventoryRenderHelper;
import de.teamlapen.vampirewerewolf.core.ModBlocks;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModBlocksRender {

    public static void register() {
        registerRenderer();
    }

    private static void registerRenderer() {
        InventoryRenderHelper renderHelper = new InventoryRenderHelper(REFERENCE.MODID);
        renderHelper.registerRender(ModBlocks.silver_ore);
    }
}
