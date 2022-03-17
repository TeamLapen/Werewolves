package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.player.vampire.VampirePlayer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;

public class BleedingEffect extends WerewolvesEffect {

    public BleedingEffect() {
        super("bleeding", MobEffectCategory.HARMFUL, 0x740000);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int j = 20 >> amplifier;
        if (j > 0) {
            return duration % j == 0;
        } else {
            return true;
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn.isInvertedHealAndHarm()) {
            entityLivingBaseIn.removeEffect(ModEffects.bleeding);
            return;
        }

        if (entityLivingBaseIn.getHealth() > 1.0F) {
            entityLivingBaseIn.hurt(WUtils.OPEN_WOUND_DAMAGE_SOURCE, WerewolvesConfig.BALANCE.POTIONS.bleedingEffectDamage.get().floatValue());
            if (entityLivingBaseIn.getRandom().nextInt(4) == 0) {
                if (Helper.isVampire(entityLivingBaseIn)) {
                    if (entityLivingBaseIn instanceof Player) {
                        VampirePlayer.getOpt(((Player) entityLivingBaseIn)).map(vampire -> vampire.useBlood(2, true));
                    } else if (entityLivingBaseIn instanceof IVampire) {
                        ((IVampire) entityLivingBaseIn).useBlood(1, true);
                    }
                } else if (entityLivingBaseIn instanceof PathfinderMob) {
                    VampirismAPI.getExtendedCreatureVampirism((PathfinderMob) entityLivingBaseIn).ifPresent(creature -> creature.setBlood(creature.getBlood() - 1));
                }
            }
        }
    }
}
