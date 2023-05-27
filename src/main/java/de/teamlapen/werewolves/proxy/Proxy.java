package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import de.teamlapen.werewolves.network.ServerboundWerewolfAppearancePacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public interface Proxy extends IInitListener {

    default void handleAttackTargetEventPacket(ClientboundAttackTargetEventPacket packet) {
    }

    default void handleAppearancePacket(ServerPlayer sender, ServerboundWerewolfAppearancePacket msg) {

    }

    default void endVisionBatch() {

    }

    default void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {

    }
}
