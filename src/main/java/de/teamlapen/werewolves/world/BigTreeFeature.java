package de.teamlapen.werewolves.world;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class BigTreeFeature extends net.minecraft.world.gen.feature.BigTreeFeature {
    private final BlockState logState;
    private final BlockState leavesState;

    public BigTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyIn) {
        this(configFactoryIn, doBlockNotifyIn, Blocks.OAK_LOG.getDefaultState(),Blocks.OAK_LEAVES.getDefaultState());
    }

    public BigTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyIn, BlockState logState, BlockState leavesState) {
        super(configFactoryIn, doBlockNotifyIn);
        this.leavesState = leavesState;
        this.logState = logState;
    }

    /**
     * copied from supermethod only changed the leaves blockstate
     */
    @Override
    protected void crossSection(@Nonnull IWorldGenerationReader worldIn, @Nonnull BlockPos pos, float p_208529_3_, @Nonnull MutableBoundingBox p_208529_4_, @Nonnull Set<BlockPos> changedBlocks) {
        int i = (int)((double)p_208529_3_ + 0.618D);

        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                if (Math.pow((double)Math.abs(j) + 0.5D, 2.0D) + Math.pow((double)Math.abs(k) + 0.5D, 2.0D) <= (double)(p_208529_3_ * p_208529_3_)) {
                    BlockPos blockpos = pos.add(j, 0, k);
                    if (isAirOrLeaves(worldIn, blockpos)) {
                        this.setLogState(changedBlocks, worldIn, blockpos, leavesState, p_208529_4_);
                    }
                }
            }
        }

    }

    /**
     * copied from supermethod only changed the log blockstate
     */
    @Override
    protected int makeLimb(@Nonnull Set<BlockPos> p_208523_1_, @Nonnull IWorldGenerationReader worldIn, @Nonnull BlockPos p_208523_3_, @Nonnull BlockPos p_208523_4_, boolean p_208523_5_, @Nonnull MutableBoundingBox p_208523_6_) {
        if (!p_208523_5_ && Objects.equals(p_208523_3_, p_208523_4_)) {
            return -1;
        } else {
            BlockPos blockpos = p_208523_4_.add(-p_208523_3_.getX(), -p_208523_3_.getY(), -p_208523_3_.getZ());
            int i = this.getGreatestDistance(blockpos);
            float f = (float)blockpos.getX() / (float)i;
            float f1 = (float)blockpos.getY() / (float)i;
            float f2 = (float)blockpos.getZ() / (float)i;

            for(int j = 0; j <= i; ++j) {
                BlockPos blockpos1 = p_208523_3_.add(0.5F + (float)j * f, 0.5F + (float)j * f1, 0.5F + (float)j * f2);
                if (p_208523_5_) {
                    this.setLogState(p_208523_1_, worldIn, blockpos1, logState.with(LogBlock.AXIS, this.getLoxAxis(p_208523_3_, blockpos1)), p_208523_6_);
                } else if (!func_214587_a(worldIn, blockpos1)) {
                    return j;
                }
            }

            return -1;
        }
    }

}
