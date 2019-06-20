package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.RenderDirewolf;
import de.teamlapen.werewolves.entity.EntityDirewolf;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModEntitiesRender {
    public static void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityDirewolf.class, RenderDirewolf::new);
    }
}
