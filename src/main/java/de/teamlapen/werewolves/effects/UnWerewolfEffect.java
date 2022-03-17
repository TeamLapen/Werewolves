package de.teamlapen.werewolves.effects;

import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.entity.factions.FactionPlayerHandler;
import de.teamlapen.werewolves.mixin.client.ScreenAccessor;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.EffectRenderer;
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
            if (entityLivingBaseIn instanceof Player) {
                Player player = ((Player) entityLivingBaseIn);
                if (Helper.isWerewolf(player)) {
                    FactionPlayerHandler.get(player).setFactionAndLevel(null, 0);
                    player.displayClientMessage(new TranslatableComponent("text.werewolves.no_longer_werewolf"), true);
                    LOGGER.debug("Player {} left faction", player);
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration == 1;
    }

    @Override
    public void initializeClient(Consumer<EffectRenderer> consumer) {
        consumer.accept(new EffectRenderer() {
            @Override
            public void renderInventoryEffect(MobEffectInstance effect, EffectRenderingInventoryScreen<?> gui, PoseStack mStack, int x, int y, float z) {
                String s = UtilLib.translate(effect.getEffect().getDescriptionId());
                ((ScreenAccessor) gui).getFont().drawShadow(mStack, s, (float) (x + 10 + 18), (float) (y + 6), 16777215);
                String duration = "**:**";
                ((ScreenAccessor) gui).getFont().drawShadow(mStack, duration, (float) (x + 10 + 18), (float) (y + 6 + 10), 8355711);
            }

            @Override
            public void renderHUDEffect(MobEffectInstance effect, GuiComponent gui, PoseStack mStack, int x, int y, float z, float alpha) {

            }

            @Override
            public boolean shouldRenderInvText(MobEffectInstance effect) {
                return false;
            }
        });
    }
}
