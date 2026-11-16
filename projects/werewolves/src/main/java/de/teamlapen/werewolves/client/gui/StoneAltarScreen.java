package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.inventory.container.StoneAltarContainer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class StoneAltarScreen extends AbstractContainerScreen<StoneAltarContainer> {
    private static final Identifier altarGuiTextures = WIdentifier.mod("textures/gui/stone_altar.png");


    public StoneAltarScreen(StoneAltarContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new ExpBar(leftPos - 14, topPos));
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI, altarGuiTextures, k, l, 0, 0, this.imageWidth, this.imageHeight,256,256);
    }
}
