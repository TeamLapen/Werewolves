package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record ServerboundBiteEventPackage(int entityId) implements CustomPacketPayload {

    public static final Type<ServerboundBiteEventPackage> TYPE = new Type<>(WResourceLocation.mod("bite_event"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundBiteEventPackage> CODEC = StreamCodec.composite(ByteBufCodecs.INT, ServerboundBiteEventPackage::entityId, ServerboundBiteEventPackage::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
