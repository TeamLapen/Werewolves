package de.teamlapen.werewolves.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import de.teamlapen.vampirism.world.gen.modifier.ExtendedAddSpawnsBiomeModifier;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.gen.WerewolvesBiomeFeatures;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BiomeGenerator {

    public static void addProvider(GatherDataEvent event) {
        BiomeGenerator biomeGenerator = new BiomeGenerator(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), biomeGenerator.modifierGenerator());
    }

    private final DataGenerator gen;
    private final ExistingFileHelper exFileHelper;
    private final @NotNull RegistryOps<JsonElement> ops;
    private final @NotNull Registry<Biome> biomes;
    private final @NotNull Registry<PlacedFeature> placedFeatures;

    public BiomeGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        this.gen = gen;
        this.exFileHelper = exFileHelper;
        RegistryAccess access = RegistryAccess.builtinCopy();
        this.ops = RegistryOps.create(JsonOps.INSTANCE, access);
        this.biomes = this.ops.registry(Registry.BIOME_REGISTRY).orElseThrow();
        this.placedFeatures = this.ops.registry(Registry.PLACED_FEATURE_REGISTRY).orElseThrow();
    }

    public @NotNull JsonCodecProvider<BiomeModifier> modifierGenerator() {
        return JsonCodecProvider.forDatapackRegistry(this.gen, this.exFileHelper, REFERENCE.MODID, this.ops, ForgeRegistries.Keys.BIOME_MODIFIERS, getBiomeModifier());
    }

    private @NotNull Map<ResourceLocation, BiomeModifier> getBiomeModifier() {
        Map<ResourceLocation, BiomeModifier> data = new HashMap<>();
        data.put(new ResourceLocation(REFERENCE.MODID, "spawn/werewolf_spawns"), new ExtendedAddSpawnsBiomeModifier(biome(ModTags.Biomes.HasSpawn.WEREWOLF), biome(ModTags.Biomes.NoSpawn.WEREWOLF), Lists.newArrayList(new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(ModEntities.WEREWOLF_BEAST.get(), 1, 1, 1, WerewolvesMod.WEREWOLF_CREATURE_TYPE), new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(ModEntities.WEREWOLF_SURVIVALIST.get(), 1, 1, 1, WerewolvesMod.WEREWOLF_CREATURE_TYPE))));
        data.put(new ResourceLocation(REFERENCE.MODID, "spawn/human_werewolf_spawns"), ExtendedAddSpawnsBiomeModifier.singleSpawn(biome(ModTags.Biomes.HasSpawn.HUMAN_WEREWOLF), biome(ModTags.Biomes.NoSpawn.HUMAN_WEREWOLF), new ExtendedAddSpawnsBiomeModifier.ExtendedSpawnData(ModEntities.HUMAN_WEREWOLF.get(), 1, 1, 1, WerewolvesMod.WEREWOLF_CREATURE_TYPE)));
        data.put(new ResourceLocation(REFERENCE.MODID, "gen/silver_ore"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biome(ModTags.Biomes.HasGen.SILVER_ORE), placedFeature(WerewolvesBiomeFeatures.silver_ore_placed), GenerationStep.Decoration.UNDERGROUND_ORES));
        data.put(new ResourceLocation(REFERENCE.MODID, "gen/wolfsbane"), new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biome(ModTags.Biomes.HasGen.WOLFSBANE), placedFeature(WerewolvesBiomeFeatures.wolfsbane_placed), GenerationStep.Decoration.VEGETAL_DECORATION));
        return data;
    }

    private @NotNull HolderSet<Biome> biome(@NotNull TagKey<Biome> key) {
        return biomes.getOrCreateTag(key);
    }

    private @NotNull HolderSet<PlacedFeature> placedFeature(@SuppressWarnings("SameParameterValue") @NotNull RegistryObject<PlacedFeature> placedFeature) {
        return HolderSet.direct(this.placedFeatures.getHolderOrThrow(Objects.requireNonNull(placedFeature.getKey())));
    }
}
