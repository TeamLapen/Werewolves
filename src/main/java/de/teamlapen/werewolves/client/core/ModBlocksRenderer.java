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
        RenderType renderType = RenderType.cutout();
        RenderTypeLookup.setRenderLayer(ModBlocks.WOLFSBANE.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.JACARANDA_SAPLING.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGIC_SAPLING.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.STONE_ALTAR.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), renderType);
        RenderTypeLookup.setRenderLayer(ModBlocks.JACARANDA_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGIC_LEAVES.get(), RenderType.cutoutMipped());
        RenderTypeLookup.setRenderLayer(ModBlocks.POTTED_WOLFSBANE.get(), RenderType.cutoutMipped());
    }

    private static void registerTileRenderer() {
        ClientRegistry.bindTileEntityRenderer(ModTiles.STONE_ALTAR.get(), StoneAltarTESR::new);
    }
}
