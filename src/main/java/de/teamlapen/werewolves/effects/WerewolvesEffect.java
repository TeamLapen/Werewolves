package de.teamlapen.werewolves.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.jetbrains.annotations.NotNull;


public class WerewolvesEffect extends MobEffect {
    public WerewolvesEffect(String name, @NotNull MobEffectCategory typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }
}
