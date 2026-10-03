package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class WerewolfFormDurationOverlay implements LayeredDraw.Layer {

    private final Minecraft mc = Minecraft.getInstance();
    protected static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = WResourceLocation.mc("hud/experience_bar_background");
    protected static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = WResourceLocation.mc("hud/experience_bar_progress");

    @Override
    public void render(GuiGraphics pGuiGraphics, DeltaTracker pDeltaTracker) {
        Player player = this.mc.player;
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            WerewolfFormAction lastFormAction = werewolf.getLastFormAction();
            if (werewolf.getSpecialAttributes().transformationTime > 0 && lastFormAction != null && lastFormAction.consumesWerewolfTime(werewolf)) {
                double perc = 1 - werewolf.getSpecialAttributes().transformationTime;
                float trans = FormHelper.getActiveFormAction(werewolf).map(werewolfFormAction -> werewolfFormAction.consumesWerewolfTime(werewolf)).orElse(false) ? 1f : 0.7f;
                renderExpBar(pGuiGraphics, perc, trans);
            }
        }
    }

    private void renderExpBar(GuiGraphics graphics, double perc, float transparency) {
        int scaledWidth = graphics.guiWidth();
        int scaledHeight = graphics.guiHeight();
        int x = scaledWidth / 2 - 91;

        graphics.setColor(1f, 0.1f, 0f, transparency);
        RenderSystem.disableBlend();

            int j = 182;
            int k = (int)((1-perc) * 183.0F);
            int l = scaledHeight - 32 + 3;
            graphics.blitSprite(EXPERIENCE_BAR_BACKGROUND_SPRITE, x, l, j, 5);
            if (k > 0) {
                graphics.blitSprite(EXPERIENCE_BAR_PROGRESS_SPRITE, j, 5, k, 0, x+k, l, j-k, 5);
            }

        RenderSystem.enableBlend();
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
