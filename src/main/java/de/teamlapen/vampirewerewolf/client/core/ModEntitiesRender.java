package de.teamlapen.vampirewerewolf.client.core;

import de.teamlapen.vampirewerewolf.client.render.RenderWerewolf;
import de.teamlapen.vampirewerewolf.entities.EntityWerewolf;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModEntitiesRender {
    public static void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityWerewolf.class, RenderWerewolf::new);
    }
}
