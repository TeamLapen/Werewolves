package de.teamlapen.werewolves.client.core;

import de.teamlapen.werewolves.client.render.tiles.StoneAltarTESR;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModTiles;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;

@OnlyIn(Dist.CLIENT)
public class ModBlocksRenderer {
    public static void register() {
        registerRenderType();
    }

    private static void registerRenderType() {
        RenderType renderType = RenderType.cutout();
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.wolfsbane.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.jacaranda_sapling.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.magic_sapling.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.stone_altar.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.stone_altar_fire_bowl.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.totem_top_werewolves_werewolf_crafted.get(), renderType);
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.jacaranda_leaves.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.magic_leaves.get(), RenderType.cutoutMipped());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.potted_wolfsbane.get(), RenderType.cutoutMipped());
    }

    public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModTiles.stone_altar.get(), StoneAltarTESR::new);
    }
}
