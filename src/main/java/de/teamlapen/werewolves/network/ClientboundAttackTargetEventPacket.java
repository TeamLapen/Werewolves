package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.api.WResourceLocation;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ClientboundAttackTargetEventPacket(int entityId) implements CustomPacketPayload {

    public static final Type<ClientboundAttackTargetEventPacket> TYPE = new Type<>(WResourceLocation.mod("attack_target_event"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClientboundAttackTargetEventPacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ClientboundAttackTargetEventPacket::entityId,
            ClientboundAttackTargetEventPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
