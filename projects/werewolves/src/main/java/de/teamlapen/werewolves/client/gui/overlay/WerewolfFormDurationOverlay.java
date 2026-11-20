package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.faction.client.gui.overlay.TextureOverlay;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;

public class WerewolfFormDurationOverlay extends TextureOverlay {

    private final Minecraft mc = Minecraft.getInstance();
    protected static final Identifier EXPERIENCE_BAR_BACKGROUND_SPRITE = WIdentifier.mc("hud/experience_bar_background");
    protected static final Identifier EXPERIENCE_BAR_PROGRESS_SPRITE = WIdentifier.mc("hud/experience_bar_progress");

    @Override
    public void render(GuiGraphicsExtractor pGuiGraphics, DeltaTracker pDeltaTracker) {
        Player player = this.mc.player;
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            Holder<? extends WerewolfFormAction> lastFormAction = werewolf.getLastFormAction();
            if (werewolf.getSpecialAttributes().transformationTime > 0 && lastFormAction != null && lastFormAction.value().consumesWerewolfTime(werewolf)) {
                double perc = 1 - werewolf.getSpecialAttributes().transformationTime;
                float trans = FormHelper.getActiveFormAction(werewolf).map(werewolfFormAction -> werewolfFormAction.value().consumesWerewolfTime(werewolf)).orElse(false) ? 1f : 0.7f;
                renderExpBar(pGuiGraphics, perc, trans);
            }
        }
    }

    private void renderExpBar(GuiGraphicsExtractor graphics, double perc, float transparency) {
        int scaledWidth = graphics.guiWidth();
        int scaledHeight = graphics.guiHeight();
        int x = scaledWidth / 2 - 91;

        var color = ARGB.colorFromFloat(transparency, 1, 0.1f, 0);

        int j = 182;
        int k = (int) ((1 - perc) * 183.0F);
        int l = scaledHeight - 32 + 3;
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EXPERIENCE_BAR_BACKGROUND_SPRITE, x, l, j, 5, color);
        if (k > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EXPERIENCE_BAR_PROGRESS_SPRITE, j, 5, k, 0, x + k, l, j - k, 5, color);
        }
    }
}
