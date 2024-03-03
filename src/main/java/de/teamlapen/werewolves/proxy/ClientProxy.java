package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.blocks.LogBlock;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.client.gui.WolfsbaneDiffuserScreen;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    private ModHUDOverlay hudOverlay;
    private RenderHandler renderHandler;

    public ClientProxy() {
        //Minecraft.instance is null during runData.
        //noinspection ConstantConditions
        if (Minecraft.getInstance() != null) {
            this.renderHandler = new RenderHandler(Minecraft.getInstance());
            MinecraftForge.EVENT_BUS.register(renderHandler);
            ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(renderHandler); // Must be added before initial resource manager load
        }
    }

    @Override
    public void onInitStep(@NotNull Step step, @NotNull ParallelDispatchEvent event) {
        super.onInitStep(step, event);
        ClientEventHandler clientHandler;
        switch (step) {
            case CLIENT_SETUP -> {
                MinecraftForge.EVENT_BUS.register(clientHandler = new ClientEventHandler());
                MinecraftForge.EVENT_BUS.register(hudOverlay = new ModHUDOverlay());
                FMLJavaModLoadingContext.get().getModEventBus().addListener(clientHandler::onAddLayer);
                event.enqueueWork(() -> {
                    Sheets.addWoodType(LogBlock.MAGIC);
                    Sheets.addWoodType(LogBlock.JACARANDA);
                });
                ModKeys.register(clientHandler);
            }
            case LOAD_COMPLETE -> {
                event.enqueueWork(ModScreens::registerScreensUnsafe);
            }
            default -> {
            }
        }
    }

    @Override
    public void handleAttackTargetEventPacket(@NotNull ClientboundAttackTargetEventPacket packet) {
        this.hudOverlay.attackTriggered(packet.entityId());
    }

    @Override
    public void endVisionBatch() {
        this.renderHandler.endVisionBatch();
    }

    @Override
    public void displayWolfsbaneScreen(WolfsbaneDiffuserBlockEntity tile, Component title) {
        openScreen(new WolfsbaneDiffuserScreen(tile, title));
    }

    public static void runOnRenderThread(Runnable runnable) {
        Minecraft.getInstance().execute(runnable);
    }

    public static void openScreen(Screen screen) {
        runOnRenderThread(() -> Minecraft.getInstance().setScreen(screen));
    }
}
