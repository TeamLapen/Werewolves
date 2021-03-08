package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;

public interface Proxy extends IInitListener {

    default void handleAttackTargetEventPacket(AttackTargetEventPacket packet) {
    }

    default boolean isShiftDown() {
        return false;
    }

    default void toggleStepHeight(boolean activate) {
    }

}
