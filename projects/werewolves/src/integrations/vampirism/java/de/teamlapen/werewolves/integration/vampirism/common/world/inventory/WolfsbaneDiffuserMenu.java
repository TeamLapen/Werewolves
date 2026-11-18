package de.teamlapen.werewolves.integration.vampirism.common.world.inventory;

import de.teamlapen.library.common.world.blockentity.PlayerOwnedBlockEntity;
import de.teamlapen.vampirism.common.world.inventory.DiffuserMenu;
import de.teamlapen.werewolves.common.core.ModDataMaps;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatMenus;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;

public class WolfsbaneDiffuserMenu extends DiffuserMenu {

    public WolfsbaneDiffuserMenu(int pContainerId, Inventory playerInventory, PlayerOwnedBlockEntity.LockDataHolder lockData) {
        super(CompatMenus.WOLFSBANE_DIFFUSER.get(), pContainerId, playerInventory, lockData);
    }

    public WolfsbaneDiffuserMenu(int pContainerId, Inventory playerInventory, Container pContainer, ContainerData pData, PlayerOwnedBlockEntity.LockDataHolder lockData) {
        super(CompatMenus.WOLFSBANE_DIFFUSER.get(), pContainerId, playerInventory, pContainer, pData, lockData);
    }

    @Override
    public boolean isFuel(ItemStack pStack) {
        return pStack.typeHolder().getData(ModDataMaps.WOLFSBANE_DIFFUSER_FUEL_MAP) != null;
    }

    public static class Factory extends DiffuserMenu.Factory<WolfsbaneDiffuserMenu> {

        @Override
        public WolfsbaneDiffuserMenu create(int windowId, Inventory inv, PlayerOwnedBlockEntity.LockDataHolder lockData) {
            return new WolfsbaneDiffuserMenu(windowId, inv, lockData);
        }
    }
}
