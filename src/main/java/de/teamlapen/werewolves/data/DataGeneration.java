package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.data.ModBlockFamilies;
import de.teamlapen.werewolves.core.ModRegistries;
import de.teamlapen.werewolves.mixin.RegistriesDatapackGeneratorAccessor;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DataPackRegistriesHooks;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = REFERENCE.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {

    @SuppressWarnings("UnreachableCode")
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockFamilies.init();
        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, ModRegistries.DATA_BUILDER, Set.of(REFERENCE.MODID));
        generator.addProvider(event.includeServer(), provider);
        //noinspection DataFlowIssue
        lookupProvider = ((RegistriesDatapackGeneratorAccessor) provider).getRegistries();
        ModTagsProvider.register(generator, event, packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), new RecipeGenerator(packOutput));
        generator.addProvider(event.includeServer(), new LootTablesGenerator(packOutput));
        generator.addProvider(event.includeServer(), new GlobalLootTableGenerator(packOutput));
        generator.addProvider(event.includeClient(), new ItemModelGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BlockStateGenerator(packOutput, existingFileHelper));
        generator.addProvider(event.includeServer(), new SkillTreeProvider(packOutput, lookupProvider));
    }

    private static CompletableFuture<HolderLookup.Provider> addVampirismRegistries(CompletableFuture<HolderLookup.Provider> lookupProvider, PackOutput packOutput) {
        DatapackBuiltinEntriesProvider vampirism = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, de.teamlapen.vampirism.core.ModRegistries.DATA_BUILDER, Set.of(de.teamlapen.vampirism.REFERENCE.MODID));
        //noinspection DataFlowIssue
        return ((RegistriesDatapackGeneratorAccessor) vampirism).getRegistries();
    }
}
