package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.recipes.WeaponOilRecipe;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class ModRecipes {
    @ObjectHolder(REFERENCE.MODID + ":weapon_oil")
    public static final SimpleRecipeSerializer<WeaponOilRecipe> weapon_oil = getNull();

    public static void register(IForgeRegistry<RecipeSerializer<?>> event) {
        event.register(new SimpleRecipeSerializer<>(WeaponOilRecipe::new).setRegistryName(REFERENCE.MODID, "weapon_oil"));
    }
}
