package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.effects.inst.LupusSanguinemEffectInstance;
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
            entity.addEffect(new LupusSanguinemEffectInstance(-1));
        }
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof Player) {
            FactionPlayerHandler.get((Player) entityLivingBaseIn).joinFaction(WReference.WEREWOLF_FACTION);
        }
    }

}
