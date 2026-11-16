package de.teamlapen.werewolves.common.world.attachments;

import de.teamlapen.vampirism.common.network.packets.client.ClientboundAddGarlicEmitterPacket;
import de.teamlapen.vampirism.common.network.packets.client.ClientboundRemoveGarlicEmitterPacket;
import de.teamlapen.vampirism.common.network.packets.client.ClientboundUpdateGarlicEmitterPacket;
import de.teamlapen.vampirism.common.world.attachments.LevelGarlic;
import de.teamlapen.werewolves.common.network.client.ClientboundAddWolfsbaneEmitterPacket;
import de.teamlapen.werewolves.common.network.client.ClientboundUpdateWolfsbaneEmitterPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;

public class ServerLevelWolfsbane extends LevelWolfsbane{

    private final ServerLevel serverLevel;

    public ServerLevelWolfsbane(ServerLevel serverLevel) {
        this.serverLevel = serverLevel;
    }

    @Override
    protected void notifyChange(LevelWolfsbane.Emitter emitter) {
        ClientboundAddWolfsbaneEmitterPacket packet = new ClientboundAddWolfsbaneEmitterPacket(emitter);
        for (ServerPlayer player : this.serverLevel.getLevel().players()) {
            player.connection.send(packet);
        }
    }

    @Override
    protected void notifyRemove(int emitter) {
        ClientboundRemoveGarlicEmitterPacket packet = new ClientboundRemoveGarlicEmitterPacket(emitter);
        for (ServerPlayer player : this.serverLevel.getLevel().players()) {
            player.connection.send(packet);
        }
    }

    @Override
    protected void notifyClear() {
        ClientboundUpdateWolfsbaneEmitterPacket packet = new ClientboundUpdateWolfsbaneEmitterPacket(new ArrayList<>(emitterHashMap.values()));
        for (ServerPlayer player : this.serverLevel.getLevel().players()) {
            player.connection.send(packet);
        }
    }

    @Override
    public void updatePlayer(ServerPlayer player) {
        player.connection.send(new ClientboundUpdateWolfsbaneEmitterPacket(new ArrayList<>(emitterHashMap.values())));
    }
}
