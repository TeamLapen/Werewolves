package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.client.core.ClientEventHandler;
import de.teamlapen.werewolves.client.core.WEntityRenderer;
import de.teamlapen.werewolves.client.core.WItemRenderer;
import de.teamlapen.werewolves.client.core.WerewolvesHUDOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy{

    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {
        super.onInitStep(step, event);
        switch (step) {
            case CLIENT_SETUP:
                WEntityRenderer.registerEntityRenderer();
                MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
                MinecraftForge.EVENT_BUS.register(new WerewolvesHUDOverlay());
                break;
            case LOAD_COMPLETE:
                WItemRenderer.registerColors();
                break;
        }
    }
}
