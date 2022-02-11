package de.teamlapen.werewolves.inventory.recipes;

import de.teamlapen.werewolves.core.ModRecipes;
import de.teamlapen.werewolves.items.OilItem;
import de.teamlapen.werewolves.items.oil.IOil;
import de.teamlapen.werewolves.util.OilUtils;
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
                if (stack.getItem() instanceof OilItem) {
                    if (oil != null) return false;
                    oil = OilUtils.getOil(stack);
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
        ItemStack oil = ItemStack.EMPTY;
        ItemStack tool = ItemStack.EMPTY;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof OilItem) {
                    oil = stack;
                } else {
                    tool = stack;
                }
            }
        }
        ItemStack result = tool.copy();
        if (oil.isEmpty() || tool.isEmpty()) return result;
        OilUtils.getOilOpt(oil).ifPresent(weaponOil -> {
            WeaponOilHelper.setWeaponOils(result, weaponOil, weaponOil.getMaxDuration(result));
        });
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
