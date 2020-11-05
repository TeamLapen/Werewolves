package de.teamlapen.werewolves.client.render.tiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.teamlapen.werewolves.tileentity.StoneAltarTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static de.teamlapen.werewolves.tileentity.StoneAltarTileEntity.Phase;

@OnlyIn(Dist.CLIENT)
public class StoneAltarTESR extends TileEntityRenderer<StoneAltarTileEntity> {
    private static final ResourceLocation FIRE = new ResourceLocation("textures/block/fire_0.png");
    private static final BlockState FIRE_STATE = Blocks.FIRE.getDefaultState();

    public StoneAltarTESR(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull StoneAltarTileEntity tileEntityIn, float partialTicks, @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        StoneAltarTileEntity.Phase phase = tileEntityIn.getCurrentPhase();
        if (phase == Phase.FOG || phase == Phase.STARTING) {
            matrixStackIn.translate(0, 0.7, 0);
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(FIRE_STATE, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
        }
    }
}
