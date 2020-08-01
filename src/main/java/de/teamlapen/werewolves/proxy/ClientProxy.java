package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.client.core.ClientEventHandler;
import de.teamlapen.werewolves.client.core.WEntityRenderer;
import de.teamlapen.werewolves.client.core.WItemRenderer;
import de.teamlapen.werewolves.client.core.WerewolvesHUDOverlay;
import de.teamlapen.werewolves.client.render.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    private RenderHandler renderHandler;

    @Override
    public void onInitStep(Step step, ModLifecycleEvent event) {
        super.onInitStep(step, event);
        switch (step) {
            case CLIENT_SETUP:
                WEntityRenderer.registerEntityRenderer();
                MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
                MinecraftForge.EVENT_BUS.register(new WerewolvesHUDOverlay());
                MinecraftForge.EVENT_BUS.register(this.renderHandler = new RenderHandler(Minecraft.getInstance()));
                break;
            case LOAD_COMPLETE:
                WItemRenderer.registerColors();
                break;
        }
    }

    @Override
    public void setTrackedEntities(List<LivingEntity> entities) {
        this.renderHandler.addTrackedEntities(entities);
    }

    @Override
    public void clearTrackedEntities() {
        this.renderHandler.clearTrackedEntities();
    }
}
