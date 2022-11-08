package de.teamlapen.werewolves.mixin;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(BlockLoot.class)
public interface BlockLootAccessor {

    @Mutable
    @Final
    @Accessor("EXPLOSION_RESISTANT")
    static void setEXPLOSION_RESISTANT(Set<Item> items) {
        throw new IllegalStateException("Mixin failed to apply");
    }

    @Accessor("EXPLOSION_RESISTANT")
    static Set<Item> getEXPLOSION_RESISTANT() {
        throw new IllegalStateException("Mixin failed to apply");
    }
}
