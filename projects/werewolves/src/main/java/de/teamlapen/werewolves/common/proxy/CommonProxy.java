package de.teamlapen.werewolves.common.proxy;

import de.teamlapen.werewolves.common.world.ModWorldEventHandler;
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
