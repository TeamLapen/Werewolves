package de.teamlapen.werewolves.effects;

import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.core.ModSkills;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.AttributeModifierTemplate;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.UUID;

public class SilverEffect extends WerewolfWeakeningEffect {

    private static final String MOVEMENT_SPEED = "d28ab2de-2f21-43fe-914e-2cae40c924e4";
    private static final String ARMOR = "ff4dc3ea-68fe-4bfe-aca6-8db3cf26b285";
    private static final String DAMAGE = "67318644-855f-49ad-9c74-f310e270a7f5";

    public SilverEffect() {
        super(0xC0C0C0, List.of(new Modifier(Attributes.MOVEMENT_SPEED, UUID.fromString(MOVEMENT_SPEED), "Silver effect", 0.15f), new Modifier(Attributes.ARMOR, UUID.fromString(ARMOR), "Silver effect", 0.15f), new Modifier(Attributes.ATTACK_DAMAGE, UUID.fromString(DAMAGE), "Silver effect", 0.075f, 1)));
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
        return new MobEffectInstance(ModEffects.SILVER.get(), defaultDuration, amplifier, isContinued, true);
    }
}
