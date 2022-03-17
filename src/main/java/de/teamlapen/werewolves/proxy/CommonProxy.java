package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import de.teamlapen.werewolves.world.ModWorldEventHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;

public class CommonProxy implements Proxy {

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        switch (step) {
            case COMMON_SETUP:
                MinecraftForge.EVENT_BUS.register(new ModWorldEventHandler());
                break;
        }
    }

    @Override
    public void handleAppearancePacket(ServerPlayer sender, WerewolfAppearancePacket msg) {
        Entity entity = sender.level.getEntity(msg.entityId);
        if (entity instanceof Player) {
            WerewolfPlayer.getOpt(((Player) entity)).ifPresent(werewolf -> {
                werewolf.setSkinData(msg.form, msg.data);
            });
        }
    }
}
