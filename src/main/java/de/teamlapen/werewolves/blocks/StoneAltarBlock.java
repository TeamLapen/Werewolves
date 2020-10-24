package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.tileentity.StoneAltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class StoneAltarBlock extends WerewolfBlock {
    protected static final VoxelShape SHAPE = makeShape();
    private static final String REG_NAME = "stone_altar";

    public StoneAltarBlock() {
        super(REG_NAME, Block.Properties.create(Material.ROCK));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.makeCuboidShape(2, 0, 2, 14, 9.5, 14);

        VoxelShape b1 = Block.makeCuboidShape(0, 7, 0, 2, 12, 2);
        VoxelShape b2 = Block.makeCuboidShape(14, 7, 14, 16, 12, 16);
        VoxelShape b3 = Block.makeCuboidShape(14, 7, 0, 16, 12, 2);
        VoxelShape b4 = Block.makeCuboidShape(0, 7, 14, 2, 12, 16);

        VoxelShape c1 = Block.makeCuboidShape(7, 7, 0, 9, 12, 2);
        VoxelShape c2 = Block.makeCuboidShape(0, 7, 7, 2, 12, 9);
        VoxelShape c3 = Block.makeCuboidShape(7, 7, 14, 9, 12, 16);
        VoxelShape c4 = Block.makeCuboidShape(14, 7, 7, 16, 12, 9);

        VoxelShape d1 = Block.makeCuboidShape(1, 7, 1, 15, 11, 2);
        VoxelShape d2 = Block.makeCuboidShape(1, 7, 1, 2, 11, 15);
        VoxelShape d3 = Block.makeCuboidShape(15, 7, 15, 14, 11, 2);
        VoxelShape d4 = Block.makeCuboidShape(15, 7, 15, 2, 11, 14);

        return VoxelShapes.or(a, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTiles.stone_altar.create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    @SuppressWarnings("ConstantConditions")
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) return ActionResultType.PASS;
        return ((StoneAltarTileEntity) worldIn.getTileEntity(pos)).onInteraction(player);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
