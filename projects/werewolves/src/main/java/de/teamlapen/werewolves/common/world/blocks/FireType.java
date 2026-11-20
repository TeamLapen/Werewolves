package de.teamlapen.werewolves.common.world.blocks;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum FireType implements StringRepresentable {
    DEFAULT,
    SOUL,
    COPPER;

    public static final EnumProperty<FireType> PROPERTY = EnumProperty.create("fire_type", FireType.class);

    @Override
    public String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Nullable
    public static FireType getType(Item item) {
        if (item == Items.TORCH) {
            return DEFAULT;
        } else if (item == Items.SOUL_TORCH) {
            return SOUL;
        } else if (item == Items.COPPER_TORCH) {
            return COPPER;
        }
        return null;
    }
}
