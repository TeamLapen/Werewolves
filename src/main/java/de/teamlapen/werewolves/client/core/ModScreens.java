package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.client.gui.overlay.ActionCooldownOverlay;
import de.teamlapen.werewolves.client.gui.overlay.FurOverlay;
import de.teamlapen.werewolves.client.gui.overlay.WerewolfFormDurationOverlay;
import de.teamlapen.werewolves.core.ModContainer;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;
import org.jetbrains.annotations.ApiStatus;

public class ModScreens {

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModContainer.STONE_ALTAR.get(), StoneAltarScreen::new);
    }

    @ApiStatus.Internal
    public static void registerScreenOverlays(RegisterGuiOverlaysEvent event) {
        event.registerBelowAll(new ResourceLocation(REFERENCE.MODID, "action_cooldown"), new ActionCooldownOverlay());
        event.registerBelowAll(new ResourceLocation(REFERENCE.MODID, "fur_border"), new FurOverlay());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), new ResourceLocation(REFERENCE.MODID, "form_duration"), new WerewolfFormDurationOverlay());
    }
}
