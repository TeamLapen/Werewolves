package de.teamlapen.werewolves.network;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.player.werewolf.WerewolfPlayer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

public class InputEventPacket implements IMessage {

    public static final String BITE = "bt";
    private final static String TAG = "InputEventPacket";
    private final String SPLIT = "&";
    private String param;
    private String action;

    /**
     * Don't use
     */
    public InputEventPacket() {

    }

    public InputEventPacket(String action, String param) {
        this.action = action;
        this.param = param;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        String[] s = ByteBufUtils.readUTF8String(buf).split(this.SPLIT);
        this.action = s[0];
        if (s.length > 1) {
            this.param = s[1];
        } else {
            this.param = "";
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.action + this.SPLIT + this.param);
    }

    public static class Handler extends AbstractServerMessageHandler<InputEventPacket> {

        @Override
        public IMessage handleServerMessage(EntityPlayer player, InputEventPacket message, MessageContext ctx) {
            if (message.action == null)
                return null;
            if (message.action.equals(BITE)) {
                int id = 0;
                try {
                    id = Integer.parseInt(message.param);
                } catch (NumberFormatException e) {
                    WerewolvesMod.log.e(TAG, "Receiving invalid param for %S", message.action);
                }
                if (id != 0) {
                    WerewolfPlayer.get(player).biteEntity(id);
                }
            }
            return null;
        }

        @Override
        protected boolean handleOnMainThread() {
            return true;
        }

    }

}
