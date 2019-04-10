package de.teamlapen.vampirewerewolf.world.gen;

import com.google.common.collect.Lists;

import de.teamlapen.vampirewerewolf.blocks.WerewolfFlower;
import de.teamlapen.vampirewerewolf.core.ModBlocks;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.List;
import java.util.Random;

public class VampireWerewolfWorldGen implements IWorldGenerator {
    private WorldGenerator silver_ore;
    private WorldGenerator wolfsbane;

    public VampireWerewolfWorldGen() {
        silver_ore = new WorldGenMinable(ModBlocks.silver_ore.getDefaultState(), 9);
        wolfsbane = new WorldGenFlowers(ModBlocks.werewolf_flower, WerewolfFlower.EnumFlowerType.WOLFBANE);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            runGenerator(silver_ore, world, random, chunkX, chunkZ, 50, 0, 100);
            runFlowerGenerator(wolfsbane, world, random, chunkX, chunkZ, 2, Lists.newArrayList(Biomes.RIVER, Biomes.TAIGA, Biomes.TAIGA_HILLS));
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

    private void runFlowerGenerator(WorldGenerator gen, World world, Random random, int chunkX, int chunkZ, int chance, List<Biome> biomes) {
        if (!biomes.contains(world.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)))) return;
        for (int i = 0; i < chance; i++) {
            int x = chunkX * 16 + random.nextInt(16) + 8;
            int z = chunkZ * 16 + random.nextInt(16) + 8;
            int y = world.getHeight(x, z);

            gen.generate(world, random, new BlockPos(x, y, z));
        }
    }
}
