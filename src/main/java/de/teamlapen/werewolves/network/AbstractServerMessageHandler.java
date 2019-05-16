package de.teamlapen.werewolves.network;

import de.teamlapen.lib.lib.network.AbstractMessageHandler;
import de.teamlapen.lib.lib.network.AbstractPacketDispatcher;
import de.teamlapen.werewolves.WerewolvesMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class AbstractServerMessageHandler<T extends IMessage> extends AbstractMessageHandler<T> {

    @Override
    public IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx) {
        return null;
    }

    @Override
    protected AbstractPacketDispatcher getDispatcher() {
        return WerewolvesMod.dispatcher;
    }
}
