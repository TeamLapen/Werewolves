package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.UUID;

public class WolfsbaneEffect extends WerewolfWeakeningEffect {

    private static final String MOVEMENT_SPEED = "8ffcfde9-4799-4120-8714-4f479cc6e23e";

    public WolfsbaneEffect() {
        super(0x6006ab, List.of(new Modifier(Attributes.MOVEMENT_SPEED, ModEffects.WOLFSBANE.getId(), 0.15f)));
    }

    public static MobEffectInstance createWolfsbaneEffect(LivingEntity entity, int defaultDuration) {
        return new MobEffectInstance(ModEffects.WOLFSBANE, defaultDuration);
    }

    public static MobEffectInstance createWolfsbaneEffect(LivingEntity entity, int defaultDuration, int amplifier) {
        return new MobEffectInstance(ModEffects.WOLFSBANE, defaultDuration, amplifier);
    }
}
