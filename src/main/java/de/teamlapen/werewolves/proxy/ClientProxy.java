package de.teamlapen.werewolves.proxy;

import de.teamlapen.werewolves.client.core.*;
import de.teamlapen.werewolves.network.AttackTargetEventPacket;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {

    private ModHUDOverlay hudOverlay;
    private ClientEventHandler clientHandler;
    private boolean autoJump;

    public ClientProxy() {
        RenderHandler renderHandler = new RenderHandler(Minecraft.getInstance());
        MinecraftForge.EVENT_BUS.register(renderHandler);
        //Minecraft.instance is null during runData.
        //noinspection ConstantConditions
        if (Minecraft.getInstance() != null)
            ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(renderHandler); // Must be added before initial resource manager load
    }

    @Override
    public void onInitStep(Step step, ParallelDispatchEvent event) {
        super.onInitStep(step, event);
        switch (step) {
            case CLIENT_SETUP:
                ModEntityRenderer.registerEntityRenderer();
                ModBlocksRenderer.register();
                MinecraftForge.EVENT_BUS.register(clientHandler = new ClientEventHandler());
                MinecraftForge.EVENT_BUS.register(hudOverlay = new ModHUDOverlay());
                ModKeys.register(clientHandler);
                FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onTextureStitchEvent);
                break;
            case LOAD_COMPLETE:
                ModItemRenderer.registerColors();
                ModScreens.registerScreens();
                break;
        }
    }

    @Override
    public void handleAttackTargetEventPacket(AttackTargetEventPacket packet) {
        this.hudOverlay.attackTriggered(packet.entityId);
    }

    public void onTextureStitchEvent(TextureStitchEvent.Pre event) {
        event.addSprite(new ResourceLocation(REFERENCE.MODID, PlayerContainer.EMPTY_ARMOR_SLOT_HELMET.getPath()));
        event.addSprite(new ResourceLocation(REFERENCE.MODID, PlayerContainer.EMPTY_ARMOR_SLOT_CHESTPLATE.getPath()));
        event.addSprite(new ResourceLocation(REFERENCE.MODID, PlayerContainer.EMPTY_ARMOR_SLOT_LEGGINGS.getPath()));
        event.addSprite(new ResourceLocation(REFERENCE.MODID, PlayerContainer.EMPTY_ARMOR_SLOT_BOOTS.getPath()));
    }
}
