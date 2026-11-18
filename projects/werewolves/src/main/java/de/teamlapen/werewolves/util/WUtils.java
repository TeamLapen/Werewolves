package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.misc.mixin.ObjectiveCriteriaAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;

import java.util.function.Predicate;

public class WUtils {
    public static final BooleanProperty SOUL_FIRE = BooleanProperty.create("soulfire");
    public static final ObjectiveCriteria WEREWOLF_LEVEL_CRITERIA = ObjectiveCriteriaAccessor.registerCustom("werewolves:werewolf");
    public static final Predicate<Entity> IS_WEREWOLF = Helper::isWerewolf;

    @SuppressWarnings("EmptyMethod")
    public static void init() {
    }
}
