package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.vampirism.client.gui.SkillsScreen;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class ExpBar extends Widget {
    private static final ResourceLocation ICON = new ResourceLocation(REFERENCE.MODID, "textures/gui/skill_screen.png");

    private static final int display_width = 256;
    private static final int display_height = 202;

    private final SkillsScreen screen;
    private boolean init;

    public ExpBar(int xIn, int yIn, SkillsScreen screen) {
        super(xIn, yIn, 10, 202, new TranslationTextComponent("text.werewolves.skill_screen.level_progression", (int) Math.ceil(WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc() * 100)));
        this.screen = screen;
    }

    @Override
    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        if (!this.init) {
            this.init = true;
            this.x = ((this.screen.width - display_width) / 2) - 20;
            this.y = (this.screen.height - display_height) / 2;
        }
        if (this.visible) {
            Color color = VampirismAPI.getFactionPlayerHandler(Minecraft.getInstance().player).map(IFactionPlayerHandler::getCurrentFaction).map(IFaction::getColor).orElse(Color.WHITE);
            Minecraft.getInstance().textureManager.bind(ICON);
            RenderSystem.color4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1.0F);
            this.blit(matrixStack, this.x, this.y, 10, 0, 19, 202);

            float perc = WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc();

            int ySize = ((int) (182 * perc));
            RenderSystem.color4f(170 / 255F, 16 / 255F, 208 / 255F, 1.0F);

            blit(matrixStack, this.x + 7, this.y + 10, 0, 0, 5, 182);
            blit(matrixStack, this.x + 7, this.y + 10 + (182 - ySize), 5, (182 - ySize), 5, ySize);

            this.renderToolTip(matrixStack, p_render_1_, p_render_2_);
        }
    }


    @Override
    public void renderToolTip(MatrixStack matrixStack, int mouseX, int mouseY) {
        if (mouseX > this.x && mouseX < this.x + 19 && mouseY > this.y && mouseY < this.y + 202) {
            String descS = UtilLib.translate("text.werewolves.skill_screen.level_progression", (int) Math.ceil(WerewolfPlayer.get(Minecraft.getInstance().player).getLevelHandler().getLevelPerc() * 100));
            this.fillGradient(matrixStack, mouseX - 3, mouseY - 3, mouseX + Minecraft.getInstance().font.width(descS) + 3, mouseY + 12 + 3, -1073741824, -1073741824);
            Minecraft.getInstance().font.draw(matrixStack, descS, mouseX, mouseY, 0xff808080);
        }
    }
}
