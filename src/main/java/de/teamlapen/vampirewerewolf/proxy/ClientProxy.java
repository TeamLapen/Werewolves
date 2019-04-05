package de.teamlapen.vampirewerewolf.proxy;

import de.teamlapen.vampirewerewolf.client.render.RenderHandler;
import de.teamlapen.vampirewerewolf.core.RegistryManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Clientside Proxy
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        RegistryManager.setupClientRegistryManager();
    }
    @Override
    public void onInitStep(Step step, FMLStateEvent event) {
        super.onInitStep(step, event);
        RegistryManager.getRegistryManagerClient().onInitStep(step, event);
        switch (step) {
            case PRE_INIT:
                registerSubscriptions();
                break;
            case INIT:
                break;
            case POST_INIT:
                break;
        }
    }

    private void registerSubscriptions() {
        MinecraftForge.EVENT_BUS.register(new RenderHandler(Minecraft.getMinecraft()));
    }
}
