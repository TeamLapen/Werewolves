package de.teamlapen.werewolves.world.gen.feature.trunkplacer;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class MultipleForkingTrunkPlacer extends TrunkPlacer {
    public static final Codec<MultipleForkingTrunkPlacer> CODEC = RecordCodecBuilder.create((p_70161_) -> {
        return trunkPlacerParts(p_70161_).apply(p_70161_, MultipleForkingTrunkPlacer::new);
    });

    public MultipleForkingTrunkPlacer(int baseHeight, int random1, int random2) {
        super(baseHeight, random1, random2);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return WerewolvesBiomeFeatures.FORK_TRUNK_PLACER.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader level, @NotNull BiConsumer<BlockPos, BlockState> placer, @NotNull RandomSource random, int treeHeight, BlockPos pos, @NotNull TreeConfiguration config) {
        setDirtAt(level, placer, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int i = treeHeight - random.nextInt(4) - 1;
        int j = 3 - random.nextInt(3);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int k = pos.getX();
        int l = pos.getZ();
        OptionalInt optionalint = OptionalInt.empty();

        for(int i1 = 0; i1 < treeHeight; ++i1) {
            int j1 = pos.getY() + i1;
            if (i1 >= i && j > 0) {
                k += direction.getStepX();
                l += direction.getStepZ();
                --j;
            }

            if (this.placeLog(level, placer, random, blockpos$mutableblockpos.set(k, j1, l), config)) {
                optionalint = OptionalInt.of(j1 + 1);
            }
        }

        if (optionalint.isPresent()) {
            list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(k, optionalint.getAsInt(), l), 1, false));
        }

        k = pos.getX();
        l = pos.getZ();
        List<Direction> dirs = Direction.Plane.HORIZONTAL.stream().filter(d -> random.nextInt(3) != 0).toList();
        for (Direction direction1 : dirs) {
            if (direction1 != direction) {
                int j2 = i - random.nextInt(2) - 1;
                int k1 = 1 + random.nextInt(3);
                optionalint = OptionalInt.empty();

                for (int l1 = j2; l1 < treeHeight && k1 > 0; --k1) {
                    if (l1 >= 1) {
                        int i2 = pos.getY() + l1;
                        k += direction1.getStepX();
                        l += direction1.getStepZ();
                        if (this.placeLog(level, placer, random, blockpos$mutableblockpos.set(k, i2, l), config)) {
                            optionalint = OptionalInt.of(i2 + 1);
                        }
                    }

                    ++l1;
                }

                if (optionalint.isPresent()) {
                    list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(k, optionalint.getAsInt(), l), 0, false));
                }
            }
        }

        return list;
    }
}
