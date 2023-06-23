package de.teamlapen.werewolves.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MapColor;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksAccessor {

    @Invoker("log")
    static RotatedPillarBlock createLogBlock_werewolves(MapColor topColor, MapColor barkColor) {
        throw new NotImplementedException("Mixin inject failed");
    }
}
