package de.teamlapen.werewolves.inventory.recipes;

import de.teamlapen.werewolves.core.ModRecipes;
import de.teamlapen.werewolves.items.OilItem;
import de.teamlapen.werewolves.items.oil.SilverWeaponOil;
import de.teamlapen.werewolves.util.WeaponOilHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
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
        boolean oil = false;
        boolean tool = false;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof SwordItem) {
                    if (tool) return false;
                    tool = true;
                } else if (stack.getItem() instanceof OilItem) {
                    if (oil) return false;
                    oil = true;
                }
            }
        }
        return oil && tool;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingInventory inventory) {
        ItemStack oil = ItemStack.EMPTY;
        ItemStack tool = ItemStack.EMPTY;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof SwordItem) {
                    tool = stack;
                } else if (stack.getItem() instanceof OilItem) {
                    oil = stack;
                }
            }
        }
        ItemStack result = tool.copy();
        if (oil.isEmpty() || tool.isEmpty()) return result;
        SilverWeaponOil oilItem = ((OilItem) oil.getItem()).getWeaponOil();
        WeaponOilHelper.setWeaponOils(result, oilItem, oilItem.getMaxDuration(result));
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
