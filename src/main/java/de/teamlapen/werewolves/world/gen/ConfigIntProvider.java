package de.teamlapen.werewolves.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ConfigIntProvider extends IntProvider {

    public static final Codec<ConfigIntProvider> CODEC = Codec.INT.fieldOf("value").xmap(ConfigIntProvider::new, (p_236775_0_) -> p_236775_0_.supplier.get()).codec();

    public static ConfigIntProvider of(Supplier<Integer> supplier) {
        return new ConfigIntProvider(supplier);
    }

    private final Supplier<Integer> supplier;

    private ConfigIntProvider(int value) {
        this.supplier = () -> value;
    }
    protected ConfigIntProvider(Supplier<Integer> supplier) {
        this.supplier = supplier;
    }

    @Override
    public int sample(@NotNull RandomSource randomSource) {
        return supplier.get();
    }

    @Override
    public int getMinValue() {
        return supplier.get();
    }

    @Override
    public int getMaxValue() {
        return supplier.get();
    }

    @Override
    public @NotNull IntProviderType<?> getType() {
        return WerewolvesBiomeFeatures.CONFIG_INT_PROVIDER.get();
    }
}
