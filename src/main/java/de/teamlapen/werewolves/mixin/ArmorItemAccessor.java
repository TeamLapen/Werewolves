package de.teamlapen.werewolves.mixin;

import net.minecraft.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(ArmorItem.class)
public interface ArmorItemAccessor {

    @Accessor("ARMOR_MODIFIER_UUID_PER_SLOT")
    static UUID[] getARMOR_MODIFIERS() {
        throw new IllegalStateException("Injection failed");
    }
}
