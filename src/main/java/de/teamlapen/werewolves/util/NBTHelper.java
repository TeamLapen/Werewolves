package de.teamlapen.werewolves.util;

import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class NBTHelper {

    public static boolean containsString(@NotNull CompoundTag nbt, @NotNull String key) {
        return nbt.contains(key, 8);
    }

    public static boolean containsLong(@NotNull CompoundTag nbt, @NotNull String key) {
        return nbt.contains(key, 99);
    }

    public static @NotNull CompoundTag nbtWith(@NotNull Consumer<CompoundTag> data) {
        CompoundTag nbt = new CompoundTag();
        data.accept(nbt);
        return nbt;
    }
}
