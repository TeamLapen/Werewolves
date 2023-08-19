package de.teamlapen.werewolves.world.gen;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.mixin.MultiNoiseBiomeSourceParameterListPresetAccessor;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.modcompat.terrablender.TerraBlenderCompat;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class OverworldModifications {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void addBiomesToOverworldUnsafe() {
        if (TerraBlenderCompat.areBiomesAddedViaTerraBlender()) { //If we are already adding the biome to the overworld using TerraBlender, we shouldn't hack it into the overworld preset
            LOGGER.info("Werewolves Biomes are added via TerraBlender. Not adding them to overworld preset.");
            return;
        }
        if (WerewolvesConfig.COMMON.disableWerewolfForest.get()) {
            return;
        }

        /*
         * Hack the vampire forest into the Overworld biome list preset, replacing some taiga biome areas.
         *
         * Create a wrapper function for the parameterSource function, which calls the original one and then modifies the result
         */

        final MultiNoiseBiomeSourceParameterList.Preset.SourceProvider originalParameterSourceFunction = ((MultiNoiseBiomeSourceParameterListPresetAccessor) (Object) MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD).getProvider();


        MultiNoiseBiomeSourceParameterList.Preset.SourceProvider wrapperParameterSourceFunction = new MultiNoiseBiomeSourceParameterList.Preset.SourceProvider() {

            @Override
            public <T> Climate.@NotNull ParameterList<T> apply(@NotNull Function<ResourceKey<Biome>, T> keyFunction) {
                //Create copy of vanilla list
                Climate.ParameterList<Holder<Biome>> vanillaList = originalParameterSourceFunction.apply((Function<ResourceKey<Biome>, Holder<Biome>>) keyFunction);
                List<Pair<Climate.ParameterPoint, Holder<Biome>>> biomes = new ArrayList<>(vanillaList.values());

                //Setup parameter point (basically the volume in the n-d parameter space) at which the biome should be generated
                //Order of parameters: Temp , humidity, continentalness, erosion, depth, weirdness
                Climate.ParameterPoint[] forestPoints = new Climate.ParameterPoint[]{
                        Climate.parameters(Climate.Parameter.span(0.19F, 0.4F), Climate.Parameter.span(-0.25F, 0.25F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(0), Climate.Parameter.span(-0.2F, 0.2F), 0),
//                    Climate.parameters(Climate.Parameter.span(0.19F, 0.4F), Climate.Parameter.span(-0.25F, 0.25F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(1), Climate.Parameter.span(-0.2F, 0.2F), 0),
                        Climate.parameters(Climate.Parameter.span(0.19F, 0.4F), Climate.Parameter.span(-0.25F, 0.25F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(0), Climate.Parameter.span(0.2f, 0.6F), 0),
//                    Climate.parameters(Climate.Parameter.span(0.19F, 0.4F), Climate.Parameter.span(-0.25F, 0.25F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(1), Climate.Parameter.span(0.2f, 0.6F), 0)
                };


                //Remove vanilla biomes that are completely inside the given range
                int oldCount = biomes.size();
                int removed = 0;
                Iterator<Pair<Climate.ParameterPoint, Holder<Biome>>> it = biomes.iterator();
                while (it.hasNext()) {
                    Pair<Climate.ParameterPoint, Holder<Biome>> pair = it.next();
                    //It should be safe to get the biome here because {@link BiomeSource} does so as well right after this function call
                    removed += pair.getSecond().unwrapKey().map(biomeId -> {
                        if ("minecraft".equals(biomeId.location().getNamespace()) && Arrays.stream(forestPoints).anyMatch(p -> intersects(p, pair.getFirst()))) {
                            it.remove();
                            LOGGER.debug("Removing biome {} from parameter point {} in overworld preset", biomeId, pair.getFirst());
                            return 1;
                        }
                        return 0;
                    }).orElse(0);

                }
                LOGGER.debug("Removed a total of {} points from {}", removed, oldCount);


                LOGGER.info("Adding biome {} to ParameterPoints {} in Preset.OVERWORLD", ModBiomes.WEREWOLF_FOREST, Arrays.toString(forestPoints));
                for (Climate.ParameterPoint forestPoint : forestPoints) {
                    biomes.add(Pair.of(forestPoint, (Holder<Biome>) keyFunction.apply(ModBiomes.WEREWOLF_FOREST)));
                }

                return (Climate.ParameterList<T>) new Climate.ParameterList<>(biomes);
            }
        };


        ((MultiNoiseBiomeSourceParameterListPresetAccessor) (Object) MultiNoiseBiomeSourceParameterList.Preset.OVERWORLD).setProvider(wrapperParameterSourceFunction);
    }

    private static boolean intersects(Climate.ParameterPoint a, Climate.ParameterPoint b) {
        return intersects(a.temperature(), b.temperature()) && intersects(a.humidity(), b.humidity()) && intersects(a.continentalness(), b.continentalness()) && intersects(a.erosion(), b.erosion()) && intersects(a.depth(), b.depth()) && intersects(a.weirdness(), b.weirdness());
    }

    private static boolean intersects(Climate.Parameter a, Climate.Parameter b) {
        return (a.max() > b.min() && a.min() < b.max()) || (a.max() == a.min() && b.max() == b.min() && a.max() == b.max());
    }
}
