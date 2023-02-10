package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nonnull;


public class HowlingEffect extends WerewolvesEffect {

    public static final String ATTACK_SPEED = "193a0552-7368-4759-8020-3285caaf12f6";

    public HowlingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFC90E);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED, 2.0, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == WerewolvesConfig.BALANCE.SKILLS.howling_disabled_duration.get() * 20;
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        this.removeAttributeModifiers(entityLivingBaseIn, entityLivingBaseIn.getAttributes(), amplifier);
    }

    @Override
    public void removeAttributeModifiers(@Nonnull LivingEntity entityLivingBaseIn, @Nonnull AttributeMap attributeMapIn, int amplifier) {
        if (Helper.isWerewolf(entityLivingBaseIn)) {
            super.removeAttributeModifiers(entityLivingBaseIn, attributeMapIn, amplifier);
        }
    }

    @Override
    public void addAttributeModifiers(@Nonnull LivingEntity entityLivingBaseIn, @Nonnull AttributeMap attributeMapIn, int amplifier) {
        if (Helper.isWerewolf(entityLivingBaseIn)) {
            super.addAttributeModifiers(entityLivingBaseIn, attributeMapIn, amplifier);
        }
    }
}
