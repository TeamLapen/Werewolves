package de.teamlapen.werewolves.common.world.blocks;

import net.minecraft.world.item.ItemStackTemplate;

import com.mojang.serialization.MapCodec;
import de.teamlapen.werewolves.common.world.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.common.core.ModBlockEntities;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import static net.minecraft.world.level.block.CampfireBlock.makeParticles;

public class StoneAltarBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE = makeShape();
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty SOUL_FIRE = WUtils.SOUL_FIRE;
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final String REG_NAME = "stone_altar";
    protected static final MapCodec<StoneAltarBlock> CODEC = simpleCodec(StoneAltarBlock::new);

    public StoneAltarBlock() {
        this(Block.Properties.of().mapColor(MapColor.STONE).noOcclusion().lightLevel((state) -> state.getValue(LIT) ? 14 : 0));
    }
    public StoneAltarBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(WATERLOGGED, false).setValue(SOUL_FIRE, false).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.SIGNAL_FIRE, false));
    }

    protected static VoxelShape makeShape() {
        VoxelShape a = Block.box(3, 0, 3, 13, 7, 13);
        VoxelShape b = Block.box(1, 7, 1, 15, 10, 15);

        return Shapes.or(a, b);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.MODEL;
    }



    @Override
    public boolean isBurning(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        return state.getValue(LIT);
    }

    @Override
    public boolean placeLiquid(@Nonnull LevelAccessor world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull FluidState fluid) {
        if (SimpleWaterloggedBlock.super.placeLiquid(world, pos, state, fluid)) {
            if (state.getValue(LIT)) {
                world.setBlock(pos, state.setValue(LIT, false), 3);
                if (world.isClientSide()) {
                    for (int i = 0; i < 20; ++i) {
                        makeParticles((Level) world, pos.above(1), false, true);
                    }
                } else {
                    ((StoneAltarBlockEntity) world.getBlockEntity(pos)).aboardRitual();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void onPlace(@Nonnull BlockState state, @Nonnull Level world, @Nonnull BlockPos pos, @Nonnull BlockState state2, boolean p_220082_5_) {
        super.onPlace(state, world, pos, state2, p_220082_5_);
        if (state.getValue(LIT) && state2.getBlock() == this && !state2.getValue(LIT)) {
            ((StoneAltarBlockEntity) world.getBlockEntity(pos)).startRitual(state);
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nonnull
    @Override
    public InteractionResult useWithoutItem(@Nonnull BlockState state, Level worldIn, @Nonnull BlockPos pos, Player player, @Nonnull BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(InteractionHand.MAIN_HAND);
        StoneAltarBlockEntity te = ((StoneAltarBlockEntity) worldIn.getBlockEntity(pos));
        if (!worldIn.isClientSide() && te != null) {
            StoneAltarBlockEntity.Result result = te.canActivate(player);
            if (!state.getValue(LIT)) {
                switch (result) {
                    case OTHER_FACTION -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.wrong_faction"));
                        return InteractionResult.CONSUME;
                    }
                    case IS_RUNNING -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.ritual_still_running"));
                        return InteractionResult.CONSUME;
                    }
                }
                switch (result) {
                    case NIGHT_ONLY -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.ritual_night_only"));
                        return InteractionResult.CONSUME;
                    }
                    case WRONG_LEVEL -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.ritual_wrong_level"));
                        return InteractionResult.CONSUME;
                    }
                    case STRUCTURE_LESS -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.ritual_structures_missing"));
                        return InteractionResult.CONSUME;
                    }
                    case STRUCTURE_LIT -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.ritual_less_lit_structures"));
                        return InteractionResult.CONSUME;
                    }
                    case TO_LESS_BLOOD -> {
                        player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.ritual_to_less_prey"));
                        return InteractionResult.CONSUME;
                    }
                    case INV_MISSING -> {
                        Map<Item, Integer> missing = te.getMissingItems();
                        MutableComponent s = Component.translatable("text.werewolves.stone_altar.ritual_missing_items");
                        missing.forEach((item, integer) -> s.append(" ").append(Component.translatable(item.getDescriptionId()).withStyle((style -> {
                            return style.withColor(ChatFormatting.AQUA).withHoverEvent(new HoverEvent.ShowItem(new ItemStackTemplate(item, integer)));
                        }))).append(" " + integer));
                        player.sendOverlayMessage(s);
                        player.openMenu(te);
                    }
                    case OK -> {
                        if (state.getValue(WATERLOGGED)) {
                            player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.can_not_burn"));
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
                            player.sendOverlayMessage(Component.translatable("text.werewolves.stone_altar.empty_hand"));
                            return InteractionResult.PASS;
                        }
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return ModBlockEntities.STONE_ALTAR.get().create(pos, state);
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(WATERLOGGED).add(SOUL_FIRE).add(HORIZONTAL_FACING).add(BlockStateProperties.SIGNAL_FIRE);
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
    public @NotNull BlockState rotate(BlockState state, @NotNull LevelAccessor world, @NotNull BlockPos pos, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public void animateTick(BlockState stateIn, @Nonnull Level worldIn, @Nonnull BlockPos pos, @Nonnull RandomSource rand) {
        if (stateIn.getValue(LIT)) {
            Vector3f offset = getTorchOffset(stateIn);
            worldIn.addParticle(stateIn.getValue(SOUL_FIRE) ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME, pos.getX() + offset.x(), pos.getY() + offset.y(), pos.getZ() + offset.z(), 0.0D, 0.0D, 0.0D);
        }
    }

    private Vector3f getTorchOffset(BlockState stateIn) {
        Vector3f vec = new Vector3f(0.5f, 15.4f / 16, 0.5f);
        switch (stateIn.getValue(HORIZONTAL_FACING)) {
            case EAST -> vec.add(new Vector3f(4.5f / 16, 0, 4.5f / 16));
            case SOUTH -> vec.add(new Vector3f(-4.5f / 16, 0, 4.5f / 16));
            case WEST -> vec.add(new Vector3f(-4.5f / 16, 0, -4.5f / 16));
            default -> vec.add(new Vector3f(4.5f / 16, 0, -4.5f / 16));
        }
        return vec;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.STONE_ALTAR.get(), StoneAltarBlockEntity::tick);
    }
}
