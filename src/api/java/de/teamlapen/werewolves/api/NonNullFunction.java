package de.teamlapen.werewolves.api;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface NonNullFunction<T, R> extends Function<T, R> {

    @NotNull
    @Override
    R apply(T t);
}
