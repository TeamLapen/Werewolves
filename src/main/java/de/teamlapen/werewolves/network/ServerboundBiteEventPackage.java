package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ServerboundBiteEventPackage(int entityId) implements IMessage.IServerBoundMessage {

    static void encode(ServerboundBiteEventPackage msg, FriendlyByteBuf buf) {
        buf.writeVarInt(msg.entityId);
    }

    static ServerboundBiteEventPackage decode(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        return new ServerboundBiteEventPackage(entityId);
    }

    public static void handle(final ServerboundBiteEventPackage msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            if (ctx.getSender() != null) {
                WerewolfPlayer.getOpt(ctx.getSender()).ifPresent(werewolfPlayer -> werewolfPlayer.bite(msg.entityId));
            }
            ctx.setPacketHandled(true);
        });
    }
}
