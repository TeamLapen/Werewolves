package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import net.minecraftforge.client.event.TextureStitchEvent;

public interface Proxy extends IInitListener {

    default void handleAttackTargetEventPacket(AttackTargetEventPacket packet) {
    }

    default void onTextureStitchEvent(TextureStitchEvent.Pre event){

    }
}
