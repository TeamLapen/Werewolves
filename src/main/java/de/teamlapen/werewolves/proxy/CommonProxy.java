package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.WerewolfAppearancePacket;
import de.teamlapen.werewolves.world.ModWorldEventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
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
    public void handleAppearancePacket(ServerPlayerEntity sender, WerewolfAppearancePacket msg) {
        Entity entity = sender.level.getEntity(msg.entityId);
        if (entity instanceof PlayerEntity) {
            WerewolfPlayer.getOpt(((PlayerEntity) entity)).ifPresent(werewolf -> {
                werewolf.setSkinData(msg.form, msg.data);
            });
        }
    }
}
