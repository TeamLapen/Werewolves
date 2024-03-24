package de.teamlapen.werewolves.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Supplier;

public class CommonConfig {

    public final ModConfigSpec.BooleanValue disableWerewolfForest;
    public final ModConfigSpec.IntValue werewolfBiomeWeightTerrablender;
    public final IntValueExt silverOreWeight;

    CommonConfig(ModConfigSpec.Builder builder) {
        builder.push("common_server");
        this.werewolfBiomeWeightTerrablender = builder.comment("Only considered if terrablender installed. Heigher values increase Werewolf region weight (likelyhood to appear)").defineInRange("werewolfBiomeWeightTerrablender", 2, 1, 1000);
        this.disableWerewolfForest = builder.comment("Disable werewolf forest generation").define("disableWerewolfForest", false);
        this.silverOreWeight = new IntValueExt(builder.comment("Weight of silver ore spawning"), "silverOreWeight", 30, 0, 256);
        builder.pop();
    }

    public static class IntValueExt implements Supplier<Integer> {

        private final ModConfigSpec.IntValue value;
        private final int min;
        private final int max;
        private final String path;

        public IntValueExt(ModConfigSpec.Builder value, String name, int defaultValue, int min, int max) {
            this.value = value.defineInRange(name, defaultValue, min, max);
            this.min = min;
            this.max = max;
            this.path = name;
        }

        public String getPath() {
            return path;
        }

        public int getMinValue() {
            return this.min;
        }

        public int getMaxValue() {
            return this.max;
        }

        @Override
        public Integer get() {
            return value.get();
        }
    }
}
