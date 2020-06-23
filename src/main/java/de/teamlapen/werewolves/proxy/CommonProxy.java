package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.world.WWorldEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

public class CommonProxy implements Proxy {
    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {
        switch (step) {
            case COMMON_SETUP:
                MinecraftForge.EVENT_BUS.register(new WWorldEventHandler());
                break;
        }
    }
}
