package de.teamlapen.werewolves.mixin;

import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockFamilies.class)
public interface BlockFamiliesAccessor {

    @Invoker("familyBuilder")
    static BlockFamily.Builder familyBuilder(Block block) {
        throw new UnsupportedOperationException();
    }
}
