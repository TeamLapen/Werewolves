package de.teamlapen.werewolves.potions;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;


public class HowlingEffect extends WerewolvesEffect{

    public static final String ATTACK_SPEED = "193a0552-7368-4759-8020-3285caaf12f6";

    public HowlingEffect() {
        super("howling", EffectType.BENEFICIAL, 0xFFC90E);
        this.addAttributesModifier(SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED, WerewolvesConfig.BALANCE.howling_attackspeed_amount.get(), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration == WerewolvesConfig.BALANCE.howling_disabled_duration.get() * 20;
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        this.removeAttributesModifiersFromEntity(entityLivingBaseIn, entityLivingBaseIn.getAttributes(),amplifier);
    }

    @Override
    public void applyAttributesModifiersToEntity(@Nonnull LivingEntity entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        if (Helper.isWerewolf(entityLivingBaseIn)) {
            super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        }
    }

    @Override
    public void removeAttributesModifiersFromEntity(@Nonnull LivingEntity entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier) {
        if(Helper.isWerewolf(entityLivingBaseIn)) {
            super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        }
    }
}
