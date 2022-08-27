package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.werewolves.WerewolvesMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record ClientboundAttackTargetEventPacket(int entityId) implements IMessage {

    static void encode(@NotNull ClientboundAttackTargetEventPacket msg, @NotNull FriendlyByteBuf packetBuffer) {
        packetBuffer.writeVarInt(msg.entityId);
    }

    static @NotNull ClientboundAttackTargetEventPacket decode(@NotNull FriendlyByteBuf packetBuffer) {
        return new ClientboundAttackTargetEventPacket(packetBuffer.readVarInt());
    }

    static void handle(ClientboundAttackTargetEventPacket msg, @NotNull Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> WerewolvesMod.proxy.handleAttackTargetEventPacket(msg));
        ctx.setPacketHandled(true);
    }
}
