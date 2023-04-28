package de.teamlapen.werewolves.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Supplier;

public class CommonConfig {

    public final ForgeConfigSpec.BooleanValue disableWerewolfForest;
    public final ForgeConfigSpec.IntValue werewolfBiomeWeightTerrablender;
    public final IntValueExt silverOreWeight;

    CommonConfig(ForgeConfigSpec.Builder builder) {
        builder.push("common_server");
        this.werewolfBiomeWeightTerrablender = builder.comment("Only considered if terrablender installed. Heigher values increase Werewolf region weight (likelyhood to appear)").defineInRange("werewolfBiomeWeightTerrablender", 2, 1, 1000);
        this.disableWerewolfForest = builder.comment("Disable werewolf forest generation").define("disableWerewolfForest", false);
        this.silverOreWeight = new IntValueExt(builder.comment("Weight of silver ore spawning"), "silverOreWeight", 30, 0, 256);
        builder.pop();
    }

    public static class IntValueExt implements Supplier<Integer> {

        private final ForgeConfigSpec.IntValue value;
        private final int min;
        private final int max;
        private final String path;

        public IntValueExt(ForgeConfigSpec.Builder value, String name, int defaultValue, int min, int max) {
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
