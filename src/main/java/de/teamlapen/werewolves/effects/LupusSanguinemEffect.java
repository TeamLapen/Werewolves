package de.teamlapen.werewolves.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.mixin.client.ScreenAccessor;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;

public class LupusSanguinemEffect extends WerewolvesEffect {

    private static final String REG_NAME = "lupus_sanguinem";

    public LupusSanguinemEffect() {
        super(REG_NAME, EffectType.HARMFUL, 0xe012ef);
    }

    public static void addSanguinemEffectRandom(@Nonnull LivingEntity entity, float percentage) {
        if (entity.getRandom().nextFloat() < percentage) {
            addSanguinemEffect(entity);
        }
    }

    public static void addSanguinemEffect(@Nonnull LivingEntity entity) {
        boolean canBecomeWerewolf = false; //TODO other entities
        if (entity instanceof PlayerEntity) {
            canBecomeWerewolf = Helper.canBecomeWerewolf(((PlayerEntity) entity));
        }
        if (canBecomeWerewolf) {
            entity.addEffect(new LupusSanguinemEffectInstance(Integer.MAX_VALUE));
        }
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof PlayerEntity) {
            FactionPlayerHandler.get((PlayerEntity) entityLivingBaseIn).joinFaction(WReference.WEREWOLF_FACTION);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 2;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {
        super.renderInventoryEffect(effect, gui, mStack, x, y, z);
        String s = UtilLib.translate(effect.getEffect().getDescriptionId());
        ((ScreenAccessor) gui).getFont().drawShadow(mStack, s, (float) (x + 10 + 18), (float) (y + 6), 16777215);
        String duration = "**:**";
        ((ScreenAccessor) gui).getFont().drawShadow(mStack, duration, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
