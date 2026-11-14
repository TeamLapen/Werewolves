package de.teamlapen.werewolves.client;

import de.teamlapen.lib.lib.util.IInitListener;
import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.common.core.ModRegistryManager;
import de.teamlapen.werewolves.client.proxy.ClientProxy;
import de.teamlapen.werewolves.common.proxy.Proxy;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
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

        NeoForge.EVENT_BUS.register(this.clientEventHandler);
        NeoForge.EVENT_BUS.register(this.modHUDOverlay);
        NeoForge.EVENT_BUS.register(new ModKeys(this.clientEventHandler));
        NeoForge.EVENT_BUS.register(this.renderHandler);

    }

    @SubscribeEvent
    public void setupClient(@NotNull FMLClientSetupEvent event) {
        this.registryManager.onInitStep(IInitListener.Step.CLIENT_SETUP, event);
    }

    private void registerReloadListeners(@NotNull RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(this.renderHandler);
    }

    @SubscribeEvent
    public void onAddLayers(EntityRenderersEvent.AddLayers event) {
        this.modPlayerRenderer = new ModPlayerRenderer(event.getContext());
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
