package de.teamlapen.werewolves.util;

import de.teamlapen.werewolves.common.world.attachments.LevelDamage;
import de.teamlapen.werewolves.common.world.attachments.ModDamageSources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class DamageHandler {

    public static Optional<DamageSource> getDamageSource(Level world, WerewolvesDamageSourceFunction sourceFunc) {
        return LevelDamage.getOpt(world).map(sourceFunc::getDamageSource);
    }

    public static boolean hurtModded(ServerLevel level, Entity entity, WerewolvesDamageSourceFunction sourceFunc, float amount) {
        return getDamageSource(entity.level(), sourceFunc).map(source -> entity.hurtServer(level, source, amount)).orElse(false);
    }

    @FunctionalInterface
    public interface WerewolvesDamageSourceFunction {
        DamageSource getDamageSource(ModDamageSources damageSources);
    }
}
