package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.blocks.LogBlock;
import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.network.ClientboundAttackTargetEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
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
}
