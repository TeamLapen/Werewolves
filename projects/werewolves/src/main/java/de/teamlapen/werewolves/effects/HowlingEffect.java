package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;


public class HowlingEffect extends WerewolvesEffect {

    public static final ResourceLocation ATTACK_SPEED = WIdentifier.mod("howling");

    public HowlingEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFC90E);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

}
