package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.werewolves.WerewolvesMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AttackTargetEventPacket implements IMessage {

    public final int entityId;

    public AttackTargetEventPacket(int entityId) {
        this.entityId = entityId;
    }

    static void encode(AttackTargetEventPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeVarInt(msg.entityId);
    }

    static AttackTargetEventPacket decode(PacketBuffer packetBuffer) {
        return new AttackTargetEventPacket(packetBuffer.readVarInt());
    }

    static void handle(AttackTargetEventPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> WerewolvesMod.proxy.handleAttackTargetEventPacket(msg));
        ctx.setPacketHandled(true);
    }
}
