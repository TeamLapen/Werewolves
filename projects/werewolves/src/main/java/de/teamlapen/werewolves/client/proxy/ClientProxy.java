package de.teamlapen.werewolves.client.proxy;

import de.teamlapen.faction.client.gui.screens.MinionStatsScreen;
import de.teamlapen.werewolves.common.proxy.CommonProxy;
import de.teamlapen.werewolves.common.world.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.client.gui.WerewolfMinionAppearanceScreen;
import de.teamlapen.werewolves.client.gui.WolfsbaneDiffuserScreen;
import de.teamlapen.werewolves.common.world.entities.minion.WerewolfMinionEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class ClientProxy extends CommonProxy {

    @Override
    public void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {
        openScreen(new WolfsbaneDiffuserScreen(tile, title));
    }

    @Override
    public void displayWerewolfMinionAppearanceScreen(WerewolfMinionEntity entity, Runnable back) {
        openScreen(new WerewolfMinionAppearanceScreen(entity, back::run));
    }

    @Override
    public void displayWerewolfStatsScreen(WerewolfMinionEntity entity, Runnable back) {
        openScreen(new MinionStatsScreen<>(entity, back::run));
    }

    public static void runOnRenderThread(Runnable runnable) {
        Minecraft.getInstance().execute(runnable);
    }

    public static void openScreen(Screen screen) {
        runOnRenderThread(() -> Minecraft.getInstance().setScreen(screen));
    }

    @Override
    public void sendToServer(CustomPacketPayload packetPayload) {
        //noinspection DataFlowIssue
        Minecraft.getInstance().getConnection().send(packetPayload);
    }
}
