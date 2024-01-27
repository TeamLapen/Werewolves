package de.teamlapen.werewolves.blocks;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class StrippableLogBlock extends LogBlock {

    private final Supplier<? extends LogBlock> stripped;

    public StrippableLogBlock(Properties properties, Supplier<? extends LogBlock> strippedBlock) {
        super(properties);
        this.stripped = strippedBlock;
    }

    public StrippableLogBlock(MapColor color1, MapColor color2, Supplier<? extends LogBlock> strippedBlock) {
        super(color1, color2);
        this.stripped = strippedBlock;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (toolAction == ToolActions.AXE_STRIP) {
            return stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
