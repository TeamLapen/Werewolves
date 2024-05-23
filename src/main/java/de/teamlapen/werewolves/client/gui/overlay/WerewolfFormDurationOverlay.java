package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.entities.player.werewolf.actions.WerewolfFormAction;
import de.teamlapen.werewolves.mixin.client.InGameGuiAccessor;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.NotNull;

public class WerewolfFormDurationOverlay implements IGuiOverlay {

    private final Minecraft mc = Minecraft.getInstance();
    protected static final ResourceLocation EXPERIENCE_BAR_BACKGROUND_SPRITE = new ResourceLocation("hud/experience_bar_background");
    protected static final ResourceLocation EXPERIENCE_BAR_PROGRESS_SPRITE = new ResourceLocation("hud/experience_bar_progress");

    @Override
    public void render(@NotNull ExtendedGui gui, @NotNull GuiGraphics graphics, float partialTicks, int width, int height) {
        Player player = this.mc.player;
        if (Helper.isWerewolf(player)) {
            WerewolfPlayer werewolf = WerewolfPlayer.get(player);
            WerewolfFormAction lastFormAction = werewolf.getLastFormAction();
            if (werewolf.getSpecialAttributes().transformationTime > 0 && lastFormAction != null && lastFormAction.consumesWerewolfTime(werewolf)) {
                double perc = 1 - werewolf.getSpecialAttributes().transformationTime;
                float trans = FormHelper.getActiveFormAction(werewolf).map(werewolfFormAction -> werewolfFormAction.consumesWerewolfTime(werewolf)).orElse(false) ? 1f : 0.7f;
                renderExpBar(graphics, perc, trans);
            }
        }
    }

    private void renderExpBar(GuiGraphics graphics, double perc, float transparency) {
        int scaledWidth = ((InGameGuiAccessor) Minecraft.getInstance().gui).getScaledWidth();
        int scaledHeight = ((InGameGuiAccessor) Minecraft.getInstance().gui).getScaledHeight();
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
