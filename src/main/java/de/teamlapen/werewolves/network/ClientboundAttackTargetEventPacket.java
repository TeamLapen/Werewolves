package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public record ClientboundAttackTargetEventPacket(int entityId) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "attack_target_event");
    public static final Codec<ClientboundAttackTargetEventPacket> CODEC = Codec.INT.xmap(ClientboundAttackTargetEventPacket::new, msg -> msg.entityId);

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeJsonWithCodec(CODEC, this);
    }

    @Override
    public @NotNull ResourceLocation id() {
        return ID;
    }
}
