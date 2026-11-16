package de.teamlapen.werewolves.common.network.client;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.attachments.LevelWolfsbane;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ClientboundUpdateWolfsbaneEmitterPacket(List<LevelWolfsbane.Emitter> emitters) implements CustomPacketPayload {
    public static final Type<ClientboundUpdateWolfsbaneEmitterPacket> TYPE = new Type<>(WIdentifier.mod("update_wolfsbane_emitter"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundUpdateWolfsbaneEmitterPacket> CODEC = StreamCodec.composite(
            LevelWolfsbane.Emitter.STREAM_CODEC.apply(ByteBufCodecs.list()), ClientboundUpdateWolfsbaneEmitterPacket::emitters,
            ClientboundUpdateWolfsbaneEmitterPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
