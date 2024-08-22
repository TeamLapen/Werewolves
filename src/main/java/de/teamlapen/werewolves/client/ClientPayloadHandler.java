package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleAttackTargetEventPacket(ClientboundAttackTargetEventPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> WerewolvesModClient.getInstance().getModHUDOverlay().attackTriggered(msg.entityId()));
    }
}
