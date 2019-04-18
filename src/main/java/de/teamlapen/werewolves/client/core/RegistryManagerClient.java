package de.teamlapen.werewolves.client.core;

import de.teamlapen.lib.lib.util.IInitListener;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Handle client side registration events as well as a few dependent registrations
 */
@SideOnly(Side.CLIENT)
public class RegistryManagerClient implements IInitListener {

    @Override
    public void onInitStep(Step step, FMLStateEvent event) {
        switch (step) {
            case PRE_INIT:
                break;
            case INIT:
                ModItemsRender.registerColors();
                break;
            case POST_INIT:
                break;
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onRegisterModels(ModelRegistryEvent event) {
        ModEntitiesRender.registerEntityRenderer();
        ModBlocksRender.register();
        ModItemsRender.register();
    }

}
