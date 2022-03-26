package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModFeatures;
import de.teamlapen.vampirism.mixin.MultiNoiseBiomeSourcePresetAccessor;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.modcompat.terrablender.TerraBlenderCompat;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.WerewolfHeavenBiome;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;


public class ModBiomes {
    private static final Logger LOGGER = LogManager.getLogger();

    @ObjectHolder(REFERENCE.MODID + ":werewolf_heaven")
    public static final Biome werewolf_heaven = getNull();
    public static final ResourceKey<Biome> WEREWOLF_HEAVEN_KEY = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(REFERENCE.MODID, "werewolf_heaven"));

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(WerewolfHeavenBiome.createWerewolfHeavenBiome().setRegistryName(WEREWOLF_HEAVEN_KEY.location()));

        BiomeDictionary.addTypes(WEREWOLF_HEAVEN_KEY, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.HILLS);
    }

    static void removeStructuresFromBiomes() {
        VampirismAPI.worldGenRegistry().removeStructureFromBiomes(ModFeatures.hunter_camp.getRegistryName(), Lists.newArrayList(WEREWOLF_HEAVEN_KEY.location()));
    }

    static void addBiomesToGeneratorUnsafe() {
        if (!WerewolvesConfig.COMMON.disableWerewolfHeaven.get()) {
            BiomeManager.addAdditionalOverworldBiomes(WEREWOLF_HEAVEN_KEY);
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(WEREWOLF_HEAVEN_KEY, WerewolvesConfig.COMMON.werewolfHeavenWeight.get()));
        }
    }

    public static void onBiomeLoadingEventAdditions(BiomeLoadingEvent event) {
        if (!WerewolvesConfig.COMMON.disableOreGen.get() && event.getCategory() != Biome.BiomeCategory.THEEND && event.getCategory() != Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WerewolvesBiomeFeatures.silver_ore_placed);
        }
        if (event.getCategory() == Biome.BiomeCategory.FOREST) {
            WerewolvesBiomeFeatures.addWerewolvesFlowers(event.getGeneration());
        }

        if (event.getCategory() == Biome.BiomeCategory.TAIGA) {
            event.getSpawns().addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.human_werewolf, 3, 1, 1));
            event.getSpawns().addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_survivalist, 1, 1, 1));
            event.getSpawns().addSpawn(WerewolvesMod.WEREWOLF_CREATURE_TYPE, new MobSpawnSettings.SpawnerData(ModEntities.werewolf_beast, 1, 1, 1));
        }
    }

    public static void addBiomesToOverworldUnsafe() {
        if (TerraBlenderCompat.areBiomesAddedViaTerraBlender()) {
            LOGGER.info("Werewolvse Biomes are added via TerraBlender. Not adding them to overworld preset.");
            return;
        }
        if (WerewolvesConfig.COMMON.disableWerewolfHeaven.get()) {
            return;
        }

        final Function<Registry<Biome>, Climate.ParameterList<Supplier<Biome>>> originalParameterSourceFunction = ((MultiNoiseBiomeSourcePresetAccessor) MultiNoiseBiomeSource.Preset.OVERWORLD).getPresetSupplier_vampirism();


        Function<Registry<Biome>, Climate.ParameterList<Supplier<Biome>>> wrapperParameterSourceFunction = (registry) -> {
            //Create copy of vanilla list
            Climate.ParameterList<Supplier<Biome>> vanillaList = originalParameterSourceFunction.apply(registry);
            List<Pair<Climate.ParameterPoint, Supplier<Biome>>> biomes = new ArrayList<>(vanillaList.values());

            //Setup parameter point (basically the volume in the n-d parameter space) at which the biome should be generated
            //Order of parameters: Temp , humidity, continentalness, erosion, depth, weirdness
            Climate.ParameterPoint[] forestPoints = new Climate.ParameterPoint[]{
                    Climate.parameters(Climate.Parameter.span(-0.40F, -0.19F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(-0.11F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(1), Climate.Parameter.span(-0.56666666F, -0.05F), 0),
                    Climate.parameters(Climate.Parameter.span(-0.40F, -0.19F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(-0.11F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(2), Climate.Parameter.span(-0.56666666F, -0.05F), 0),
                    Climate.parameters(Climate.Parameter.span(-0.40F, -0.19F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(-0.11F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(1), Climate.Parameter.span(0.05f, 0.4F), 0),
                    Climate.parameters(Climate.Parameter.span(-0.40F, -0.19F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(-0.11F, 0.55F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.point(2), Climate.Parameter.span(0.05f, 0.4F), 0)
            };


            //Remove vanilla biomes that are completely inside the given range
            int oldCount = biomes.size();
            int removed = 0;
            Iterator<Pair<Climate.ParameterPoint, Supplier<Biome>>> it = biomes.iterator();
            while (it.hasNext()) {
                Pair<Climate.ParameterPoint, Supplier<Biome>> pair = it.next();
                //It should be safe to get the biome here because {@link BiomeSource} does so as well right after this function call
                ResourceLocation biomeId = pair.getSecond().get().getRegistryName();
                if (biomeId != null && "minecraft".equals(biomeId.getNamespace()) && Arrays.stream(forestPoints).anyMatch(p -> intersects(p, pair.getFirst()))) {
                    it.remove();
                    removed++;
                    LOGGER.debug("Removing biome {} from parameter point {} in overworld preset", biomeId, pair.getFirst());
                }
            }
            LOGGER.debug("Removed a total of {} points from {}", removed, oldCount);


            LOGGER.info("Adding biome {} to ParameterPoints {} in Preset.OVERWORLD", ModBiomes.WEREWOLF_HEAVEN_KEY.location(), Arrays.toString(forestPoints));
            for (Climate.ParameterPoint forestPoint : forestPoints) {
                biomes.add(Pair.of(forestPoint, () -> registry.get(ModBiomes.WEREWOLF_HEAVEN_KEY)));
            }

            return new Climate.ParameterList<>(biomes);
        };


        ((MultiNoiseBiomeSourcePresetAccessor) MultiNoiseBiomeSource.Preset.OVERWORLD).setPresetSupplier_vampirism(wrapperParameterSourceFunction);
    }

    private static boolean intersects(Climate.ParameterPoint a, Climate.ParameterPoint b) {
        return intersects(a.temperature(), b.temperature()) && intersects(a.humidity(), b.humidity()) && intersects(a.continentalness(), b.continentalness()) && intersects(a.erosion(), b.erosion()) && intersects(a.depth(), b.depth()) && intersects(a.weirdness(), b.weirdness());
    }

    private static boolean intersects(Climate.Parameter a, Climate.Parameter b) {
        return (a.max() > b.min() && a.min() < b.max()) || (a.max() == a.min() && b.max() == b.min() && a.max() == b.max());
    }
}
