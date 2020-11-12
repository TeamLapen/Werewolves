package de.teamlapen.werewolves.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class LupusSanguinem extends WerewolvesEffect {

    private static final String REG_NAME = "lupus_sanguinem";

    public static void addSanguinemEffect(LivingEntity entity) {
        boolean canBecomeWerewolf = false; //TODO other entities
        if (entity instanceof PlayerEntity) {
            canBecomeWerewolf = FactionPlayerHandler.getOpt(((PlayerEntity) entity)).map(player -> player.canJoin(WReference.WEREWOLF_FACTION)).orElse(false);
        }
        if (canBecomeWerewolf) {
            if (entity.getRNG().nextInt(5) == 0) {
                entity.addPotionEffect(new LupusSanguinemInstance(Integer.MAX_VALUE));
            }
        }
    }

    public LupusSanguinem() {
        super(REG_NAME, EffectType.HARMFUL, 0xe012ef);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof PlayerEntity) {
            FactionPlayerHandler.getOpt((PlayerEntity) entityLivingBaseIn).ifPresent(e -> e.joinFaction(WReference.WEREWOLF_FACTION));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return false;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {
        String s = UtilLib.translate(effect.getPotion().getName());
        gui.font.drawStringWithShadow(mStack, s, (float) (x + 10 + 18), (float) (y + 6), 16777215);
    }
}
