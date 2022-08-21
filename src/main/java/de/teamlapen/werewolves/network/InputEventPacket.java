package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class InputEventPacket implements IMessage {

    public static final String BITE = "bt";
    public static final String LEAP = "lp";

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SPLIT = "&";
    private String param;
    private String action;

    public InputEventPacket(String action, String param) {
        this.action = action;
        this.param = param;
    }

    private InputEventPacket() {

    }

    static void encode(InputEventPacket msg, FriendlyByteBuf buf) {
        buf.writeUtf(msg.action + SPLIT + msg.param);
    }

    static InputEventPacket decode(FriendlyByteBuf buf) {
        String[] s = buf.readUtf(50).split(SPLIT);
        InputEventPacket msg = new InputEventPacket();
        msg.action = s[0];
        if (s.length > 1) {
            msg.param = s[1];
        } else {
            msg.param = "";
        }
        return msg;
    }

    public static void handle(final InputEventPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        Validate.notNull(msg.action);
        ServerPlayer player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(() -> {
            if (BITE.equals(msg.action)) {
                try {
                    int id = Integer.parseInt(msg.param);
                    if (id != 0) {
                        WerewolfPlayer.getOpt(player).ifPresent(werewolfPlayer -> werewolfPlayer.bite(id));
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("Receiving invalid param {} for {}", msg.param, msg.action);
                }
            } else if (LEAP.equals(msg.action)) {
                WerewolfPlayer.getOpt(player).ifPresent(werewolfPlayer -> {
                    werewolfPlayer.getActionHandler().toggleAction(ModActions.LEAP.get());
                });
            }
            ctx.setPacketHandled(true);
        });
    }
}
