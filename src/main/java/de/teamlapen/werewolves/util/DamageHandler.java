package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.world.LevelDamage;
import de.teamlapen.werewolves.world.ModDamageSources;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DamageHandler extends de.teamlapen.vampirism.util.DamageHandler {

    public static @NotNull Optional<DamageSource> getDamageSource(@NotNull Level world, @NotNull WerewolvesDamageSourceFunction sourceFunc) {
        return LevelDamage.getOpt(world).map(sourceFunc::getDamageSource);
    }

    public static boolean hurtModded(@NotNull Entity entity, @NotNull WerewolvesDamageSourceFunction sourceFunc, float amount) {
        return getDamageSource(entity.level(), sourceFunc).map(source -> entity.hurt(source, amount)).orElse(false);
    }

    @FunctionalInterface
    public interface WerewolvesDamageSourceFunction {
        DamageSource getDamageSource(ModDamageSources damageSources);
    }
}
