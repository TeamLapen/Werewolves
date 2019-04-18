package de.teamlapen.werewolves.client.core;

import de.teamlapen.lib.lib.util.InventoryRenderHelper;
import de.teamlapen.werewolves.blocks.WerewolfFlower;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.item.Item;
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
        renderHelper.registerRenderAllMeta(Item.getItemFromBlock(ModBlocks.werewolf_flower), WerewolfFlower.EnumFlowerType.values());
    }
}
