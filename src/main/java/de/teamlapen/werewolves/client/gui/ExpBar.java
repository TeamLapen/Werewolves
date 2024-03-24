package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.util.MultilineTooltip;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.werewolves.entities.player.werewolf.LevelHandler;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.MultilineTooltipEx;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpBar extends AbstractWidget {
    private static final ResourceLocation ICON = new ResourceLocation(REFERENCE.MODID, "textures/gui/exp_bar.png");

    public ExpBar(int xIn, int yIn) {
        super(xIn, yIn, 15, 123, Component.translatable("text.werewolves.skill_screen.level_progression", (int) Math.ceil(WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc() * 100)));
        this.setTooltip(new MultilineTooltipEx(createToolTip()));
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(ICON, this.getX(), this.getY(), 10, 0, this.getWidth(), this.getHeight());

        float perc = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc();

        int ySize = ((int) (111 * perc));
        int color = Optional.ofNullable(VampirismAPI.factionPlayerHandler(Minecraft.getInstance().player).getCurrentFaction()).map(IFaction::getColor).orElse(Color.WHITE.getRGB());
        graphics.setColor(((color >> 16) & 0xFF) / 255F, ((color >> 8) & 0xFF) / 255F, (color & 0xFF) / 255F, 1.0F);

        graphics.blit(ICON, this.getX() + 5, this.getY() + 6, 0, 0, 5, 111);
        graphics.blit(ICON, this.getX() + 5, this.getY() + 6 + (111 - ySize), 5, (111 - ySize), 5, ySize);

        graphics.setColor(1, 1, 1, 1);
    }

    public List<Component> createToolTip() {
        List<Component> tooltips = new ArrayList<>();
        tooltips.add(Component.translatable("text.werewolves.skill_screen.level_progression_label"));
        LevelHandler handler = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler();
        tooltips.add(Component.translatable("text.werewolves.skill_screen.prey_snatched", handler.getLevelProgress(), handler.getNeededProgress()));
        return tooltips;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {
        output.add(NarratedElementType.TITLE, this.getMessage());
    }
}
