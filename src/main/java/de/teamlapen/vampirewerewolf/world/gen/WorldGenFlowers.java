package de.teamlapen.vampirewerewolf.world.gen;

import de.teamlapen.vampirewerewolf.blocks.WerewolfFlower;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenFlowers extends WorldGenerator {
    private WerewolfFlower flower;
    private IBlockState state;

    public WorldGenFlowers(WerewolfFlower flowerIn, WerewolfFlower.EnumFlowerType type) {
        this.setGeneratedBlock(flowerIn, type);
    }

    public void setGeneratedBlock(WerewolfFlower flowerIn, WerewolfFlower.EnumFlowerType typeIn) {
        this.flower = flowerIn;
        this.state = flowerIn.getDefaultState().withProperty(WerewolfFlower.TYPE, typeIn);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (worldIn.isAirBlock(position) && (!worldIn.provider.isNether() || position.getY() < 255) && this.flower.canBlockStay(worldIn, position, this.state)) {
            worldIn.setBlockState(position, this.state, 2);
        }
        return true;
    }

}
