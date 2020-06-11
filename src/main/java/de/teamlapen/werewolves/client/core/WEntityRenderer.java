package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.WerewolfPlayerRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WEntityRenderer {

    public static WerewolfPlayerRenderer render;

    public static void registerEntityRenderer(){
        render = new WerewolfPlayerRenderer(Minecraft.getInstance().getRenderManager());
    }
}
