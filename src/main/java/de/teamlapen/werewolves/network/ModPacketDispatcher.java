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
        this.dispatcher.registerMessage(nextID(), InputEventPacket.class, InputEventPacket::encode, InputEventPacket::decode, InputEventPacket::handle);
        this.dispatcher.registerMessage(nextID(), AttackTargetEventPacket.class, AttackTargetEventPacket::encode, AttackTargetEventPacket::decode, AttackTargetEventPacket::handle);
        this.dispatcher.registerMessage(nextID(), WerewolfAppearancePacket.class, WerewolfAppearancePacket::encode, WerewolfAppearancePacket::decode, WerewolfAppearancePacket::handle);
    }
}
