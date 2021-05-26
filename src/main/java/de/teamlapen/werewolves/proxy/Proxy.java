package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import net.minecraft.entity.player.ServerPlayerEntity;

public interface Proxy extends IInitListener {

    default void handleAttackTargetEventPacket(AttackTargetEventPacket packet) {
    }

    default void handleAppearancePacket(ServerPlayerEntity sender, WerewolfAppearancePacket msg) {

    }
}
