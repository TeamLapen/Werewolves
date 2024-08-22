package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.client.gui.overlay.FurOverlay;
import de.teamlapen.werewolves.client.gui.overlay.WerewolfFormDurationOverlay;
import de.teamlapen.werewolves.core.ModContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import org.jetbrains.annotations.ApiStatus;

public class ModScreens {

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModContainer.STONE_ALTAR.get(), StoneAltarScreen::new);
    }

    @ApiStatus.Internal
    public static void registerScreenOverlays(RegisterGuiLayersEvent event) {
        event.registerBelowAll(WResourceLocation.mod("fur_border"), new FurOverlay());
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_BAR, WResourceLocation.mod("form_duration"), new WerewolfFormDurationOverlay());
    }
}
