package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class WerewolfWeakeningEffect extends WerewolvesEffect {
    private static final String MOVEMENT_SPEED = "8ffcfde9-4799-4120-8714-4f479cc6e23e";
    private static final String ARMOR = "19435a2e-9f5b-4d3b-952e-b1f561e06cab";

    public WerewolfWeakeningEffect(int color) {
        super(MobEffectCategory.HARMFUL, color);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED, -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, ARMOR, -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
    }

    public static MobEffectInstance createSilverEffect(LivingEntity entity, int defaultDuration, int amplifier) {
        if (entity instanceof Player && Helper.isWerewolf(((Player) entity))) {
            if (WerewolfPlayer.getOpt(((Player) entity)).map(w -> w.getSkillHandler().isSkillEnabled(ModSkills.SILVER_BLOODED.get())).orElse(false)) {
                defaultDuration /= 2f;
            }
        }
        return new MobEffectInstance(ModEffects.SILVER.get(), defaultDuration, amplifier);
    }

    public static MobEffectInstance createWolfsbaneEffect(LivingEntity entity, int defaultDuration) {
        return new MobEffectInstance(ModEffects.WOLFSBANE.get(), defaultDuration);

    }
}
