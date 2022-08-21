package de.teamlapen.werewolves.inventory.container;

import de.teamlapen.lib.lib.inventory.InventoryContainer;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModContainer;
import de.teamlapen.werewolves.core.ModItems;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class StoneAltarContainer extends InventoryContainer {
    public static final SelectorInfo[] SELECTOR_INFOS;


    @Deprecated
    public StoneAltarContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(2), ContainerLevelAccess.NULL);
    }

    public StoneAltarContainer(int id, Inventory playerInventory, Container inventory, ContainerLevelAccess worldPos) {
        super(ModContainer.STONE_ALTAR.get(), id, playerInventory, worldPos, inventory, SELECTOR_INFOS);
        this.addPlayerSlots(playerInventory);
    }

    @Override
    public boolean stillValid(@Nonnull Player playerIn) {
        return stillValid(this.worldPos, playerIn, ModBlocks.STONE_ALTAR.get());
    }

    static {
        SELECTOR_INFOS = new SelectorInfo[]{
                new SelectorInfo(Ingredient.of(ModItems.LIVER.get()), 62, 34),
                new SelectorInfo(Ingredient.of(ModItems.CRACKED_BONE.get()), 98, 34)
        };
    }
}
