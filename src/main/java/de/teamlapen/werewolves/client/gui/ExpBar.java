package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.util.MultilineTooltip;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.werewolves.entities.player.werewolf.LevelHandler;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExpBar extends AbstractWidget {
    private static final ResourceLocation ICON = new ResourceLocation(REFERENCE.MODID, "textures/gui/exp_bar.png");

    private final Screen screen;

    public ExpBar(int xIn, int yIn, Screen screen) {
        super(xIn, yIn, 10, 202, Component.translatable("text.werewolves.skill_screen.level_progression", (int) Math.ceil(WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc() * 100)));
        this.screen = screen;
    }

    @Override
    public void render(@Nonnull PoseStack stack, int p_render_1_, int p_render_2_, float p_render_3_) {
        if (this.visible) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, ICON);
            this.blit(stack, this.getX(), this.getY(), 10, 0, 15, 123);

            float perc = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc();

            int ySize = ((int) (111 * perc));
            int color = VampirismAPI.getFactionPlayerHandler(Minecraft.getInstance().player).map(IFactionPlayerHandler::getCurrentFaction).map(IFaction::getColor).orElse(Color.WHITE.getRGB());
            RenderSystem.setShaderColor(((color >> 16) & 0xFF) / 255F, ((color >> 8) & 0xFF) / 255F, (color & 0xFF) / 255F, 1.0F);

            blit(stack, this.getX() + 5, this.getY() + 6, 0, 0, 5, 111);
            blit(stack, this.getX() + 5, this.getY() + 6 + (111 - ySize), 5, (111 - ySize), 5, ySize);

            if (this.isHovered) {
                this.setTooltip(new MultilineTooltip(createToolTip()));
            }
        }
    }


    public List<Component> createToolTip() {
        List<Component> tooltips = new ArrayList<>();
        tooltips.add(Component.translatable("text.werewolves.skill_screen.level_progression_label"));
        LevelHandler handler = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler();
        tooltips.add(Component.translatable("text.werewolves.skill_screen.prey_snatched", Math.min(handler.getLevelProgress(), handler.getNeededProgress())));
        return tooltips;
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput output) {

    }
}
