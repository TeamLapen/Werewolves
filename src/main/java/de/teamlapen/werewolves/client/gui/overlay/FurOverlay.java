package de.teamlapen.werewolves.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.NotNull;

public class FurOverlay extends GuiComponent implements IGuiOverlay {
    private final ResourceLocation FUR = new ResourceLocation(REFERENCE.MODID, "textures/gui/overlay/werewolf_fur_border.png");
    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(ForgeGui gui, @NotNull PoseStack mStack, float partialTicks, int width, int height) {
        if (WerewolvesConfig.CLIENT.disableScreenFurRendering.get()) return;
        if (this.mc.options.getCameraType() == CameraType.FIRST_PERSON && Helper.isWerewolf(this.mc.player) && WerewolfPlayer.getOpt(this.mc.player).map(FormHelper::isFormActionActive).orElse(false)) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, FUR);
            RenderSystem.enableBlend();
            blit(mStack, 0, 0, this.getBlitOffset(), 0, 0, this.mc.getWindow().getScreenWidth(), this.mc.getWindow().getScreenHeight(), this.mc.getWindow().getGuiScaledWidth(), this.mc.getWindow().getGuiScaledHeight());
            RenderSystem.disableBlend();
        }
    }
}
