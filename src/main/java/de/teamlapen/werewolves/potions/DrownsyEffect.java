package de.teamlapen.werewolves.potions;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModEffects;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class DrownsyEffect extends WerewolvesEffect {

    private static final String REGNAME = "drownsy";

    public DrownsyEffect() {
        super(REGNAME, EffectType.HARMFUL, 0xe012ef);
    }

    public static int getPotionDuration() {
        return WerewolvesConfig.BALANCE.UTIL.drownsytime.get() * 1200;
    }

    public static void addDrownsyPotion(PlayerEntity playerEntity) {
        if (FactionPlayerHandler.getOpt(playerEntity).map(player -> player.canJoin(WReference.WEREWOLF_FACTION)).orElse(false)) {
            playerEntity.addPotionEffect(new EffectInstance(ModEffects.drownsy, getPotionDuration()));
        }
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof PlayerEntity) {
            //TODO nice effect
            FactionPlayerHandler.getOpt((PlayerEntity) entityLivingBaseIn).ifPresent(e -> e.joinFaction(WReference.WEREWOLF_FACTION));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration == 1;
    }
}
