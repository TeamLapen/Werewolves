package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.tiles.StoneAltarTESR;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModTiles;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
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
        RenderType renderType = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.wolfsbane, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.jacaranda_sapling, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.magic_sapling, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.stone_altar, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.stone_altar_fire_bowl, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf_crafted, renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.jacaranda_leaves, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.magic_leaves, RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.potted_wolfsbane, RenderType.cutoutMipped());
    }

    private static void registerTileRenderer() {
        ClientRegistry.bindTileEntityRenderer(ModTiles.stone_altar, StoneAltarTESR::new);
    }
}
