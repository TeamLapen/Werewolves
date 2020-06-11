package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.client.core.ClientEventHandler;
import de.teamlapen.werewolves.client.core.WEntityRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

public class ClientProxy extends CommonProxy{

    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {
        super.onInitStep(step, event);
        switch (step) {
            case CLIENT_SETUP:
                WEntityRenderer.registerEntityRenderer();
                MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
                break;
        }
    }
}
