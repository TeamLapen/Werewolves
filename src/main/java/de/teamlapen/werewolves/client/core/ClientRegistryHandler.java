package de.teamlapen.werewolves.client.core;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ClientRegistryHandler {

    public static void init() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();

        modbus.addListener(ModKeys::registerKeyMapping);
        modbus.addListener(ModModelRender::onRegisterRenderers);
        modbus.addListener(ModModelRender::onRegisterLayers);
        modbus.addListener(ModBlocksRenderer::registerBlockEntityRenderers);
        modbus.addListener(ModScreens::registerScreenOverlays);
    }
}
