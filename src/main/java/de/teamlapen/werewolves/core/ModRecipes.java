package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.recipes.WeaponOilRecipe;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

public class ModRecipes {
    @ObjectHolder(REFERENCE.MODID + ":weapon_oil")
    public static final SpecialRecipeSerializer<WeaponOilRecipe> weapon_oil = getNull();

    public static void register(IForgeRegistry<IRecipeSerializer<?>> event) {
        event.register(new SpecialRecipeSerializer<>(WeaponOilRecipe::new).setRegistryName(REFERENCE.MODID,"weapon_oil"));
    }
}
