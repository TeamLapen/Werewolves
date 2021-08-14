package de.teamlapen.werewolves.mixin;

import net.minecraft.village.PointOfInterestType;
import org.apache.commons.lang3.NotImplementedException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedReturnValue")
@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeInvoker {
    @Invoker("registerBlockStates")
    static PointOfInterestType registerBlockStates_werewolves(PointOfInterestType poit) {
        throw new NotImplementedException("Mixin inject failed");
    }
}
