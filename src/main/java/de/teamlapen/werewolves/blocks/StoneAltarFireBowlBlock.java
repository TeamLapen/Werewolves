package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static net.minecraft.world.level.block.CampfireBlock.makeParticles;

public class StoneAltarFireBowlBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final String REG_NAME = "stone_altar_fire_bowl";
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SOUL_FIRE = WUtils.SOUL_FIRE;
    protected static final VoxelShape SHAPE = makeShape();

    public StoneAltarFireBowlBlock() {
        super(Block.Properties.of(Material.STONE).noOcclusion().lightLevel((state) -> state.getValue(LIT) ? 14 : 0));
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, false).setValue(WATERLOGGED, false).setValue(SOUL_FIRE, false).setValue(FACING, Direction.NORTH));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.box(0, 0, 0, 16, 5, 16);
        VoxelShape b = Block.box(2, 5, 2, 14, 9, 14);
        VoxelShape c = Block.box(0, 9, 0, 16, 14, 16);

        return Shapes.or(a, b, c);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public InteractionResult use(BlockState state, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand handIn, @Nonnull BlockHitResult p_225533_6_) {
        if (!state.getValue(LIT)) {
            ItemStack stack = player.getItemInHand(handIn);
            if (stack.getItem() == Items.TORCH || stack.getItem() == Items.SOUL_TORCH) {
                if (!state.getValue(WATERLOGGED)) {
                    worldIn.setBlockAndUpdate(pos, state.setValue(LIT, true).setValue(SOUL_FIRE, stack.getItem() == Items.SOUL_TORCH));
                    return InteractionResult.sidedSuccess(worldIn.isClientSide);
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED).add(SOUL_FIRE).add(FACING);
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isBurning(BlockState state, BlockGetter world, BlockPos pos) {
        return super.isBurning(state, world, pos);
    }

    @Override
    public boolean placeLiquid(@Nonnull LevelAccessor world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull FluidState fluid) {
        if (SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid)) {
            if (state.getValue(LIT)) {
                world.setBlock(pos, state.setValue(LIT, false), 3);
                if (world.isClientSide()) {
                    for (int i = 0; i < 20; ++i) {
                        makeParticles((Level) world, pos.above(1), false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState stateIn, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + rand.nextDouble();
            double d1 = (double) pos.getY() + rand.nextDouble() + 0.7D;
            double d2 = (double) pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }
}
