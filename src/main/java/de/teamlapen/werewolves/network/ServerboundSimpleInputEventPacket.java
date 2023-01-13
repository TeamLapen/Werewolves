package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.vampirism.entity.player.actions.ActionHandler;
import de.teamlapen.werewolves.core.ModActions;
import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public record ServerboundSimpleInputEventPacket(Type type) implements IMessage.IServerBoundMessage {

    private static final Logger LOGGER = LogManager.getLogger();

    static void encode(ServerboundSimpleInputEventPacket msg, FriendlyByteBuf buf) {
        buf.writeEnum(msg.type);
    }

    static ServerboundSimpleInputEventPacket decode(FriendlyByteBuf buf) {
        return new ServerboundSimpleInputEventPacket(buf.readEnum(Type.class));
    }

    public static void handle(final ServerboundSimpleInputEventPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ServerPlayer player = ctx.getSender();
        Validate.notNull(player);
        ctx.enqueueWork(() -> {
            if (msg.type == Type.LEAP) {
                WerewolfPlayer.getOpt(player).ifPresent(werewolfPlayer -> {
                    werewolfPlayer.getActionHandler().toggleAction(ModActions.LEAP.get(), new ActionHandler.ActivationContext());
                });
            }
            ctx.setPacketHandled(true);
        });
    }

    public enum Type {
        LEAP
    }
}
