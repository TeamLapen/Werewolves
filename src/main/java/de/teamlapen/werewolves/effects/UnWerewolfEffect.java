package de.teamlapen.werewolves.effects;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.mixin.MixinAccessors;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

public class UnWerewolfEffect extends WerewolvesEffect {
    private static final Logger LOGGER = LogManager.getLogger();

    public UnWerewolfEffect() {
        super("un_werewolf", EffectType.NEUTRAL, 0xdc9417);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
        if (!entityLivingBaseIn.getEntityWorld().isRemote()) {
            if (entityLivingBaseIn instanceof PlayerEntity) {
                PlayerEntity player = ((PlayerEntity) entityLivingBaseIn);
                if (Helper.isWerewolf(player)) {
                    FactionPlayerHandler.get(player).setFactionAndLevel(null, 0);
                    player.sendStatusMessage(new TranslationTextComponent("text.werewolves.no_longer_werewolf"), true);
                    LOGGER.debug("Player {} left faction", player);
                }
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public boolean shouldRenderInvText(EffectInstance effect) {
        return false;
    }

    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {
        super.renderInventoryEffect(effect, gui, mStack, x, y, z);
        String s = UtilLib.translate(effect.getPotion().getName());
        ((MixinAccessors.ScreenMixin) gui).getFont().drawStringWithShadow(mStack, s, (float) (x + 10 + 18), (float) (y + 6), 16777215);
        String duration = "**:**";
        ((MixinAccessors.ScreenMixin) gui).getFont().drawStringWithShadow(mStack, duration, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
    }
}
