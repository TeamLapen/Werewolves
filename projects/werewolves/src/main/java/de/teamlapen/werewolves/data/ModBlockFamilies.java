package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.mixin.BlockFamiliesAccessor;
import net.minecraft.data.BlockFamily;

public class ModBlockFamilies {

    public static final BlockFamily JACARANDA_PLANKS = BlockFamiliesAccessor.familyBuilder(ModBlocks.JACARANDA_PLANKS.get()).button(ModBlocks.JACARANDA_BUTTON.get()).fence(ModBlocks.JACARANDA_FENCE.get()).fenceGate(ModBlocks.JACARANDA_FENCE_GATE.get()).pressurePlate(ModBlocks.JACARANDA_PRESSURE_PLATE.get()).sign(ModBlocks.JACARANDA_SIGN.get(), ModBlocks.JACARANDA_WALL_SIGN.get()).slab(ModBlocks.JACARANDA_SLAB.get()).stairs(ModBlocks.JACARANDA_STAIRS.get()).door(ModBlocks.JACARANDA_DOOR.get()).trapdoor(ModBlocks.JACARANDA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    public static final BlockFamily MAGIC_PLANKS = BlockFamiliesAccessor.familyBuilder(ModBlocks.MAGIC_PLANKS.get()).button(ModBlocks.MAGIC_BUTTON.get()).fence(ModBlocks.MAGIC_FENCE.get()).fenceGate(ModBlocks.MAGIC_FENCE_GATE.get()).pressurePlate(ModBlocks.MAGIC_PRESSURE_PLATE.get()).sign(ModBlocks.MAGIC_SIGN.get(), ModBlocks.MAGIC_WALL_SIGN.get()).slab(ModBlocks.MAGIC_SLAB.get()).stairs(ModBlocks.MAGIC_STAIRS.get()).door(ModBlocks.MAGIC_DOOR.get()).trapdoor(ModBlocks.MAGIC_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();


    public static void init() {

    }
}
