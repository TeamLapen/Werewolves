package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import de.teamlapen.werewolves.network.ServerboundWerewolfAppearancePacket;
import net.minecraft.server.level.ServerPlayer;

public interface Proxy extends IInitListener {

    default void handleAttackTargetEventPacket(ClientboundAttackTargetEventPacket packet) {
    }

    default void handleAppearancePacket(ServerPlayer sender, ServerboundWerewolfAppearancePacket msg) {

    }
}
