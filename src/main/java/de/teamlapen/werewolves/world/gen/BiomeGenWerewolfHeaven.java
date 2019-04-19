package de.teamlapen.werewolves.world.gen;

import de.teamlapen.werewolves.entity.EntityDirewolf;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class BiomeGenWerewolfHeaven extends Biome {
    public final static String name = "werewolf heaven";

    public BiomeGenWerewolfHeaven() {
        super(new BiomeProperties(name).setWaterColor(0xFF7A317A).setBaseHeight(0.3F).setHeightVariation(0.3F));

        this.decorator.treesPerChunk = 10;

        this.spawnableCaveCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntityDirewolf.class, 3, 2, 5));
    }

    @Override
    public int getGrassColorAtPos(BlockPos pos) {
        return 0x9EFD38;
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
        return 0x9EFD38;
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature) {
        return 0x4BB8F7;
    }
}
