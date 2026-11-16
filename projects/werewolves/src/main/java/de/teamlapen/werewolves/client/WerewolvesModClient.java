package de.teamlapen.werewolves.client;

import net.minecraft.world.entity.player.PlayerModelType;

import net.minecraft.client.player.AbstractClientPlayer;

import net.minecraft.client.renderer.entity.player.AvatarRenderer;

import de.teamlapen.werewolves.client.model.WerewolfEarsModel;

import de.teamlapen.werewolves.client.render.layer.HumanWerewolfLayer;

import de.teamlapen.werewolves.client.render.state.WerewolvesRenderStates;

import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.common.core.ModRegistryManager;
import de.teamlapen.werewolves.client.proxy.ClientProxy;
import de.teamlapen.werewolves.common.proxy.Proxy;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;
import de.teamlapen.werewolves.api.WIdentifier;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

@ApiStatus.Internal
public class WerewolvesModClient {

    private static WerewolvesModClient INSTANCE;
    public static WerewolvesModClient getInstance() {
        return INSTANCE;
    }

    private final IEventBus modEventBus;
    private final ModRegistryManager registryManager;
    private final ModHUDOverlay modHUDOverlay;
    private final ClientEventHandler clientEventHandler;
    private final RenderHandler renderHandler;

    private ModPlayerRenderer modPlayerRenderer;

    public WerewolvesModClient(IEventBus modbus, ModRegistryManager registryManager) {
        INSTANCE = this;
        this.modEventBus = modbus;
        this.registryManager = registryManager;
        this.modHUDOverlay = new ModHUDOverlay();
        this.clientEventHandler = new ClientEventHandler();
        this.renderHandler = new RenderHandler(Minecraft.getInstance());

        modbus.addListener(ModKeys::registerKeyMapping);
        modbus.addListener(ModModelRender::onRegisterRenderers);
        modbus.addListener(ModModelRender::onRegisterLayers);
        modbus.addListener(ModBlocksRenderer::registerBlockEntityRenderers);
        modbus.addListener(ModScreens::registerScreens);
        modbus.addListener(ModScreens::registerScreenOverlays);
        modbus.addListener(this::registerReloadListeners);
        modbus.addListener(this::onAddLayers);
        modbus.addListener(WerewolvesRenderStates::addRenderStateModifier);
        modbus.addListener(RenderHandler::addRenderStateModifier);
        modbus.addListener(this.renderHandler::createBuffers);
        modbus.addListener(de.teamlapen.werewolves.client.extensions.EffectExtensions::registerClientExtensions);
        modbus.register(de.teamlapen.werewolves.client.extensions.ItemExtensions.class);

        NeoForge.EVENT_BUS.register(this.clientEventHandler);
        NeoForge.EVENT_BUS.register(this.modHUDOverlay);
        NeoForge.EVENT_BUS.register(new ModKeys(this.clientEventHandler));
        NeoForge.EVENT_BUS.register(this.renderHandler);

    }

    private void registerReloadListeners(@NotNull AddClientReloadListenersEvent event) {
        event.addListener(WIdentifier.mod("render_handler"), this.renderHandler);
    }

    @SubscribeEvent
    public void onAddLayers(EntityRenderersEvent.AddLayers event) {
        this.modPlayerRenderer = new ModPlayerRenderer(event.getContext());
        WerewolvesRenderStates.clearCaches();
        if (ClientUtils.noLoadingExceptions()) {
            for (PlayerModelType skin : event.getSkins()) {
                AvatarRenderer<AbstractClientPlayer> renderer = event.getPlayerRenderer(skin);
                if (renderer != null) {
                    renderer.addLayer(new HumanWerewolfLayer<>(renderer, new WerewolfEarsModel<>(event.getEntityModels().bakeLayer(skin == PlayerModelType.SLIM ? ModModelRender.EARS_CLAWS_SLIM : ModModelRender.EARS_CLAWS))));
                }
            }
        }
    }

    public ClientEventHandler getClientEventHandler() {
        return clientEventHandler;
    }

    public RenderHandler getRenderHandler() {
        return renderHandler;
    }

    public IEventBus getModEventBus() {
        return modEventBus;
    }

    public ModHUDOverlay getModHUDOverlay() {
        return modHUDOverlay;
    }

    @UnknownNullability
    public ModPlayerRenderer getModPlayerRenderer() {
        return modPlayerRenderer;
    }

    public static Proxy getProxy() {
        return new ClientProxy();
    }
}
