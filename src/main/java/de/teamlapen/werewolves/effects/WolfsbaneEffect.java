package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.UUID;

public class WolfsbaneEffect extends WerewolfWeakeningEffect {

    private static final String MOVEMENT_SPEED = "8ffcfde9-4799-4120-8714-4f479cc6e23e";
    private static final String ARMOR = "19435a2e-9f5b-4d3b-952e-b1f561e06cab";

    public WolfsbaneEffect() {
        super(0x6006ab, List.of(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString(MOVEMENT_SPEED), "Wolfsbane effect"), new Modifier(Attributes.ARMOR, UUID.fromString(ARMOR), "Wolfsbane effect")));
    }

    public static MobEffectInstance createWolfsbaneEffect(LivingEntity entity, int defaultDuration) {
        return new MobEffectInstance(ModEffects.WOLFSBANE.get(), defaultDuration);
    }

    public static MobEffectInstance createWolfsbaneEffect(LivingEntity entity, int defaultDuration, int amplifier) {
        return new MobEffectInstance(ModEffects.WOLFSBANE.get(), defaultDuration, amplifier);
    }
}
