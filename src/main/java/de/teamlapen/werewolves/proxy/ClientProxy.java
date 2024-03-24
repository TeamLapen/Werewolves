package de.teamlapen.werewolves.proxy;

import de.teamlapen.vampirism.client.gui.screens.HunterMinionAppearanceScreen;
import de.teamlapen.vampirism.client.gui.screens.HunterMinionStatsScreen;
import de.teamlapen.werewolves.blocks.LogBlock;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.client.gui.WerewolfMinionAppearanceScreen;
import de.teamlapen.werewolves.client.gui.WerewolfMinionStatsScreen;
import de.teamlapen.werewolves.client.gui.WolfsbaneDiffuserScreen;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.event.lifecycle.ParallelDispatchEvent;
import org.jetbrains.annotations.NotNull;

public class ClientProxy extends CommonProxy {

    @Override
    public void onInitStep(@NotNull Step step, @NotNull ParallelDispatchEvent event) {
        super.onInitStep(step, event);
        if (step == Step.CLIENT_SETUP) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(LogBlock.MAGIC);
                Sheets.addWoodType(LogBlock.JACARANDA);
            });
        }
    }

    @Override
    public void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {
        openScreen(new WolfsbaneDiffuserScreen(tile, title));
    }

    @Override
    public void displayWerewolfMinionAppearanceScreen(WerewolfMinionEntity entity) {
        openScreen(new WerewolfMinionAppearanceScreen(entity, Minecraft.getInstance().screen));
    }

    @Override
    public void displayWerewolfStatsScreen(WerewolfMinionEntity entity) {
        openScreen(new WerewolfMinionStatsScreen(entity, Minecraft.getInstance().screen));
    }

    public static void runOnRenderThread(Runnable runnable) {
        Minecraft.getInstance().execute(runnable);
    }

    public static void openScreen(Screen screen) {
        runOnRenderThread(() -> Minecraft.getInstance().setScreen(screen));
    }
}
