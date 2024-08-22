package de.teamlapen.werewolves.network;

import de.teamlapen.werewolves.client.ClientPayloadHandler;
import de.teamlapen.werewolves.server.ServerPayloadHandler;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPacketDispatcher {

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    @SubscribeEvent
    public static void registerHandler(RegisterPayloadHandlersEvent event) {
        registerPackets(event.registrar(REFERENCE.MODID).versioned(PROTOCOL_VERSION));
    }

    @SuppressWarnings("Convert2MethodRef")
    public static void registerPackets(PayloadRegistrar registrar) {
        registrar.playToClient(ClientboundAttackTargetEventPacket.TYPE, ClientboundAttackTargetEventPacket.CODEC, (msg, l) -> ClientPayloadHandler.handleAttackTargetEventPacket(msg, l));
        registrar.playToServer(ServerboundWerewolfAppearancePacket.TYPE, ServerboundWerewolfAppearancePacket.CODEC, (msg, l) -> ServerPayloadHandler.handleWerewolfAppearancePacket(msg, l));
        registrar.playToServer(ServerboundSimpleInputEventPacket.TYPE, ServerboundSimpleInputEventPacket.CODEC, (msg, l) -> ServerPayloadHandler.handleSimpleInputEventPacket(msg, l));
        registrar.playToServer(ServerboundBiteEventPackage.TYPE, ServerboundBiteEventPackage.CODEC, (msg, l) -> ServerPayloadHandler.handleBiteEventPacket(msg, l));
    }
}
