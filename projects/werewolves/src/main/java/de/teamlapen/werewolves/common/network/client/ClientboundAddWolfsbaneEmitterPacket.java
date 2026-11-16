package de.teamlapen.werewolves.common.network.client;

import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.attachments.LevelWolfsbane;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ClientboundAddWolfsbaneEmitterPacket(LevelWolfsbane.Emitter emitter) implements CustomPacketPayload {

    public static final Type<ClientboundAddWolfsbaneEmitterPacket> TYPE = new Type<>(WIdentifier.mod("add_wolfsbane_emitter"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundAddWolfsbaneEmitterPacket> CODEC = StreamCodec.composite(
            LevelWolfsbane.Emitter.STREAM_CODEC, ClientboundAddWolfsbaneEmitterPacket::emitter,
            ClientboundAddWolfsbaneEmitterPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
