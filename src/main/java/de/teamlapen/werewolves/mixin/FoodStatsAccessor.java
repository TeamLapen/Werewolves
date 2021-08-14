package de.teamlapen.werewolves.mixin;

import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoodStats.class)
public interface FoodStatsAccessor {

    @Accessor("tickTimer")
    int getFoodTimer();

    @Accessor("tickTimer")
    void setFoodTimer(int timer);
}
