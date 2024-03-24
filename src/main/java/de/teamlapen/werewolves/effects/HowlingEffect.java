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
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED, 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

}
