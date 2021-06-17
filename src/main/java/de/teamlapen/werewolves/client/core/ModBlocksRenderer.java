package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.tiles.StoneAltarTESR;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModTiles;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@OnlyIn(Dist.CLIENT)
public class ModBlocksRenderer {
    public static void register() {
        registerRenderType();
        registerTileRenderer();
    }

    private static void registerRenderType() {
        RenderType renderType = RenderType.getCutout();
        RenderTypeLookup.setRenderLayer(ModBlocks.wolfsbane, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.jacaranda_sapling, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.magic_sapling, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.stone_altar, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.stone_altar_fire_bowl, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf_crafted, renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.jacaranda_leaves, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.magic_leaves, RenderType.getCutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.potted_wolfsbane, RenderType.getCutoutMipped());
    }

    private static void registerTileRenderer() {
        ClientRegistry.bindTileEntityRenderer(ModTiles.stone_altar, StoneAltarTESR::new);
    }
}
