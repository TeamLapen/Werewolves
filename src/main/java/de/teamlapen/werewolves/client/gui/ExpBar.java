package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.werewolves.entities.player.werewolf.LevelHandler;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ExpBar extends Widget {
    private static final ResourceLocation ICON = new ResourceLocation(REFERENCE.MODID, "textures/gui/exp_bar.png");

    private final Screen screen;

    public ExpBar(int xIn, int yIn, Screen screen) {
        super(xIn, yIn, 10, 202, new TranslationTextComponent("text.werewolves.skill_screen.level_progression", (int) Math.ceil(WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc() * 100)));
        this.screen = screen;
    }

    @Override
    public void render(@Nonnull MatrixStack stack, int p_render_1_, int p_render_2_, float p_render_3_) {
        if (this.visible) {
            Minecraft.getInstance().textureManager.bind(ICON);
            this.blit(stack, this.x, this.y, 10, 0, 15, 123);

            float perc = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc();

            int ySize = ((int) (111 * perc));
            Color color = VampirismAPI.getFactionPlayerHandler(Minecraft.getInstance().player).map(IFactionPlayerHandler::getCurrentFaction).map(IFaction::getColor).orElse(Color.WHITE);
            RenderSystem.color4f(color.getRed()/255F, color.getGreen() / 255F, color.getBlue() / 255F, 1.0F);

            blit(stack, this.x + 5, this.y + 6, 0, 0, 5, 111);
            blit(stack, this.x + 5, this.y + 6 + (111 - ySize), 5, (111 - ySize), 5, ySize);

            this.renderToolTip(stack, p_render_1_, p_render_2_);
        }
    }


    @Override
    public void renderToolTip(@Nonnull MatrixStack stack, int mouseX, int mouseY) {
        if (mouseX > this.x && mouseX < this.x + 15 && mouseY > this.y && mouseY < this.y + 123) {
            List<IReorderingProcessor> tooltips = new ArrayList<>();
            tooltips.add(new TranslationTextComponent("text.werewolves.skill_screen.level_progression_label").getVisualOrderText());
            LevelHandler handler = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler();
            tooltips.add(new TranslationTextComponent("text.werewolves.skill_screen.prey_snatched", Math.min(handler.getLevelProgress(), handler.getNeededProgress()), handler.getNeededProgress()).getVisualOrderText());
            this.screen.renderTooltip(stack, tooltips, mouseX, mouseY);
        }
    }
}
