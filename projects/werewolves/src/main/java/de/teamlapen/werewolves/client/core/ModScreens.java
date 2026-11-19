package de.teamlapen.werewolves.client.core;

import de.teamlapen.faction.api.util.FIdentifier;
import de.teamlapen.faction.client.core.FactionAppearanceScreens;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.client.gui.WerewolfPlayerAppearanceScreen;
import de.teamlapen.werewolves.client.gui.overlay.FurOverlay;
import de.teamlapen.werewolves.client.gui.overlay.WerewolfFormDurationOverlay;
import de.teamlapen.werewolves.common.core.ModContainer;
import de.teamlapen.werewolves.common.core.ModFactions;
import net.minecraft.client.gui.components.WidgetSprites;
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
        event.registerBelowAll(WIdentifier.mod("fur_border"), new FurOverlay());
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_LEVEL, WIdentifier.mod("form_duration"), new WerewolfFormDurationOverlay());
    }

    public static void registerAppearanceScreens(FactionAppearanceScreens.RegisterFactionAppearanceScreensEvent event) {
        event.register(ModFactions.WEREWOLF, WerewolfPlayerAppearanceScreen::new, new WidgetSprites(FIdentifier.mod("widget/appearance"), FIdentifier.mod("widget/appearance_highlighted")));
    }
}
