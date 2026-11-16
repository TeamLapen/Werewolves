package de.teamlapen.werewolves.client.gui.overlay;

import de.teamlapen.faction.client.gui.overlay.BaseOverlay;
import de.teamlapen.faction.client.gui.overlay.TextureOverlay;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.config.ModConfig;
import de.teamlapen.werewolves.common.world.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.util.FormHelper;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.client.CameraType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public class FurOverlay extends TextureOverlay {

    private final Identifier FUR = WIdentifier.mod("textures/gui/overlay/werewolf_fur_border.png");

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker deltaTracker) {
        if (canRenderOverlays() && !ModConfig.client().disableScreenFurRendering.getAsBoolean()) {
            if (mc().options.getCameraType() == CameraType.FIRST_PERSON && Helper.isWerewolf(player()) && FormHelper.isFormActionActive(WerewolfPlayer.get(player()))) {
                renderTextureOverlay(guiGraphics, FUR, 1);
            }
        }
    }
}
