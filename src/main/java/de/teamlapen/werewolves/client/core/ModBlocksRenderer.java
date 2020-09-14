package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.core.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModBlocksRenderer {
    public static void register() {
        registerRenderType();
    }

    private static void registerRenderType() {
        RenderType renderType = RenderType.cutout();
        RenderTypeLookup.setRenderLayer(ModBlocks.stone_altar, renderType);
    }
}
