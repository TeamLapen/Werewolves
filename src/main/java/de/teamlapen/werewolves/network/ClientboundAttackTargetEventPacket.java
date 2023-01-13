package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.werewolves.WerewolvesMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ClientboundAttackTargetEventPacket(int entityId) implements IMessage.IClientBoundMessage {

    static void encode(ClientboundAttackTargetEventPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeVarInt(msg.entityId);
    }

    static ClientboundAttackTargetEventPacket decode(FriendlyByteBuf packetBuffer) {
        return new ClientboundAttackTargetEventPacket(packetBuffer.readVarInt());
    }

    static void handle(ClientboundAttackTargetEventPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> WerewolvesMod.proxy.handleAttackTargetEventPacket(msg));
        ctx.setPacketHandled(true);
    }
}
