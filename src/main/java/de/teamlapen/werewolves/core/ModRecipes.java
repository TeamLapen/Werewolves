package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.inventory.recipes.WeaponOilRecipe;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, REFERENCE.MODID);

    public static final RegistryObject<SpecialRecipeSerializer<WeaponOilRecipe>> weapon_oil = RECIPES.register("weapon_oil", () -> new SpecialRecipeSerializer<>(WeaponOilRecipe::new));

    static void registerRecipes(IEventBus bus) {
        RECIPES.register(bus);
    }
}
