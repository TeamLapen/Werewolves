package de.teamlapen.werewolves.client;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class ClientPayloadHandler {

    public static void handleAttackTargetEventPacket(ClientboundAttackTargetEventPacket msg, PlayPayloadContext context) {
        context.workHandler().execute(() -> WerewolvesModClient.getInstance().getModHUDOverlay().attackTriggered(msg.entityId()));
    }
}
