package de.teamlapen.werewolves.util;

import net.minecraft.nbt.CompoundTag;

import java.util.function.Consumer;

public class NBTHelper {

    public static boolean containsString(CompoundTag nbt, String key) {
        return nbt.contains(key, 8);
    }

    public static boolean containsLong(CompoundTag nbt, String key) {
        return nbt.contains(key, 99);
    }

    public static CompoundTag nbtWith(Consumer<CompoundTag> data) {
        CompoundTag nbt = new CompoundTag();
        data.accept(nbt);
        return nbt;
    }
}
