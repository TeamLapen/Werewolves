package de.teamlapen.werewolves.common.world.effects;

import net.minecraft.server.level.ServerLevel;
import de.teamlapen.faction.common.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class UnWerewolfEffect extends WerewolvesEffect {
    private static final Logger LOGGER = LogManager.getLogger();

    public UnWerewolfEffect() {
        super(MobEffectCategory.NEUTRAL, 0xdc9417);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, @Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!entityLivingBaseIn.level().isClientSide()) {
            if (entityLivingBaseIn instanceof Player player) {
                if (Helper.isWerewolf(player)) {
                    FactionPlayerHandler.get(player).leaveFaction(false);
                    player.sendOverlayMessage(Component.translatable("text.werewolves.no_longer_werewolf"));
                    LOGGER.debug("Player {} left faction", player);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return duration == 1;
    }
}
