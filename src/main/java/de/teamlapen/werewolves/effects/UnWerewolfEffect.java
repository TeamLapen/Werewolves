package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.client.extensions.EffectExtensions;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class UnWerewolfEffect extends WerewolvesEffect {
    private static final Logger LOGGER = LogManager.getLogger();

    public UnWerewolfEffect() {
        super(MobEffectCategory.NEUTRAL, 0xdc9417);
    }

    @Override
    public boolean applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!entityLivingBaseIn.getCommandSenderWorld().isClientSide()) {
            if (entityLivingBaseIn instanceof Player player) {
                if (Helper.isWerewolf(player)) {
                    FactionPlayerHandler handler = FactionPlayerHandler.get(player);
                    handler.setFactionAndLevel(null, 0);
                    player.displayClientMessage(Component.translatable("text.werewolves.no_longer_werewolf"), true);
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

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(EffectExtensions.HIDDEN_EFFECT);
    }
}
