package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.lib.lib.client.gui.ProgressBar;
import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class WolfsbaneDiffuserScreen extends Screen {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(REFERENCE.MODID, "textures/gui/garlic_diffuser.png");
    protected final int xSize = 220;
    protected final int ySize = 114;
    private final WolfsbaneDiffuserBlockEntity tile;
    protected int guiLeft;
    protected int guiTop;
    protected ProgressBar startupBar;
    protected ProgressBar fueledTimer;

    public WolfsbaneDiffuserScreen(WolfsbaneDiffuserBlockEntity tile, @NotNull Component title) {
        super(title);
        this.tile = tile;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.drawTitle(graphics);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int p_296369_, int p_296477_, float p_294317_) {
        super.renderBackground(guiGraphics, p_296369_, p_296477_, p_294317_);
        this.renderGuiBackground(guiGraphics);
    }

    private void updateProgress() {
        startupBar.setProgress(tile.getBootProgress());
        float f = tile.getFueledState();
        if (f == 0) {
            fueledTimer.active = false;
        } else {
            fueledTimer.active = true;
            fueledTimer.setProgress(f);
        }

    }

    @Override
    public void tick() {
        updateProgress();
    }

    @Override
    protected void init() {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;

        startupBar = this.addRenderableWidget(new ProgressBar(this.guiLeft + (xSize - 170) / 2, this.guiTop + 30, 170, Component.translatable("gui.vampirism.garlic_diffuser.startup")));
        startupBar.setColor(0xD0D0FF);
        startupBar.setFGColor(0xFFFFFF);

        fueledTimer = this.addRenderableWidget(new ProgressBar(this.guiLeft + (xSize - 170) / 2, this.guiTop + 60, 170, Component.translatable("gui.vampirism.garlic_diffuser.fueled")));
        fueledTimer.setColor(0xD0FFD0);
        fueledTimer.setFGColor(0xFFFFFF);

        updateProgress();
    }

    protected void renderGuiBackground(@NotNull GuiGraphics graphics) {
        graphics.blit(BACKGROUND, this.guiLeft, this.guiTop, 0, 0, 0, this.xSize, this.ySize, 256, 256);
    }

    private void drawTitle(@NotNull GuiGraphics graphics) {
        graphics.drawString(this.font, title, this.guiLeft + 15, this.guiTop + 5, 0xFFFFFFFF);
    }
}
