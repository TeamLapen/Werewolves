package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import net.minecraft.server.level.ServerPlayer;

public interface Proxy extends IInitListener {

    default void handleAttackTargetEventPacket(AttackTargetEventPacket packet) {
    }

    default void handleAppearancePacket(ServerPlayer sender, WerewolfAppearancePacket msg) {

    }
}
