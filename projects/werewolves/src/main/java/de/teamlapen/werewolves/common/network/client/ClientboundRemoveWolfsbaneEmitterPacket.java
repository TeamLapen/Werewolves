package de.teamlapen.werewolves.common.network.client;

import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ClientboundRemoveWolfsbaneEmitterPacket(int emitterId) implements CustomPacketPayload {

    public static final Type<ClientboundRemoveWolfsbaneEmitterPacket> TYPE = new Type<>(WIdentifier.mod("remove_wolfsbane_emitter"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundRemoveWolfsbaneEmitterPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundRemoveWolfsbaneEmitterPacket::emitterId,
            ClientboundRemoveWolfsbaneEmitterPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}