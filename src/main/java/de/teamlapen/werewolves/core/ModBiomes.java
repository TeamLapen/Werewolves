package de.teamlapen.werewolves.core;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.api.world.IFactionBiome;
import de.teamlapen.werewolves.config.WerewolvesConfig;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.ModBiomeFeatures;
import de.teamlapen.werewolves.world.WerewolfHeavenBiome;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModBiomes extends de.teamlapen.vampirism.core.ModBiomes {

    public static final WerewolfHeavenBiome werewolf_heaven = getNull();

    static void registerBiomes(IForgeRegistry<Biome> registry) {
        registry.register(new WerewolfHeavenBiome().setRegistryName(REFERENCE.MODID, "werewolf_heaven"));
    }

    static void addBiomes() {
        BiomeDictionary.addTypes(werewolf_heaven, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.DENSE, BiomeDictionary.Type.MAGICAL);
        if (!WerewolvesConfig.SERVER.disableWerewolfHeaven.get()) {
            BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(werewolf_heaven, WerewolvesConfig.BALANCE.UTIL.werewolfHeavenWeight.get()));
        }
    }

    static void addFeatures() {
        for(Biome biome: ForgeRegistries.BIOMES.getValues()) {
            if(BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)) {
                if(biome instanceof IFactionBiome) continue;
                ModBiomeFeatures.addWerewolvesFlowers(biome);
            }
        }
    }

    static void setUpOreGen() {
        List<Biome> oreBiomes = Lists.newArrayList(ForgeRegistries.BIOMES);
        oreBiomes.removeIf(biome -> biome.getCategory().equals(Biome.Category.THEEND) || biome.getCategory().equals(Biome.Category.NETHER) || biome instanceof IFactionBiome);
        for (Biome biome : oreBiomes) {
            biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.silver_block.getDefaultState(), 5)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 0, 0, 45))));
        }
    }
}
