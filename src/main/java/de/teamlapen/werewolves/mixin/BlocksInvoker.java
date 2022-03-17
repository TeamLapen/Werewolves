package de.teamlapen.werewolves.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.material.MaterialColor;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksInvoker {

    @Invoker("log")
    static RotatedPillarBlock createLogBlock_werewolves(MaterialColor topColor, MaterialColor barkColor) {
        throw new NotImplementedException("Mixin inject failed");
    }
}
