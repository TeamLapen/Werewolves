package de.teamlapen.werewolves.modcompat.terrablender;

import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import terrablender.api.BiomeProvider;
import terrablender.api.BiomeProviders;
import terrablender.worldgen.TBClimate;

import java.util.function.Consumer;

/**
 * Copied and modifier from {@link de.teamlapen.vampirism.modcompat.terrablender.TerraBlenderBiomeProvider}
 * <p>
 * Simple provider to add our biome to the overworld
 */
public class TerraBlenderBiomeProvider extends BiomeProvider {

    private static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "overworld");

    public static void register() {
        BiomeProviders.register(new TerraBlenderBiomeProvider());
    }

    public TerraBlenderBiomeProvider() {
        super(ID, 2);
    }

    @Override
    public void addOverworldBiomes(Registry<Biome> registry, Consumer<com.mojang.datafixers.util.Pair<TBClimate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addBiomeSimilar(mapper, Biomes.FOREST, ModBiomes.WEREWOLF_HEAVEN_KEY);
    }
}
