package de.teamlapen.werewolves.effects;

import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.client.render.util.HiddenDurationEffectRenderer;
import de.teamlapen.werewolves.effects.inst.LupusSanguinemEffectInstance;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.EffectRenderer;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class LupusSanguinemEffect extends WerewolvesEffect {

    private static final String REG_NAME = "lupus_sanguinem";

    public LupusSanguinemEffect() {
        super(REG_NAME, MobEffectCategory.HARMFUL, 0xe012ef);
    }

    public static void addSanguinemEffectRandom(@Nonnull LivingEntity entity, float percentage) {
        if (entity.getRandom().nextFloat() < percentage) {
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
    public void initializeClient(Consumer<EffectRenderer> consumer) {
        consumer.accept(new HiddenDurationEffectRenderer());
    }
}
