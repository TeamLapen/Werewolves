package de.teamlapen.werewolves.inventory.container;

import de.teamlapen.lib.lib.inventory.InventoryContainer;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModContainer;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IWorldPosCallable;

public class StoneAltarContainer extends InventoryContainer {
    public static final SelectorInfo[] SELECTOR_INFOS;


    @Deprecated
    public StoneAltarContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(2), IWorldPosCallable.DUMMY);
    }

    public StoneAltarContainer(int id, PlayerInventory playerInventory, IInventory inventory, IWorldPosCallable worldPos) {
        super(ModContainer.stone_altar_container, id, playerInventory, worldPos, inventory, SELECTOR_INFOS);
        this.addPlayerSlots(playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPos, playerIn, ModBlocks.stone_altar);
    }

    static {
        SELECTOR_INFOS = new SelectorInfo[]{
                new SelectorInfo(Ingredient.fromItems(ModItems.liver), 44, 34),
                new SelectorInfo(Ingredient.fromItems(ModItems.bones), 80, 34)
        };
    }
}
