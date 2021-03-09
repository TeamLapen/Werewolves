package de.teamlapen.werewolves.util;

import net.minecraft.nbt.CompoundNBT;

import java.util.function.Consumer;

public class NBTHelper {

    public static boolean containsString(CompoundNBT nbt, String key) {
        return nbt.contains(key, 8);
    }

    public static boolean containsLong(CompoundNBT nbt, String key) {
        return nbt.contains(key, 99);
    }

    public static CompoundNBT nbtWith(Consumer<CompoundNBT> data) {
        CompoundNBT nbt = new CompoundNBT();
        data.accept(nbt);
        return nbt;
    }
}
