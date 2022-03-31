package de.teamlapen.werewolves.modcompat.terrablender;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.function.Consumer;

/**
 * Copied and modifier from {@link de.teamlapen.vampirism.modcompat.terrablender.TerraBlenderRegistration}
 * <p>
 * Simple provider to add our biome to the overworld
 */
public class TerraBlenderRegistration {

    private static final ResourceLocation ID = new ResourceLocation(REFERENCE.MODID, "overworld");

    public static void registerRegions() {
        Regions.register(new ForestRegion());
    }

    static class ForestRegion extends Region {
        public ForestRegion() {
            super(new ResourceLocation(REFERENCE.MODID, "overworld"), RegionType.OVERWORLD, 2);
        }

        @Override
        public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
            this.addBiomeSimilar(mapper, Biomes.FOREST, ModBiomes.WEREWOLF_HEAVEN);
        }
    }
}
