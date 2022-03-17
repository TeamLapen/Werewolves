package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.blocks.entity.StoneAltarTileEntity;
import de.teamlapen.werewolves.core.ModTiles;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.Container;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Random;

import static net.minecraft.block.CampfireBlock.makeParticles;

@ParametersAimport net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;

net.minecraft.world.level.block.CampfireBlocktoneAltarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE = makeShape();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SOUL_FIRE = WUtils.SOUL_FIRE;
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    ;
    public static final String REG_NAME = "stone_altar";

    public StoneAltarBlock() {
        super(Block.Properties.of(Material.STONE).noOcclusion().lightLevel((state) -> state.getValue(LIT) ? 14 : 0));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(WATERLOGGED, false).setValue(SOUL_FIRE, false).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.box(3, 0, 3, 13, 7, 13);
        VoxelShape b = Block.box(1, 7, 1, 15, 10, 15);

        return Shapes.or(a, b);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            this.dropItems(worldIn, pos);
            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean isBurning(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getValue(LIT);
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluid) {
        if (SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid)){
            if (state.getValue(LIT)) {
                world.setBlock(pos, state.setValue(LIT, false), 3);
                if (world.isClientSide()) {
                    for (int i = 0; i < 20; ++i) {
                        makeParticles((Level) world, pos.above(1), false, true);
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
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState state2, boolean p_220082_5_) {
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
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        StoneAltarTileEntity te = ((StoneAltarTileEntity) worldIn.getBlockEntity(pos));
        if (!worldIn.isClientSide && te != null) {
            StoneAltarTileEntity.Result result = te.canActivate(player);
            if (!state.getValue(LIT)) {
                switch (result) {
                    case OTHER_FACTION:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.wrong_faction"), true);
                        return InteractionResult.CONSUME;
                    case IS_RUNNING:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.ritual_still_running"), true);
                        return InteractionResult.CONSUME;
                }
                switch (result) {
                    case NIGHT_ONLY:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.ritual_night_only"), true);
                        return InteractionResult.CONSUME;
                    case WRONG_LEVEL:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.ritual_wrong_level"), true);
                        return InteractionResult.CONSUME;
                    case STRUCTURE_LESS:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.ritual_structures_missing"), true);
                        return InteractionResult.CONSUME;
                    case STRUCTURE_LIT:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.ritual_less_lit_structures"), true);
                        return InteractionResult.CONSUME;
                    case TO_LESS_BLOOD:
                        player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.ritual_to_less_prey"), true);
                        return InteractionResult.CONSUME;
                    case INV_MISSING:
                        Map<Item, Integer> missing = te.getMissingItems();

                        MutableComponent s = new TranslatableComponent("text.werewolves.stone_altar.ritual_missing_items");
                        missing.forEach((item, integer) -> s.append(" ").append(new TranslatableComponent(item.getDescriptionId()).withStyle((style -> {
                            return style.withColor(ChatFormatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(new ItemStack(item, integer))));
                        }))).append(" " + integer));

                        player.displayClientMessage(s, true);
                        player.openMenu(te);
                        break;
                    case OK:
                        if (state.getValue(WATERLOGGED)) {
                            player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.can_not_burn"), true);
                            player.openMenu(te);
                            return InteractionResult.CONSUME;
                        }
                        te.setPlayer(player);
                        if (heldItem.getItem() == Items.TORCH || heldItem.getItem() == Items.SOUL_TORCH) {
                            worldIn.setBlock(pos, state.setValue(LIT, true).setValue(SOUL_FIRE, heldItem.getItem() == Items.SOUL_TORCH), 5);
                            return InteractionResult.CONSUME;
                        }
                        if (heldItem.isEmpty()) {
                            player.openMenu(te);
                            return InteractionResult.CONSUME;
                        } else {
                            player.displayClientMessage(new TranslatableComponent("text.werewolves.stone_altar.empty_hand"), true);
                            return InteractionResult.PASS;
                        }
                }
            }
        }
        return InteractionResult.SUCCESS;
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
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockGetter worldIn) {
        return ModTiles.stone_altar.create();
    }

    private void dropItems(Level world, BlockPos pos) {
        Random rand = new Random();
        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof Container) {
            Container inventory = (Container) tileEntity;

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED).add(SOUL_FIRE).add(HORIZONTAL_FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, context.getHorizontalDirection());
    }

    @Nonnull
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor world, BlockPos pos, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(LIT)) {
            Vector3f offset = getTorchOffset(stateIn);
            worldIn.addParticle(stateIn.getValue(SOUL_FIRE) ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, pos.getX() + offset.x(), pos.getY() + offset.y(), pos.getZ() + offset.z(), 0.0D, 0.0D, 0.0D);
        }
    }

    private Vector3f getTorchOffset(BlockState stateIn) {
        Vector3f vec = new Vector3f(0.5f, 15.4f / 16, 0.5f);
        switch (stateIn.getValue(HORIZONTAL_FACING)) {
            case EAST:
                vec.add(new Vector3f(4.5f / 16, 0, 4.5f / 16));
                break;
            case SOUTH:
                vec.add(new Vector3f(-4.5f / 16, 0, 4.5f / 16));
                break;
            case WEST:
                vec.add(new Vector3f(-4.5f / 16, 0, -4.5f / 16));
                break;
            default:
                vec.add(new Vector3f(4.5f / 16, 0, -4.5f / 16));
                break;
        }
        return vec;
    }
}
