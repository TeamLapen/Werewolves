package de.teamlapen.werewolves.common.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.common.world.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.common.world.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.data.provider.ModRecipeProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface Proxy {

    default void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {

    }

    default void displayWerewolfMinionAppearanceScreen(WerewolfMinionEntity entity, Runnable back) {

    }

    default void displayWerewolfStatsScreen(WerewolfMinionEntity entity, Runnable back) {

    }

    default void sendToServer(CustomPacketPayload packetPayload) {

    }
}
