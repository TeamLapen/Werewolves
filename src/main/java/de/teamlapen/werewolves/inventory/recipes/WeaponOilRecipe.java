package de.teamlapen.werewolves.inventory.recipes;

import de.teamlapen.werewolves.core.ModRecipes;
import de.teamlapen.werewolves.items.IOilItem;
import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class WeaponOilRecipe extends SpecialRecipe {

    public WeaponOilRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, @Nonnull World world) {
        IOil oil = null;
        ItemStack tool = null;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IOilItem) {
                    if (oil != null) return false;
                    oil = ((IOilItem) stack.getItem()).getOil(stack);
                } else {
                    if (tool != null) return false;
                    tool = stack;
                }
            }
        }
        return oil != null && tool != null && oil.canBeAppliedTo(tool);
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingInventory inventory) {
        ItemStack oilStack = ItemStack.EMPTY;
        ItemStack toolStack = ItemStack.EMPTY;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof IOilItem) {
                    oilStack = stack;
                } else {
                    toolStack = stack;
                }
            }
        }
        ItemStack result = toolStack.copy();
        if (oilStack.isEmpty() || toolStack.isEmpty()) return result;
        IOil oil = ((IOilItem) oilStack.getItem()).getOil(oilStack);
        WeaponOilHelper.setWeaponOils(result, oil, oil.getMaxDuration(result));
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x*y >= 2;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.weapon_oil;
    }
}
