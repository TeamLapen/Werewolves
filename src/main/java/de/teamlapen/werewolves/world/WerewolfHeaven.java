package de.teamlapen.werewolves.world;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.world.IFactionBiome;
import de.teamlapen.werewolves.api.WReference;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import javax.annotation.Nonnull;

public class WerewolfHeaven extends Biome implements IFactionBiome {

    private static final SurfaceBuilderConfig werewolf_heaven_surface = new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState());

    public WerewolfHeaven() {
        super(new Builder().surfaceBuilder(SurfaceBuilder.DEFAULT, werewolf_heaven_surface).category(Category.FOREST).depth(0.2f).scale(0.5f).waterColor(0x4CCCFF).waterFogColor(0x4CCCFF).precipitation(RainType.NONE).parent(null).downfall(0.0f).temperature(0.3f));
        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.func_222283_Y(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addBirchTrees(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addForestTrees(this);
        DefaultBiomeFeatures.addBerryBushes(this);
    }

    @Override
    public int getFoliageColor(@Nonnull BlockPos pos) {
        return 0x00BA43;
    }

    @Override
    public int getGrassColor(@Nonnull BlockPos pos) {
        return 0x00BA43;
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature) {
        return 0x4CCCFF;
    }

    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
