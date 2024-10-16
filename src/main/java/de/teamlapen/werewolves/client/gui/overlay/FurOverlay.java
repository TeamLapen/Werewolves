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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import net.neoforged.neoforge.client.gui.overlay.IGuiOverlay;
import org.jetbrains.annotations.NotNull;

public class FurOverlay implements IGuiOverlay {
    private final ResourceLocation FUR = new ResourceLocation(REFERENCE.MODID, "textures/gui/overlay/werewolf_fur_border.png");
    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(@NotNull ExtendedGui gui, @NotNull GuiGraphics graphics, float partialTicks, int width, int height) {
        if (WerewolvesConfig.CLIENT.disableScreenFurRendering.get()) return;
        if (this.mc.options.getCameraType() == CameraType.FIRST_PERSON && Helper.isWerewolf(this.mc.player) && FormHelper.isFormActionActive(WerewolfPlayer.get(this.mc.player))) {
            graphics.blit(FUR, 0, 0, 0, 0, this.mc.getWindow().getScreenWidth(), this.mc.getWindow().getScreenHeight(), this.mc.getWindow().getGuiScaledWidth(), this.mc.getWindow().getGuiScaledHeight());
        }
    }
}
