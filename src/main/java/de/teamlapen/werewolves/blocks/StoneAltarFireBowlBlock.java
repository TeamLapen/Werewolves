package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.block.CampfireBlock.makeParticles;

public class StoneAltarFireBowlBlock extends Block implements IWaterLoggable {
    public static final String REG_NAME = "stone_altar_fire_bowl";
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SOUL_FIRE = WUtils.SOUL_FIRE;
    protected static final VoxelShape SHAPE = makeShape();

    public StoneAltarFireBowlBlock() {
        super(Block.Properties.of(Material.STONE).noOcclusion().lightLevel((state) -> state.getValue(LIT)?14:0));
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false).setValue(WATERLOGGED, false).setValue(SOUL_FIRE, false));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.box(4, 0, 4, 12, 8, 12);

        VoxelShape b = Block.box(2, 8, 2, 14, 10, 14);

        VoxelShape c = Block.box(2, 10, 2, 4, 15, 4);
        VoxelShape d = Block.box(12, 10, 12, 14, 15, 14);
        VoxelShape e = Block.box(2, 10, 12, 4, 15, 14);
        VoxelShape f = Block.box(12, 10, 2, 14, 15, 4);

        VoxelShape g = Block.box(3, 10, 3, 13, 14, 3.1);
        VoxelShape h = Block.box(3, 10, 3, 3.1, 14, 13);
        VoxelShape i = Block.box(13, 10, 13, 12.9, 14, 3);
        VoxelShape j = Block.box(13, 10, 13, 3, 14, 12.9);

        return VoxelShapes.or(a, b, c, d, e, f, g, h, i, j);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public ActionResultType use(BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult p_225533_6_) {
        if (!state.getValue(LIT)) {
            ItemStack stack = player.getItemInHand(handIn);
            if (stack.getItem() == Items.TORCH || stack.getItem() == Items.SOUL_TORCH) {
                if (!state.getValue(WATERLOGGED)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(LIT, true).setValue(SOUL_FIRE, stack.getItem() == Items.SOUL_TORCH));
                    return ActionResultType.sidedSuccess(worldIn.isClientSide);
                }
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED).add(SOUL_FIRE);
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) :super.getFluidState(state);
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
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        return super.isBurning(state, world, pos);
    }

    @Override
    public boolean placeLiquid(@Nonnull IWorld world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull FluidState fluid) {
        if (IWaterLoggable.super.placeLiquid(world, pos, state, fluid)){
            if (state.getValue(LIT)) {
                world.setBlock(pos, state.setValue(LIT, false), 3);
                if (world.isClientSide()) {
                    for (int i = 0; i < 20; ++i) {
                        makeParticles((World) world, pos.above(1), false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + rand.nextDouble();
            double d1 = (double) pos.getY() + rand.nextDouble() + 0.7D;
            double d2 = (double) pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
