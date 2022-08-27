package de.teamlapen.werewolves.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.inventory.container.StoneAltarContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class StoneAltarScreen extends AbstractContainerScreen<StoneAltarContainer> {
    private static final ResourceLocation altarGuiTextures = new ResourceLocation(REFERENCE.MODID, "textures/gui/stone_altar.png");


    public StoneAltarScreen(@NotNull StoneAltarContainer screenContainer, @NotNull Inventory inv, @NotNull Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(@NotNull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, partialTicks);
        this.renderTooltip(stack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, altarGuiTextures);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        this.blit(stack, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }
}
