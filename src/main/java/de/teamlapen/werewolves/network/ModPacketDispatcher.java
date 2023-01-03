package de.teamlapen.werewolves.network;

import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;

public class ModPacketDispatcher extends AbstractPacketDispatcher {

    private static final String PROTOCOL_VERSION = Integer.toString(1);

    public ModPacketDispatcher() {
        super(NetworkRegistry.ChannelBuilder.named(new ResourceLocation(REFERENCE.MODID, "main")).clientAcceptedVersions(PROTOCOL_VERSION::equals).serverAcceptedVersions(PROTOCOL_VERSION::equals).networkProtocolVersion(() -> PROTOCOL_VERSION).simpleChannel());
    }

    @Override
    public void registerPackets() {
        registerServerBound(ServerboundSimpleInputEventPacket.class, ServerboundSimpleInputEventPacket::encode, ServerboundSimpleInputEventPacket::decode, ServerboundSimpleInputEventPacket::handle);
        registerClientBound(ClientboundAttackTargetEventPacket.class, ClientboundAttackTargetEventPacket::encode, ClientboundAttackTargetEventPacket::decode, ClientboundAttackTargetEventPacket::handle);
        registerServerBound(ServerboundWerewolfAppearancePacket.class, ServerboundWerewolfAppearancePacket::encode, ServerboundWerewolfAppearancePacket::decode, ServerboundWerewolfAppearancePacket::handle);
        registerServerBound(ServerboundBiteEventPackage.class, ServerboundBiteEventPackage::encode, ServerboundBiteEventPackage::decode, ServerboundBiteEventPackage::handle);
    }
}
