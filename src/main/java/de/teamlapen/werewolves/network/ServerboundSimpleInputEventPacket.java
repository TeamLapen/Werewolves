package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

public record ServerboundSimpleInputEventPacket(Action action) implements CustomPacketPayload {

    public static final Type<ServerboundSimpleInputEventPacket> TYPE = new Type<>(WResourceLocation.mod("simple_input_event"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundSimpleInputEventPacket> CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(Action.class), ServerboundSimpleInputEventPacket::action,
            ServerboundSimpleInputEventPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Action implements StringRepresentable {
        LEAP("leap");

        private final String name;

        Action(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
