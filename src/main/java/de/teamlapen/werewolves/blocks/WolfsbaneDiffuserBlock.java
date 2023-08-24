package de.teamlapen.werewolves.blocks;

import de.teamlapen.vampirism.blocks.GarlicDiffuserBlock;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModTiles;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class WolfsbaneDiffuserBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape shape = makeShape();

    private static @NotNull VoxelShape makeShape() {
        VoxelShape a = Block.box(1, 0, 1, 15, 2, 15);
        VoxelShape b = Block.box(3, 2, 3, 13, 12, 13);
        return Shapes.or(a, b);
    }

    private final Type type;

    public WolfsbaneDiffuserBlock(Type type) {
        super(Properties.of().mapColor(MapColor.STONE).strength(3f).sound(SoundType.STONE).noOcclusion());
        this.type = type;
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        switch (this.type) {
            case LONG, IMPROVED -> pTooltip.add(Component.translatable(getDescriptionId() + "." + this.type.getSerializedName()).withStyle(ChatFormatting.AQUA));
        }
        pTooltip.add(Component.translatable("block.werewolves.wolfsbane_diffuser.tooltip").withStyle(ChatFormatting.GRAY));
        int c = VampirismConfig.BALANCE.hsGarlicDiffuserEnhancedDist == null /* During game start config is not yet set*/ ? 1 : 1 + 2 * type.range.get();
        pTooltip.add(Component.translatable("block.vampirism.garlic_diffuser.tooltip2", c, c).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void attack(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
        WolfsbaneDiffuserBlockEntity tile = getTile(pLevel, pPos);
        if (tile != null) {
            tile.onTouched(pPlayer);
        }
    }

    @Override
    public @NotNull String getDescriptionId() {
        return "block.werewolves.wolfsbane_diffuser";
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return shape;
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @NotNull
    @Override
    public BlockState mirror(@NotNull BlockState state, @NotNull Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @NotNull
    @Override
    public BlockState rotate(@NotNull BlockState state, @NotNull Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        var tile = new WolfsbaneDiffuserBlockEntity(pPos, pState);
        tile.initiateBootTimer();
        tile.setType(this.type);
        return tile;
    }

    @Override
    public void playerDestroy(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState, @Nullable BlockEntity pBlockEntity, @NotNull ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
        if (pBlockEntity instanceof WolfsbaneDiffuserBlockEntity wolfbane) {
            wolfbane.onTouched(pPlayer);
        }
    }

    @Override
    public InteractionResult use(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHit) {
        ItemStack heldItem = pPlayer.getItemInHand(pHand);
        if (!heldItem.isEmpty() && ModBlocks.WOLFSBANE.get().asItem() == heldItem.getItem()) {
            if (!pLevel.isClientSide) {
                WolfsbaneDiffuserBlockEntity t = getTile(pLevel, pPos);
                if (t != null) {
                    if (t.getFuelTime() > 0) {
                        pPlayer.displayClientMessage(Component.translatable("block.vampirism.garlic_diffuser.already_fueled"), true);
                    } else {
                        t.onFueled();
                        if (!pPlayer.isCreative()) heldItem.shrink(1);
                        pPlayer.displayClientMessage(Component.translatable("block.vampirism.garlic_diffuser.successfully_fueled"), true);
                    }

                }
            }
            return InteractionResult.SUCCESS;
        } else {
            if (pLevel.isClientSide) {
                WolfsbaneDiffuserBlockEntity t = getTile(pLevel, pPos);
                if (t != null) {
                    WerewolvesMod.proxy.displayWolfsbaneScreen(t, getName());
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    private WolfsbaneDiffuserBlockEntity getTile(@NotNull BlockGetter level, @NotNull BlockPos pos) {
        BlockEntity tile = level.getBlockEntity(pos);
        if (tile instanceof WolfsbaneDiffuserBlockEntity) {
            return (WolfsbaneDiffuserBlockEntity) tile;
        }
        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return createTickerHelper(type, ModTiles.WOLFSBANE_DIFFUSER.get(), WolfsbaneDiffuserBlockEntity::serverTick);
    }

    @SuppressWarnings({"Convert2MethodRef", "FunctionalExpressionCanBeFolded"})
    public enum Type implements StringRepresentable {
        NORMAL("normal", () -> WerewolvesConfig.BALANCE.BLOCKS.wolfsbaneDiffuserNormalDuration.get() * 20, () -> WerewolvesConfig.BALANCE.BLOCKS.wolfsbaneDiffuserNormalDist.get(), 1),
        IMPROVED("improved", () -> WerewolvesConfig.BALANCE.BLOCKS.wolfsbaneDiffuserImprovedDuration.get() * 20, () -> WerewolvesConfig.BALANCE.BLOCKS.wolfsbaneDiffuserImprovedDist.get(), 2),
        LONG("long", () -> WerewolvesConfig.BALANCE.BLOCKS.wolfsbaneDiffuserLongDuration.get() * 20, () -> WerewolvesConfig.BALANCE.BLOCKS.wolfsbaneDiffuserLongDist.get(),1);

        private final String name;
        public final Supplier<Integer> fuelTime;
        public final Supplier<Integer> range;
        public final int amplifier;

        Type(String name, Supplier<Integer> fuelTime, Supplier<Integer> range, int amplifier) {
            this.name = name;
            this.fuelTime = fuelTime;
            this.range = range;
            this.amplifier = amplifier;
        }

        @NotNull
        @Override
        public String getSerializedName() {
            return name;
        }
    }
}