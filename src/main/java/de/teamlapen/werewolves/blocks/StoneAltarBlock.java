package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.blocks.entity.StoneAltarTileEntity;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Random;

import static net.minecraft.block.CampfireBlock.makeParticles;

@ParametersAreNonnullByDefault
public class StoneAltarBlock extends ContainerBlock implements IWaterLoggable {
    protected static final VoxelShape SHAPE = makeShape();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SOUL_FIRE = WUtils.SOUL_FIRE;
    public static final String REG_NAME = "stone_altar";

    public StoneAltarBlock() {
        super(Block.Properties.of(Material.STONE).noOcclusion().lightLevel((state) -> state.getValue(LIT)?14:0));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(WATERLOGGED, false).setValue(SOUL_FIRE, false));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.box(2, 0, 2, 14, 9.5, 14);

        VoxelShape b1 = Block.box(0, 7, 0, 2, 12, 2);
        VoxelShape b2 = Block.box(14, 7, 14, 16, 12, 16);
        VoxelShape b3 = Block.box(14, 7, 0, 16, 12, 2);
        VoxelShape b4 = Block.box(0, 7, 14, 2, 12, 16);

        VoxelShape c1 = Block.box(7, 7, 0, 9, 12, 2);
        VoxelShape c2 = Block.box(0, 7, 7, 2, 12, 9);
        VoxelShape c3 = Block.box(7, 7, 14, 9, 12, 16);
        VoxelShape c4 = Block.box(14, 7, 7, 16, 12, 9);

        VoxelShape d1 = Block.box(1, 7, 1, 15, 11, 2);
        VoxelShape d2 = Block.box(1, 7, 1, 2, 11, 15);
        VoxelShape d3 = Block.box(15, 7, 15, 14, 11, 2);
        VoxelShape d4 = Block.box(15, 7, 15, 2, 11, 14);

        return VoxelShapes.or(a, b1, b2, b3, b4, c1, c2, c3, c4, d1, d2, d3, d4);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            this.dropItems(worldIn, pos);
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
        return state.getValue(LIT);
    }

    @Override
    public boolean placeLiquid(IWorld world, BlockPos pos, BlockState state, FluidState fluid) {
        if (IWaterLoggable.super.placeLiquid(world, pos, state, fluid)){
            if (state.getValue(LIT)) {
                world.setBlock(pos, state.setValue(LIT, false), 3);
                if (world.isClientSide()) {
                    for (int i = 0; i < 20; ++i) {
                        makeParticles((World) world, pos.above(1), false, true);
                    }
                } else {
                    ((StoneAltarTileEntity) world.getBlockEntity(pos)).aboardRitual();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState state2, boolean p_220082_5_) {
        super.onPlace(state, world, pos, state2, p_220082_5_);
        if (state.getValue(LIT) && state2.getBlock() == this && !state2.getValue(LIT)) {
            ((StoneAltarTileEntity) world.getBlockEntity(pos)).startRitual(state);
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) :super.getFluidState(state);
    }

    @Nonnull
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        StoneAltarTileEntity te = ((StoneAltarTileEntity) worldIn.getBlockEntity(pos));
        if (!worldIn.isClientSide && te != null) {
            StoneAltarTileEntity.Result result = te.canActivate(player);
            if (!state.getValue(LIT)) {
                switch (result) {
                    case OTHER_FACTION:
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.stone_altar.wrong_faction"), true);
                        return ActionResultType.CONSUME;
                    case IS_RUNNING:
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_still_running"), true);
                        return ActionResultType.CONSUME;
                    case NIGHT_ONLY:
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_night_only"), true);
                        return ActionResultType.CONSUME;
                    case WRONG_LEVEL:
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_wrong_level"), true);
                        return ActionResultType.CONSUME;
                    case STRUCTURE_LESS:
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_structures_missing"), true);
                        return ActionResultType.CONSUME;
                    case STRUCTURE_LIT:
                        player.displayClientMessage(new TranslationTextComponent("text.werewolves.stone_altar.ritual_less_lit_structures"), true);
                        return ActionResultType.CONSUME;
                    case INV_MISSING:
                        Map<Item, Integer> missing = te.getMissingItems();

                        IFormattableTextComponent s = new TranslationTextComponent("text.werewolves.stone_altar.ritual_missing_items");
                        missing.forEach((item, integer) -> s.append(" ").append(new TranslationTextComponent(item.getDescriptionId()).withStyle((style -> {
                            return style.withColor(TextFormatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemHover(new ItemStack(item, integer))));
                        }))).append(" " + integer));

                        player.displayClientMessage(s, true);
                        break;
                    case OK:
                        if (state.getValue(WATERLOGGED)) {
                            break;
                        }
                        te.setPlayer(player);
                        if (heldItem.getItem() == Items.TORCH || heldItem.getItem() == Items.SOUL_TORCH) {
                            worldIn.setBlock(pos, state.setValue(LIT, true).setValue(SOUL_FIRE, heldItem.getItem() == Items.SOUL_TORCH), 5);
                            return ActionResultType.CONSUME;
                        }
                }

            }
            if (heldItem.isEmpty()) {
                player.openMenu(te);
                return ActionResultType.CONSUME;
            }
            return ActionResultType.PASS;
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
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return ModTiles.stone_altar.create();
    }

    private void dropItems(World world, BlockPos pos) {
        Random rand = new Random();
        TileEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof IInventory) {
            IInventory inventory = (IInventory) tileEntity;

            for (int i = 0; i < inventory.getContainerSize(); ++i) {
                ItemStack item = inventory.getItem(i);
                if (!item.isEmpty()) {
                    float rx = rand.nextFloat() * 0.8F + 0.1F;
                    float ry = rand.nextFloat() * 0.8F + 0.1F;
                    float rz = rand.nextFloat() * 0.8F + 0.1F;
                    ItemEntity entityItem = new ItemEntity(world, (float) pos.getX() + rx, (float) pos.getY() + ry, (float) pos.getZ() + rz, item.copy());
                    if (item.hasTag()) {
                        entityItem.getItem().setTag(item.getTag().copy());
                    }

                    float factor = 0.05F;
                    entityItem.setDeltaMovement(rand.nextGaussian() * (double) factor, rand.nextGaussian() * (double) factor + 0.20000000298023224D, rand.nextGaussian() * (double) factor);
                    world.addFreshEntity(entityItem);
                    inventory.setItem(i, ItemStack.EMPTY);
                }
            }

        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED).add(SOUL_FIRE);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(LIT)) {
            double d0 = (double) pos.getX() + rand.nextDouble();
            double d1 = (double) pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double d2 = (double) pos.getZ() + rand.nextDouble();
            worldIn.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }
}
