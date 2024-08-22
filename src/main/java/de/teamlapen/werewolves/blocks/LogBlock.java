package de.teamlapen.werewolves.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

public class LogBlock extends RotatedPillarBlock {

    public static final BlockSetType JACARANDA_BLOCK_TYPE = WBlockSetTypes.JACARANDA;
    public static final BlockSetType MAGIC_BLOCK_TYPE = WBlockSetTypes.MAGIC;
    public static final WoodType JACARANDA = WoodType.register(new WoodType("werewolves:jacaranda", JACARANDA_BLOCK_TYPE));
    public static final WoodType MAGIC = WoodType.register(new WoodType("werewolves:magic", MAGIC_BLOCK_TYPE));

    public LogBlock(Properties properties) {
        super(properties);
        ((FireBlock) Blocks.FIRE).setFlammable(this,5,5);
    }

    public LogBlock(MapColor color1, MapColor color2) {
        this(BlockBehaviour.Properties.of().mapColor((p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? color1 : color2;
        }).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }
}
