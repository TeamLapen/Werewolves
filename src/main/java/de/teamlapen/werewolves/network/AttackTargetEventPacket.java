package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.werewolves.WerewolvesMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record AttackTargetEventPacket(int entityId) implements IMessage {

    static void encode(AttackTargetEventPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeVarInt(msg.entityId);
    }

    static AttackTargetEventPacket decode(FriendlyByteBuf packetBuffer) {
        return new AttackTargetEventPacket(packetBuffer.readVarInt());
    }

    static void handle(AttackTargetEventPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> WerewolvesMod.proxy.handleAttackTargetEventPacket(msg));
        ctx.setPacketHandled(true);
    }
}
