package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.tileentity.StoneAltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class StoneAltarBlock extends ContainerBlock {
    protected static final VoxelShape SHAPE = makeShape();
    public static final String REG_NAME = "stone_altar";

    public StoneAltarBlock() {
        super(Block.Properties.create(Material.ROCK));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.makeCuboidShape(2, 0, 2, 14, 9.5, 14);

        VoxelShape b1 = Block.makeCuboidShape(0, 7, 0, 2, 12, 2);
        VoxelShape b2 = Block.makeCuboidShape(14, 7, 14, 16, 12, 16);
        VoxelShape b3 = Block.makeCuboidShape(14, 7, 0, 16, 12, 2);
        VoxelShape b4 = Block.makeCuboidShape(0, 7, 14, 2, 12, 16);

        VoxelShape c1 = Block.makeCuboidShape(7, 7, 0, 9, 12, 2);
        VoxelShape c2 = Block.makeCuboidShape(0, 7, 7, 2, 12, 9);
        VoxelShape c3 = Block.makeCuboidShape(7, 7, 14, 9, 12, 16);
        VoxelShape c4 = Block.makeCuboidShape(14, 7, 7, 16, 12, 9);

        VoxelShape d1 = Block.makeCuboidShape(1, 7, 1, 15, 11, 2);
        VoxelShape d2 = Block.makeCuboidShape(1, 7, 1, 2, 11, 15);
        VoxelShape d3 = Block.makeCuboidShape(15, 7, 15, 14, 11, 2);
        VoxelShape d4 = Block.makeCuboidShape(15, 7, 15, 2, 11, 14);

        return VoxelShapes.or(a, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof StoneAltarTileEntity) {
            if (((StoneAltarTileEntity) te).getCurrentPhase() == StoneAltarTileEntity.Phase.FOG || ((StoneAltarTileEntity) te).getCurrentPhase() == StoneAltarTileEntity.Phase.STARTING) {
                return 16;
            }
        }
        return 0;
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            this.dropItems(worldIn, pos);
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof StoneAltarTileEntity) {
            return ((StoneAltarTileEntity) te).getCurrentPhase() == StoneAltarTileEntity.Phase.FOG || ((StoneAltarTileEntity) te).getCurrentPhase() == StoneAltarTileEntity.Phase.STARTING;
        }
        return false;
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(handIn);
        StoneAltarTileEntity te = ((StoneAltarTileEntity) worldIn.getTileEntity(pos));
        if (!worldIn.isRemote && te != null) {
            StoneAltarTileEntity.Result result = te.canActivate(player);
            switch (result) {
                case OK:
                    if (heldItem.getItem() == Items.FLINT_AND_STEEL) {
                        te.startRitual(player);
                        return ActionResultType.SUCCESS;
                    }
                    break;
                case OTHER_FACTION:
                    player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.wrong_faction"), true);
                    return ActionResultType.SUCCESS;
                case INV_MISSING:
                    if (heldItem.isEmpty()) {
                        player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_missing_items"), true);
                    }
                    break;
                case IS_RUNNING:
                    player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_still_running"), true);
                    return ActionResultType.SUCCESS;
                case NIGHT_ONLY:
                    player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_night_only"), true);
                    return ActionResultType.SUCCESS;
                case WRONG_LEVEL:
                    player.sendStatusMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_wrong_level"), true);
                    return ActionResultType.SUCCESS;
            }
            player.openContainer(te);
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 2;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return ModTiles.stone_altar.create();
    }

    private void dropItems(World world, BlockPos pos) {
        Random rand = new Random();
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity instanceof IInventory) {
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack item = inventory.getStackInSlot(i);
                if (!item.isEmpty()) {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;
                    ItemEntity entityItem = new ItemEntity(world, (float) pos.getX() + rx, (float) pos.getY() + ry, (float) pos.getZ() + rz, item.copy());
                    if (item.hasTag()) {
                        entityItem.getItem().setTag(item.getTag().copy());
                    }

                    float factor = 0.05F;
                    entityItem.setMotion(rand.nextGaussian() * (double) factor, rand.nextGaussian() * (double) factor + 0.20000000298023224D, rand.nextGaussian() * (double) factor);
                    world.addEntity(entityItem);
                    inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                }
            }

        }
    }
}
