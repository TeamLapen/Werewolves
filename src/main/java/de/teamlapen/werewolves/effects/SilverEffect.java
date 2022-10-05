package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.WerewolfSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class SilverEffect extends Effect {
    private static final String MOVEMENT_SPEED = "8ffcfde9-4799-4120-8714-4f479cc6e23e";
    private static final String ARMOR = "19435a2e-9f5b-4d3b-952e-b1f561e06cab";

    public SilverEffect() {
        super(EffectType.HARMFUL, 0xC0C0C0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED, WerewolvesConfig.BALANCE.POTIONS.silverStatsReduction.get(), AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR, WerewolvesConfig.BALANCE.POTIONS.silverStatsReduction.get(), AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static EffectInstance createEffect(LivingEntity entity, int defaultDuration) {
        if (entity instanceof PlayerEntity && Helper.isWerewolf(((PlayerEntity) entity))) {
            if (WerewolfPlayer.getOpt(((PlayerEntity) entity)).map(w -> w.getSkillHandler().isSkillEnabled(WerewolfSkills.SILVER_BLOODED.get())).orElse(false)) {
                defaultDuration /= 2f;
            }
        }
        return new EffectInstance(ModEffects.SILVER.get(), defaultDuration);
    }
}
