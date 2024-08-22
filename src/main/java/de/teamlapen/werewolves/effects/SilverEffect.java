package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public class SilverEffect extends WerewolfWeakeningEffect {

    public SilverEffect() {
        super(0xC0C0C0, List.of(new Modifier(Attributes.MOVEMENT_SPEED, ModEffects.SILVER.getId(), 0.15f), new Modifier(Attributes.ARMOR, ModEffects.SILVER.getId(), 0.15f), new Modifier(Attributes.ATTACK_DAMAGE, ModEffects.SILVER.getId(), 0.1f)));
    }

    public static MobEffectInstance createSilverEffect(LivingEntity entity, int defaultDuration, int amplifier) {
        return createSilverEffect(entity, defaultDuration, amplifier, false);
    }

    public static MobEffectInstance createSilverEffect(LivingEntity entity, int defaultDuration, int amplifier, boolean isContinued) {
        if (entity instanceof Player && Helper.isWerewolf(((Player) entity))) {
            var werewolf = WerewolfPlayer.get(((Player) entity));
            if (werewolf.getForm().isHumanLike() && werewolf.getSkillHandler().isSkillEnabled(ModSkills.SILVER_BLOODED.get())) {
                if (amplifier > 0) {
                    amplifier--;
                } else if (!isContinued){
                    defaultDuration /= 2;
                }
            }
        }
        return new MobEffectInstance(ModEffects.SILVER, defaultDuration, amplifier, isContinued, true);
    }
}
