package de.teamlapen.werewolves.blocks;

import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModTiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WolfsbaneDiffuserBlock extends BaseEntityBlock {

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
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable BlockGetter pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void attack(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer) {
        WolfsbaneDiffuserBlockEntity tile = getTile(pLevel, pPos);
        if (tile != null) {
            tile.onTouched(pPlayer);
        }
    }

    @Override
    public String getDescriptionId() {
        return "block.werewolves.wolfsbane_diffuser";
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return shape;
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new WolfsbaneDiffuserBlockEntity(pPos, pState);
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

    public enum Type implements StringRepresentable {
        NORMAL("normal"), IMPROVED("improved"), LONG("long");


        private final String name;

        Type(String name) {
            this.name = name;
        }

        public @NotNull String getName() {
            return this.getSerializedName();
        }

        @NotNull
        @Override
        public String getSerializedName() {
            return name;
        }
    }
}