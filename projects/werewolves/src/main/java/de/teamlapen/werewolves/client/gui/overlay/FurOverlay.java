package de.teamlapen.werewolves.client.gui.overlay;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.CameraType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FurOverlay implements LayeredDraw.Layer {
    private final ResourceLocation FUR = WIdentifier.mod("textures/gui/overlay/werewolf_fur_border.png");
    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, @NotNull DeltaTracker pDeltaTracker) {
        if (this.mc.player != null && !ModConfig.CLIENT.disableScreenFurRendering.get()) {
            if (this.mc.options.getCameraType() == CameraType.FIRST_PERSON && Helper.isWerewolf(this.mc.player) && FormHelper.isFormActionActive(WerewolfPlayer.get(this.mc.player))) {
                pGuiGraphics.blit(FUR, 0, 0, 0, 0, this.mc.getWindow().getScreenWidth(), this.mc.getWindow().getScreenHeight(), this.mc.getWindow().getGuiScaledWidth(), this.mc.getWindow().getGuiScaledHeight());
            }
        }
    }
}
