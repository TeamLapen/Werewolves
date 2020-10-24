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
        RenderTypeLookup.setRenderLayer(ModBlocks.wolfsbane, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.jacaranda_sapling, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.magic_sapling, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.stone_altar, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf_crafted, renderType);
    }
}
