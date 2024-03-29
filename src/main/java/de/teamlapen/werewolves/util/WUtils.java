package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.mixin.ObjectiveCriteriaAccessor;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.common.ForgeTier;

public class WUtils {
    public static final BooleanProperty SOUL_FIRE = BooleanProperty.create("soulfire");
    public static final ObjectiveCriteria WEREWOLF_LEVEL_CRITERIA = ObjectiveCriteriaAccessor.registerCustom("werewolves:werewolf");
    public static final Tier SILVER_ITEM_TIER = new ForgeTier(2,250,6.0f,2f,14, ModTags.Blocks.NEEDS_SILVER_TOOL, () -> Ingredient.of(ModTags.Items.SILVER_INGOT));

    @SuppressWarnings("EmptyMethod")
    public static void init() {
    }
}
