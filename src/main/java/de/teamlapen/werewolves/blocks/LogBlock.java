package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.mixin.FireBlockAccessor;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class LogBlock extends RotatedPillarBlock {

    public static final WoodType JACARANDA = WoodType.register(WoodType.create("werewolves:jacaranda"));
    public static final WoodType MAGIC = WoodType.register(WoodType.create("werewolves:magic"));

    public LogBlock(Properties properties) {
        super(properties);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(this,5,5);
    }

    public LogBlock(MaterialColor color1, MaterialColor color2) {
        this(BlockBehaviour.Properties.of(Material.WOOD, (p_235431_2_) -> {
            return p_235431_2_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? color1 : color2;
        }).strength(2.0F).sound(SoundType.WOOD));
    }
}
