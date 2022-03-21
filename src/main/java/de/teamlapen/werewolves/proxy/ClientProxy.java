package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.client.gui.overlay.ActionCooldownOverlay;
import de.teamlapen.werewolves.client.gui.overlay.FurOverlay;
import de.teamlapen.werewolves.client.gui.overlay.WerewolfFormDurationOverlay;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    private ModHUDOverlay hudOverlay;

    public ClientProxy() {
        RenderHandler renderHandler = new RenderHandler(Minecraft.getInstance());
        MinecraftForge.EVENT_BUS.register(renderHandler);
        //Minecraft.instance is null during runData.
        //noinspection ConstantConditions
        if (Minecraft.getInstance() != null)
            ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(renderHandler); // Must be added before initial resource manager load
    }

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        super.onInitStep(step, event);
        ClientEventHandler clientHandler;
        switch (step) {
            case CLIENT_SETUP -> {
                ModBlocksRenderer.register();
                MinecraftForge.EVENT_BUS.register(clientHandler = new ClientEventHandler());
                MinecraftForge.EVENT_BUS.register(hudOverlay = new ModHUDOverlay());
                ModKeys.register(clientHandler);
                OverlayRegistry.registerOverlayBottom("werewolves_fur_border", new FurOverlay());
                OverlayRegistry.registerOverlayBottom("werewolves_action_cooldown", new ActionCooldownOverlay());
                OverlayRegistry.registerOverlayAbove(ForgeIngameGui.EXPERIENCE_BAR_ELEMENT, "werewolves_fur_border", new WerewolfFormDurationOverlay());
            }
            case LOAD_COMPLETE -> {
                event.enqueueWork(ModItemRenderer::registerColorsUnsafe);
                event.enqueueWork(ModScreens::registerScreensUnsafe);
            }
            default -> {
            }
        }
    }

    @Override
    public void handleAttackTargetEventPacket(AttackTargetEventPacket packet) {
        this.hudOverlay.attackTriggered(packet.entityId());
    }
}
