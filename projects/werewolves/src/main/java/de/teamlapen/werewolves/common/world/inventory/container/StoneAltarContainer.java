package de.teamlapen.werewolves.common.world.inventory.container;

import de.teamlapen.faction.common.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.common.core.ModContainer;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.common.core.ModItems;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfLevelConf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class StoneAltarContainer extends AbstractContainerMenu {

    public static final List<Predicate<ItemStack>> SLOT_PREDICATES = List.of(
            stack -> stack.is(ModItems.LIVER.get()),
            stack -> stack.is(ModItems.CRACKED_BONE.get())
    );

    private final Container altarInventory;
    private final Player player;

    public StoneAltarContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(2));
    }

    public StoneAltarContainer(int id, Inventory playerInventory, Container altarInventory) {
        super(ModContainer.STONE_ALTAR.get(), id);
        checkContainerSize(altarInventory, 2);
        this.altarInventory = altarInventory;
        this.player = playerInventory.player;

        this.addSlot(new ValidatorSlot(altarInventory, 0, 62, 34, SLOT_PREDICATES.get(0)));
        this.addSlot(new ValidatorSlot(altarInventory, 1, 98, 34, SLOT_PREDICATES.get(1)));

        this.addStandardInventorySlots(playerInventory, 8, 84);
    }

    public Optional<WerewolfLevelConf.StoneAltarRequirement> getRequirements() {
        return Optional.ofNullable(WerewolfLevelConf.getInstance().getStoneRequirement(FactionPlayerHandler.get(this.player).getCurrentLevel(ModFactions.WEREWOLF) + 1));
    }

    @Override
    public boolean stillValid(Player player) {
        return this.altarInventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            originalStack = stack.copy();

            if (index < 2) {
                if (!this.moveItemStackTo(stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(stack, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return originalStack;
    }

    public static class ValidatorSlot extends Slot {

        private final Predicate<ItemStack> validator;

        public ValidatorSlot(Container container, int slot, int x, int y, Predicate<ItemStack> validator) {
            super(container, slot, x, y);
            this.validator = validator;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return this.validator.test(stack) && super.mayPlace(stack);
        }
    }
}
