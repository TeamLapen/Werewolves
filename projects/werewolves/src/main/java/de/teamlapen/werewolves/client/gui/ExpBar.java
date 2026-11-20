package de.teamlapen.werewolves.client.gui;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModFactions;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.properties.LevelProgressHandler;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.List;

public class ExpBar extends AbstractWidget {
    private static final Identifier ICON = WIdentifier.mod("textures/gui/exp_bar.png");

    public ExpBar(int xIn, int yIn) {
        super(xIn, yIn, 15, 123, Component.empty());
    }

    private static LevelProgressHandler handler() {
        return WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler();
    }

    @Override
    public Component getMessage() {
        return Component.translatable("text.werewolves.skill_screen.level_progression", (int) Math.ceil(handler().getLevelPerc() * 100));
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, ICON, this.getX(), this.getY(), 10, 0, this.getWidth(), this.getHeight(),256,256);

        float perc = handler().getLevelPerc();

        int ySize = ((int) (111 * perc));
        var color = ModFactions.WEREWOLF.get().getColor();

        graphics.blit(RenderPipelines.GUI_TEXTURED, ICON, this.getX() + 5, this.getY() + 6, 0, 0, 5, 111,256,256,color);
        graphics.blit(RenderPipelines.GUI_TEXTURED, ICON, this.getX() + 5, this.getY() + 6 + (111 - ySize), 5, (111 - ySize), 5, ySize,256,256, color);

        if (this.isHovered()) {
            LevelProgressHandler handler = handler();
            graphics.setComponentTooltipForNextFrame(Minecraft.getInstance().font, List.of(
                    Component.translatable("text.werewolves.skill_screen.level_progression_label"),
                    Component.translatable("text.werewolves.skill_screen.prey_snatched", handler.getLevelProgress(), handler.getNeededProgress()).withStyle(ChatFormatting.GRAY)
            ), mouseX, mouseY);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.getMessage());
    }
}
