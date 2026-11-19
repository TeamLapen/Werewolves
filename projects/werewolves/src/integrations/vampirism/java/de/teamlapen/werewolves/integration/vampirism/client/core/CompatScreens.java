package de.teamlapen.werewolves.integration.vampirism.client.core;

import de.teamlapen.vampirism.client.gui.screens.*;
import de.teamlapen.vampirism.client.gui.screens.diffuser.FogDiffuserScreen;
import de.teamlapen.vampirism.client.gui.screens.diffuser.GarlicDiffuserScreen;
import de.teamlapen.vampirism.common.core.ModMenus;
import de.teamlapen.werewolves.integration.vampirism.client.gui.screen.WolfsbaneDiffuserScreen;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatMenus;
import de.teamlapen.werewolves.integration.vampirism.common.world.blocks.WolfsbaneDiffuserBlock;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class CompatScreens {

    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(CompatMenus.WOLFSBANE_DIFFUSER.get(), WolfsbaneDiffuserScreen::new);
    }
}
