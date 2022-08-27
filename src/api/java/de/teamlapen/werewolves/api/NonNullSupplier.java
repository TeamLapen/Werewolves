package de.teamlapen.werewolves.api;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface NonNullSupplier<T> extends Supplier<T> {

    @NotNull
    @Override
    T get();
}
