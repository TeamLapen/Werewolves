package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.gui.StoneAltarScreen;
import de.teamlapen.werewolves.core.ModContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModScreens {

    public static void registerScreens() {
        ScreenManager.registerFactory(ModContainer.stone_altar_container, StoneAltarScreen::new);
    }
}
