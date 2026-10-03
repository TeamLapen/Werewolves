package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.entities.player.werewolf.WerewolfPlayer;
import de.teamlapen.werewolves.network.ServerboundWerewolfAppearancePacket;
import de.teamlapen.werewolves.world.ModWorldEventHandler;
import net.neoforged.fml.event.lifecycle.ParallelDispatchEvent;
import net.neoforged.neoforge.common.NeoForge;

public class CommonProxy implements Proxy {

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        if (step == Step.COMMON_SETUP) {
            NeoForge.EVENT_BUS.register(new ModWorldEventHandler());
        }
    }
}
