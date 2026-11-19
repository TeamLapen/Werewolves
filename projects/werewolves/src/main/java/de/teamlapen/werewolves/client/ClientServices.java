package de.teamlapen.werewolves.client;

import de.teamlapen.faction.Services;
import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.client.model.WerewolfEarsModel;
import de.teamlapen.werewolves.client.render.layer.HumanWerewolfLayer;
import de.teamlapen.werewolves.client.render.state.WerewolvesRenderStates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.player.PlayerModelType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullUnmarked;

public class ClientServices extends Services {

    private final ClientEventHandler clientEventHandler  = new ClientEventHandler();
    private final ModHUDOverlay modHUDOverlay = new ModHUDOverlay();
    private final RenderHandler renderHandler= new RenderHandler();
    private final ModKeys modKeys= new ModKeys(this.clientEventHandler);
    @Nullable
    private ModPlayerRenderer modPlayerRenderer;

    @Override
    protected void registerModBus(IEventBus bus) {
        bus.addListener(ModKeys::registerKeyMapping);
        bus.addListener(ModModelRender::onRegisterRenderers);
        bus.addListener(ModModelRender::onRegisterLayers);
        bus.addListener(ModBlocksRenderer::registerBlockEntityRenderers);
        bus.addListener(ModScreens::registerScreens);
        bus.addListener(ModScreens::registerScreenOverlays);
        bus.addListener(renderHandler::registerReloadListeners);
        bus.addListener(this::onAddLayers);
        bus.addListener(WerewolvesRenderStates::addRenderStateModifier);
        bus.addListener(RenderHandler::addRenderStateModifier);
        bus.addListener(this.renderHandler::createBuffers);
        bus.addListener(de.teamlapen.werewolves.client.extensions.EffectExtensions::registerClientExtensions);
        bus.register(de.teamlapen.werewolves.client.extensions.ItemExtensions.class);
        bus.addListener(ModRenderPipelines::registerRenderPipelines);
        bus.addListener(ModScreens::registerAppearanceScreens);
    }

    public ModHUDOverlay hudOverlay() {
        return this.modHUDOverlay;
    }

    @Nullable
    public ModPlayerRenderer playerRenderer() {
        return this.modPlayerRenderer;
    }

    @Override
    protected void registerGameBus(IEventBus bus) {
        bus.register(this.clientEventHandler);
        bus.register(this.modHUDOverlay);
        bus.register(this.renderHandler);
        bus.register(this.modKeys);
    }

    @SubscribeEvent
    public void onAddLayers(EntityRenderersEvent.AddLayers event) {
        this.modPlayerRenderer = new ModPlayerRenderer(event.getContext());
        WerewolvesRenderStates.clearCaches();
        for (PlayerModelType skin : event.getSkins()) {
            AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
            if (renderer != null) {
                renderer.addLayer(new HumanWerewolfLayer<>(renderer, new WerewolfEarsModel<>(event.getEntityModels().bakeLayer(skin == PlayerModelType.SLIM ? ModModelRender.EARS_CLAWS_SLIM : ModModelRender.EARS_CLAWS))));
            }
        }
    }
}
