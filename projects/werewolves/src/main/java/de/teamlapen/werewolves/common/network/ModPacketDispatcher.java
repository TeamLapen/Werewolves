package de.teamlapen.werewolves.common.network;

import de.teamlapen.vampirism.common.network.packets.client.ClientboundAddGarlicEmitterPacket;
import de.teamlapen.vampirism.common.network.packets.client.ClientboundRemoveGarlicEmitterPacket;
import de.teamlapen.vampirism.common.network.packets.client.ClientboundUpdateGarlicEmitterPacket;
import de.teamlapen.werewolves.client.ClientPayloadHandler;
import de.teamlapen.werewolves.common.network.client.ClientboundAddWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.network.client.ClientboundRemoveWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.network.client.ClientboundUpdateWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.server.ServerPayloadHandler;
import de.teamlapen.werewolves.util.REFERENCE;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPacketDispatcher {

    private static final String PROTOCOL_VERSION = String.format("%s/%s", REFERENCE.MODID, 1);

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

        registrar.playToClient(ClientboundAddWolfsbaneEmitterPacket.TYPE, ClientboundAddWolfsbaneEmitterPacket.CODEC, (msg, context) -> ClientPayloadHandler.handleAddWolfsbaneEmitterPacket(msg, context));
        registrar.playToClient(ClientboundRemoveWolfsbaneEmitterPacket.TYPE, ClientboundRemoveWolfsbaneEmitterPacket.CODEC, (msg, context) -> ClientPayloadHandler.handleRemoveWolfsbaneEmitterPacket(msg, context));
        registrar.playToClient(ClientboundUpdateWolfsbaneEmitterPacket.TYPE, ClientboundUpdateWolfsbaneEmitterPacket.CODEC, (msg, context) -> ClientPayloadHandler.handleUpdateWolfbaneEmitterPacket(msg, context));


    }
}
