package de.teamlapen.werewolves.common.integration.jei.vampirism;

import net.minecraft.world.item.ItemStack;

/**
 * A wolfsbane diffuser fuel with its burn duration in ticks
 */
public record WolfsbaneDiffuserRecipe(ItemStack input, int burnTime) {
}
