package de.teamlapen.vampirewerewolf.world.gen;

import de.teamlapen.vampirewerewolf.core.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import java.util.Random;

public class VampireWerewolfWorldGen implements IWorldGenerator {
    private WorldGenerator silver_ore;

    public VampireWerewolfWorldGen() {
        silver_ore = new WorldGenMinable(ModBlocks.silver_ore.getDefaultState(), 9);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            runGenerator(silver_ore, world, random, chunkX, chunkZ, 50, 0, 100);
        }
    }

    private void runGenerator(WorldGenerator gen, World world, Random random, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight) {
        if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");
        
        int heightDiff = maxHeight - minHeight + 1;
        for (int i = 0; i < chance; i++) {
            int x = chunkX * 16 + random.nextInt(16);
            int y = minHeight + random.nextInt(heightDiff);
            int z = chunkZ * 16 + random.nextInt(16);

            gen.generate(world, random, new BlockPos(x, y, z));
        }
    }

}
