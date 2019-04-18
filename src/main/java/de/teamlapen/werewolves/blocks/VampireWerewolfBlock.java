package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.util.REFERENCE;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

public class VampireWerewolfBlock extends Block {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private final String registeredName;
    private boolean hasFacing = false;

    public VampireWerewolfBlock(String regName, Material materialIn) {
        super(materialIn);
        setCreativeTab(WerewolvesMod.creativeTab);
        setRegistryName(REFERENCE.MODID, regName);
        this.setUnlocalizedName(REFERENCE.MODID + "." + regName);
        this.registeredName = regName;
    }

    /**
     * @return The name this block is registered in the GameRegistry
     */
    public String getRegisteredName() {
        return registeredName;
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        if (hasFacing) {

            return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));

        }
        return state;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        if (hasFacing) {
            return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
        }
        return state;
    }

    protected void setHasFacing() {
        hasFacing = true;
    }

}
