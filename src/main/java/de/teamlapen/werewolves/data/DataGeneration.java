package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.mixin.RegistriesDatapackGeneratorAccessor;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistriesHooks;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = REFERENCE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(packOutput, extendedProvider(lookupProvider, ModRegistries.DATA_BUILDER), Set.of(REFERENCE.MODID));
        generator.addProvider(event.includeServer(), provider);
        //noinspection DataFlowIssue
        lookupProvider = ((RegistriesDatapackGeneratorAccessor) provider).getRegistries();
        ModTagsProvider.register(generator, event, packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), new RecipeGenerator(packOutput));
        generator.addProvider(event.includeServer(), new LootTablesGenerator(packOutput));
        generator.addProvider(event.includeServer(), new GlobalLootTableGenerator(packOutput));
        generator.addProvider(event.includeServer(), new SkillNodeGenerator(packOutput));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, existingFileHelper));
    }

    private static CompletableFuture<HolderLookup.Provider> addVampirismRegistries(CompletableFuture<HolderLookup.Provider> lookupProvider, PackOutput packOutput) {
        DatapackBuiltinEntriesProvider vampirism = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, de.teamlapen.vampirism.core.ModRegistries.DATA_BUILDER, Set.of(de.teamlapen.vampirism.REFERENCE.MODID));
        //noinspection DataFlowIssue
        return ((RegistriesDatapackGeneratorAccessor) vampirism).getRegistries();
    }

    private static CompletableFuture<HolderLookup.Provider> extendedProvider(CompletableFuture<HolderLookup.Provider> future, RegistrySetBuilder... builder) {
        return future.thenApply(provider ->  {
            for (RegistrySetBuilder datapackEntriesBuilder : builder) {
                var builderKeys = new HashSet<>(datapackEntriesBuilder.getEntryKeys());
                DataPackRegistriesHooks.getDataPackRegistriesWithDimensions().filter(data -> !builderKeys.contains(data.key())).forEach(data -> datapackEntriesBuilder.add(data.key(), context -> {}));
            }
            RegistryAccess.Frozen frozen = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
            for (RegistrySetBuilder registrySetBuilder : builder) {
                provider = registrySetBuilder.buildPatch(frozen, provider);
            }
            return provider;
        });
    }
}
