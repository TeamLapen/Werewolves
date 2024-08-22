package de.teamlapen.werewolves.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.core.ModWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class JacarandaTrunkPlacer extends TrunkPlacer {

    public static final MapCodec<JacarandaTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> trunkPlacerParts(instance).apply(instance, JacarandaTrunkPlacer::new));

    public JacarandaTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected @NotNull TrunkPlacerType<?> type() {
        return ModWorld.JACARANDA_TRUNK.get();
    }

    @Override
    public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(@NotNull LevelSimulatedReader pLevel, @NotNull BiConsumer<BlockPos, BlockState> pBlockSetter, @NotNull RandomSource pRandom, int pFreeTreeHeight, @NotNull BlockPos pPos, @NotNull TreeConfiguration pConfig) {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        List<FoliagePlacer.FoliageAttachment> attachments = new ArrayList<>();

        int currentLogHeight;
        for (currentLogHeight = 0; currentLogHeight < (pFreeTreeHeight / 3) * 2; currentLogHeight++) {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight), pConfig);
        }
        attachments.add(new FoliagePlacer.FoliageAttachment(pPos.above(currentLogHeight - 1).above(), 0, false));

        this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight).north().east(), pConfig);
        this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight + 1).north().east(2), pConfig);
        attachments.add(new FoliagePlacer.FoliageAttachment(pPos.above(currentLogHeight + 1).north().east(2).above(), 0, false));

        this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight).north(), pConfig);
        this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight + 1).north(2), pConfig);
        attachments.add(new FoliagePlacer.FoliageAttachment(pPos.above(currentLogHeight + 1).north(2).above(), 0, false));

        this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight).south().east(), pConfig);
        this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(currentLogHeight + 1).south(2).east(), pConfig);
        attachments.add(new FoliagePlacer.FoliageAttachment(pPos.above(currentLogHeight + 1).south(2).east().above(), 0, false));

        return attachments;
    }
}
