package de.teamlapen.werewolves.common.world.effects;

import net.minecraft.server.level.ServerLevel;
import de.teamlapen.faction.common.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.effects.inst.LupusSanguinemEffectInstance;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public class LupusSanguinemEffect extends WerewolvesEffect {

    public LupusSanguinemEffect() {
        super(MobEffectCategory.HARMFUL, 0xe012ef);
    }

    public static void infectRandomByPlayer(@Nonnull LivingEntity entity) {
        addSanguinemEffectRandom(entity, ModConfig.server().playerBiteInfectChance.get());
    }

    public static void infectRandomByMob(@Nonnull LivingEntity entity) {
        addSanguinemEffectRandom(entity, ModConfig.server().mobBiteInfectChance.get());
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
            entity.addEffect(new LupusSanguinemEffectInstance(-1));
        }
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, @Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player) {
            FactionPlayerHandler.get((Player) entityLivingBaseIn).joinFaction(ModFactions.WEREWOLF);
            return false;
        }
        return true;
    }

}
