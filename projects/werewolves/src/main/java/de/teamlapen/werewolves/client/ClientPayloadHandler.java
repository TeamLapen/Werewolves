package de.teamlapen.werewolves.client;

import de.teamlapen.vampirism.common.network.packets.client.ClientboundAddGarlicEmitterPacket;
import de.teamlapen.vampirism.common.network.packets.client.ClientboundRemoveGarlicEmitterPacket;
import de.teamlapen.vampirism.common.network.packets.client.ClientboundUpdateGarlicEmitterPacket;
import de.teamlapen.vampirism.common.world.attachments.LevelGarlic;
import de.teamlapen.werewolves.common.network.ClientboundAttackTargetEventPacket;
import de.teamlapen.werewolves.common.network.client.ClientboundAddWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.network.client.ClientboundRemoveWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.network.client.ClientboundUpdateWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.world.attachments.LevelWolfsbane;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleAttackTargetEventPacket(ClientboundAttackTargetEventPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> WerewolvesModClient.getInstance().getModHUDOverlay().attackTriggered(msg.entityId()));
    }

    public static void handleAddWolfsbaneEmitterPacket(ClientboundAddWolfsbaneEmitterPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> LevelWolfsbane.get(context.player().level()).registerWolfsbaneBlock(msg.emitter().strength(), msg.emitter().pos()));
    }


    public static void handleRemoveWolfsbaneEmitterPacket(ClientboundRemoveWolfsbaneEmitterPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> LevelWolfsbane.get(context.player().level()).removeWolfsbaneBlock(msg.emitterId()));
    }

    public static void handleUpdateWolfbaneEmitterPacket(ClientboundUpdateWolfsbaneEmitterPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> LevelWolfsbane.get(context.player().level()).fill(msg.emitters()));
    }
}
