package de.teamlapen.werewolves.world.gen;

import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.config.CommonConfig;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviderType;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class ConfigIntProvider extends IntProvider {

    public static final Codec<ConfigIntProvider> CODEC = Codec.STRING.fieldOf("configFieldName").xmap(ConfigIntProvider::new, (p_236775_0_) -> p_236775_0_.supplier.getPath()).codec();

    public static ConfigIntProvider of(CommonConfig.IntValueExt supplier) {
        return new ConfigIntProvider(supplier);
    }

    private final CommonConfig.IntValueExt supplier;

    private ConfigIntProvider(String fieldName) {
        CommonConfig.IntValueExt supplier1;
        Field field = ObfuscationReflectionHelper.findField(CommonConfig.class, fieldName);
        try {
            supplier1 = (CommonConfig.IntValueExt) field.get(WerewolvesConfig.COMMON);
        } catch (IllegalAccessException e) {
            supplier1 = null;
        }
        this.supplier = supplier1;
    }
    protected ConfigIntProvider(CommonConfig.IntValueExt supplier) {
        this.supplier = supplier;
    }

    @Override
    public int sample(@NotNull RandomSource randomSource) {
        return supplier.get();
    }

    @Override
    public int getMinValue() {
        return supplier.getMinValue();
    }

    @Override
    public int getMaxValue() {
        return supplier.getMaxValue();
    }

    @Override
    public @NotNull IntProviderType<?> getType() {
        return WerewolvesBiomeFeatures.CONFIG_INT_PROVIDER.get();
    }
}
