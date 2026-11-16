package de.teamlapen.werewolves.common.network;

import de.teamlapen.vampirism.common.util.serialization.StreamCodecExtension;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.CodecExtensions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public record ServerboundWerewolfAppearancePacket(int entityId, String name, WerewolfForm form, int... data) implements CustomPacketPayload {

    public static final Type<ServerboundWerewolfAppearancePacket> TYPE = new Type<>(WIdentifier.mod("werewolf_appearance"));
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
