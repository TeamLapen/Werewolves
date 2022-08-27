package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.client.gui.overlay.ActionCooldownOverlay;
import de.teamlapen.werewolves.client.gui.overlay.FurOverlay;
import de.teamlapen.werewolves.client.gui.overlay.WerewolfFormDurationOverlay;
import de.teamlapen.werewolves.core.ModContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ModScreens {

    public static void registerScreensUnsafe() {
        MenuScreens.register(ModContainer.STONE_ALTAR.get(), StoneAltarScreen::new);
    }

    static void registerScreenOverlays(@NotNull RegisterGuiOverlaysEvent event) {
        event.registerBelowAll("action_cooldown", new ActionCooldownOverlay());
        event.registerBelowAll("fur_border", new FurOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "form_duration", new WerewolfFormDurationOverlay());
    }
}
