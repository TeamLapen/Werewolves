package de.teamlapen.werewolves.network;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.client.ClientPayloadHandler;
import de.teamlapen.werewolves.server.ServerPayloadHandler;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class ModPacketDispatcher {

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    @SubscribeEvent
    public static void registerHandler(RegisterPayloadHandlerEvent event) {
        registerPackets(event.registrar(REFERENCE.MODID).versioned(PROTOCOL_VERSION));
    }

    @SuppressWarnings("Convert2MethodRef")
    public static void registerPackets(IPayloadRegistrar registrar) {
        registrar.play(ClientboundAttackTargetEventPacket.ID, jsonReader(ClientboundAttackTargetEventPacket.CODEC), handler -> handler.client((x,v) -> ClientPayloadHandler.handleAttackTargetEventPacket(x, v)));
        registrar.play(ServerboundWerewolfAppearancePacket.ID, jsonReader(ServerboundWerewolfAppearancePacket.CODEC), handler -> handler.server(ServerPayloadHandler::handleWerewolfAppearancePacket));
        registrar.play(ServerboundSimpleInputEventPacket.ID, jsonReader(ServerboundSimpleInputEventPacket.CODEC), handler -> handler.server(ServerPayloadHandler::handleSimpleInputEventPacket));
        registrar.play(ServerboundBiteEventPackage.ID, jsonReader(ServerboundBiteEventPackage.CODEC), handler -> handler.server(ServerPayloadHandler::handleBiteEventPacket));
    }

    protected static <T> FriendlyByteBuf.Reader<T> jsonReader(Codec<T> codec) {
        return buf -> buf.readJsonWithCodec(codec);
    }
}
