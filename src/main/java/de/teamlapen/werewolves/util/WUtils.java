package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.mixin.ObjectiveCriteriaAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.neoforged.neoforge.common.SimpleTier;

import java.util.function.Predicate;

public class WUtils {
    public static final BooleanProperty SOUL_FIRE = BooleanProperty.create("soulfire");
    public static final ObjectiveCriteria WEREWOLF_LEVEL_CRITERIA = ObjectiveCriteriaAccessor.registerCustom("werewolves:werewolf");
    public static final Tier SILVER_ITEM_TIER = new SimpleTier(2,250,8.0f,1.5f,12, ModTags.Blocks.NEEDS_SILVER_TOOL, () -> Ingredient.of(ModTags.Items.SILVER_INGOT));
    public static final Predicate<Entity> IS_WEREWOLF = Helper::isWerewolf;

    @SuppressWarnings("EmptyMethod")
    public static void init() {
    }
}
