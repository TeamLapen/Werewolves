package de.teamlapen.werewolves.network;

import de.teamlapen.lib.network.IMessage;
import de.teamlapen.vampirism.entity.minion.management.MinionData;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WerewolfAppearancePacket implements IMessage {

    static void encode(WerewolfAppearancePacket msg, PacketBuffer buf) {
        buf.writeVarInt(msg.entityId);
        buf.writeUtf(msg.name);
        buf.writeUtf(msg.form.getName());
        buf.writeVarInt(msg.data.length);
        for (int value : msg.data) {
            buf.writeVarInt(value);
        }
    }

    static WerewolfAppearancePacket decode(PacketBuffer buf) {
        int entityId = buf.readVarInt();
        String newName = buf.readUtf(MinionData.MAX_NAME_LENGTH);
        String form = buf.readUtf(32767);
        int[] data = new int[buf.readVarInt()];
        for (int i = 0; i < data.length; i++) {
            data[i] = buf.readVarInt();
        }
        return new WerewolfAppearancePacket(entityId, newName, WerewolfForm.getForm(form), data);
    }

    public static void handle(final WerewolfAppearancePacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        final NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> WerewolvesMod.proxy.handleAppearancePacket(ctx.getSender(), msg));
        ctx.setPacketHandled(true);
    }

    public final int entityId;
    public final String name;
    public final WerewolfForm form;
    public final int[] data;

    public WerewolfAppearancePacket(int entityId, String name, WerewolfForm form, int... data) {
        this.entityId = entityId;
        this.name = name;
        this.form = form;
        this.data = data;
    }
}
