package de.teamlapen.werewolves.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StoneAltarFireBowlBlock extends Block {
    public static final String REG_NAME = "stone_altar_fire_bowl";
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    protected static final VoxelShape SHAPE = makeShape();

    public StoneAltarFireBowlBlock() {
        super(Block.Properties.create(Material.ROCK).notSolid());
        this.setDefaultState(this.getStateContainer().getBaseState().with(LIT, false));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.makeCuboidShape(4, 0, 4, 12, 8, 12);

        VoxelShape b = Block.makeCuboidShape(2, 8, 2, 14, 10, 14);

        VoxelShape c = Block.makeCuboidShape(2, 10, 2, 4, 15, 4);
        VoxelShape d = Block.makeCuboidShape(12, 10, 12, 14, 15, 14);
        VoxelShape e = Block.makeCuboidShape(2, 10, 12, 4, 15, 14);
        VoxelShape f = Block.makeCuboidShape(12, 10, 2, 14, 15, 4);

        VoxelShape g = Block.makeCuboidShape(3, 10, 3, 13, 14, 3.1);
        VoxelShape h = Block.makeCuboidShape(3, 10, 3, 3.1, 14, 13);
        VoxelShape i = Block.makeCuboidShape(13, 10, 13, 12.9, 14, 3);
        VoxelShape j = Block.makeCuboidShape(13, 10, 13, 3, 14, 12.9);

        return VoxelShapes.or(a, b, c, d, e, f, g, h, i, j);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult p_225533_6_) {
        if (!state.get(LIT)) {
            ItemStack stack = player.getHeldItem(handIn);
            if (stack.getItem() == Items.FLINT_AND_STEEL) {
                worldIn.setBlockState(pos, state.with(LIT, true));
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(@Nonnull BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(@Nonnull BlockState state) {
        return 2;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return state.get(LIT) ? 15 : 0;
    }

    @Override
    public boolean isFlammable(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
        return true;
    }
}
