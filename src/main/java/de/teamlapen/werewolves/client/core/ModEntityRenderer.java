package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void updateRenderer(EntityRendererProvider.Context context) {
        render = new WerewolfPlayerRenderer(context);
    }
}
