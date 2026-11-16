package de.teamlapen.werewolves.client.gui;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.inventory.container.StoneAltarContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class StoneAltarScreen extends AbstractContainerScreen<StoneAltarContainer> {
    private static final ResourceLocation altarGuiTextures = WIdentifier.mod("textures/gui/stone_altar.png");


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
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        graphics.blit(altarGuiTextures, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}
