package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public record ServerboundWerewolfAppearancePacket(int entityId, String name, WerewolfForm form, int... data) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "werewolf_appearance");
    public static final Codec<ServerboundWerewolfAppearancePacket> CODEC = RecordCodecBuilder.create(inst -> {
        return inst.group(
                Codec.INT.fieldOf("entityId").forGetter(ServerboundWerewolfAppearancePacket::entityId),
                Codec.STRING.fieldOf("name").forGetter(ServerboundWerewolfAppearancePacket::name),
                WerewolfForm.CODEC.fieldOf("form").forGetter(msg -> msg.form),
                Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("data").forGetter(l -> l.data)
        ).apply(inst, ServerboundWerewolfAppearancePacket::new);
    });

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeJsonWithCodec(CODEC, this);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
