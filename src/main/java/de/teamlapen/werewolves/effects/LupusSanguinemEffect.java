package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.client.render.util.HiddenDurationEffectRenderer;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.inst.LupusSanguinemEffectInstance;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class LupusSanguinemEffect extends WerewolvesEffect {

    public LupusSanguinemEffect() {
        super(MobEffectCategory.HARMFUL, 0xe012ef);
    }

    public static void infectRandomByPlayer(@Nonnull LivingEntity entity) {
        addSanguinemEffectRandom(entity, WerewolvesConfig.SERVER.playerBiteInfectChance.get());
    }

    public static void infectRandomByMob(@Nonnull LivingEntity entity) {
        addSanguinemEffectRandom(entity, WerewolvesConfig.SERVER.mobBiteInfectChance.get());
    }

    public static void addSanguinemEffectRandom(@Nonnull LivingEntity entity, double chance) {
        if (entity.getRandom().nextFloat() < chance) {
            addSanguinemEffect(entity);
        }
    }

    public static void addSanguinemEffect(@Nonnull LivingEntity entity) {
        boolean canBecomeWerewolf = false; //TODO other entities
        if (entity instanceof Player) {
            canBecomeWerewolf = Helper.canBecomeWerewolf(((Player) entity));
        }
        if (canBecomeWerewolf) {
            entity.addEffect(new LupusSanguinemEffectInstance(Integer.MAX_VALUE));
        }
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player) {
            FactionPlayerHandler.getOpt((Player) entityLivingBaseIn).ifPresent(player -> player.joinFaction(WReference.WEREWOLF_FACTION));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 2;
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new HiddenDurationEffectRenderer());
    }
}
