package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.vampirism.util.StreamCodecExtension;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.CodecExtensions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.IntStream;

public record ServerboundWerewolfAppearancePacket(int entityId, String name, WerewolfForm form, int... data) implements CustomPacketPayload {

    public static final Type<ServerboundWerewolfAppearancePacket> TYPE = new Type<>(WResourceLocation.mod("werewolf_appearance"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundWerewolfAppearancePacket> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ServerboundWerewolfAppearancePacket::entityId,
            ByteBufCodecs.STRING_UTF8, ServerboundWerewolfAppearancePacket::name,
            WerewolfForm.STREAM_CODEC, ServerboundWerewolfAppearancePacket::form,
            CodecExtensions.INT_ARRAY, ServerboundWerewolfAppearancePacket::data,
            ServerboundWerewolfAppearancePacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
