package de.teamlapen.werewolves.network;

import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraftforge.fml.relauncher.Side;

/**
 * Werewolves packet dispatcher
 */
public class ModPacketDispatcher extends AbstractPacketDispatcher {

    public ModPacketDispatcher() {
        super(REFERENCE.MODID);
    }

    @Override
    public void registerPackets() {
        this.registerMessage(InputEventPacket.Handler.class, InputEventPacket.class, Side.SERVER);
    }
}
