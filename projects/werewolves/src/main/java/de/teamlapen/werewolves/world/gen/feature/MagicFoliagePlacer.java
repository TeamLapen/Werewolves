package de.teamlapen.werewolves.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teamlapen.werewolves.core.ModWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class MagicFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<MagicFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((p_68427_) -> {
        return foliagePlacerParts(p_68427_).apply(p_68427_, MagicFoliagePlacer::new);
    });


    public MagicFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return ModWorld.MAGIC_FOLIAGE.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader pLevel, @NotNull FoliageSetter pBlockSetter, @NotNull RandomSource pRandom, @NotNull TreeConfiguration pConfig, int pMaxFreeTreeHeight, @NotNull FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        BlockPos pos = pAttachment.pos().above(pOffset);
        this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pos = pos.above(), pFoliageRadius - 1, pOffset, false);

        pos = pos.below();
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.north());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.south());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.east());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.west());

        pos = pos.below();
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pos, 1, pOffset, false);
        pos = pos.below();
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pos, 1, pOffset, false);
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.north(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.south(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.east(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.west(2));

        pos = pos.below();
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pos, 1, pOffset, false);
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.north(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.north(2).east());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.north(2).west());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.south(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.south(2).east());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.south(2).west());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.east(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.east(2).north());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.east(2).south());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.west(2));
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.west(2).north());
        tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, pos.west(2).south());

        pos = pos.below();
        placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, pos, 1, pOffset, false);
    }

    @Override
    public int foliageHeight(@NotNull RandomSource pRandom, int pHeight, @NotNull TreeConfiguration pConfig) {
        return pHeight - pRandom.nextInt((int) (pHeight * 0.2));
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}
