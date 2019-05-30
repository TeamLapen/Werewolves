package de.teamlapen.werewolves.proxy;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import java.util.List;

/**
 * Abstract proxy base for both client and server. Try to keep this quite empty
 * and move larger code parts into dedicated classes.
 *
 */
public abstract class CommonProxy implements IProxy {

    @Override
    public void onInitStep(Step step, FMLStateEvent event) {
    }

    @Override
    public List<Entity> getRayTraceEntity() {
        return null;
    }

}
