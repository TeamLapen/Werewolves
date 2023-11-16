package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.client.render.util.HiddenDurationEffectRenderer;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class UnWerewolfEffect extends WerewolvesEffect {
    private static final Logger LOGGER = LogManager.getLogger();

    public UnWerewolfEffect() {
        super("un_werewolf", MobEffectCategory.NEUTRAL, 0xdc9417);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!entityLivingBaseIn.getCommandSenderWorld().isClientSide()) {
            if (entityLivingBaseIn instanceof Player player) {
                if (Helper.isWerewolf(player)) {
                    FactionPlayerHandler.getOpt(player).ifPresent(s -> {
                        s.setFactionAndLevel(null, 0);
                        player.displayClientMessage(Component.translatable("text.werewolves.no_longer_werewolf"), true);
                        LOGGER.debug("Player {} left faction", player);
                    });
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new HiddenDurationEffectRenderer());
    }
}
