package de.teamlapen.werewolves.proxy;

import de.teamlapen.lib.lib.util.IInitListener;

import net.minecraft.entity.Entity;

import java.util.List;

/**
 * Proxy interface
 */
public interface IProxy extends IInitListener {

    public List<Entity> getRayTraceEntity();
}
