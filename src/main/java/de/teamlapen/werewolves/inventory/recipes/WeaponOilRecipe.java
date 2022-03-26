package de.teamlapen.werewolves.inventory.recipes;

import de.teamlapen.werewolves.api.items.IOilItem;
import de.teamlapen.werewolves.api.items.oil.IOil;
import de.teamlapen.werewolves.core.ModRecipes;
import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class WeaponOilRecipe extends CustomRecipe {

    public WeaponOilRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer inventory, @Nonnull Level world) {
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
    public ItemStack assemble(@Nonnull CraftingContainer inventory) {
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
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.weapon_oil;
    }
}
