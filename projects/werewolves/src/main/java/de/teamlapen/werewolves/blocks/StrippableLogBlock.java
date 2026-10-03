package de.teamlapen.werewolves.blocks;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
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
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (itemAbility == ItemAbilities.AXE_STRIP) {
            return stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
        }
        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }

}
