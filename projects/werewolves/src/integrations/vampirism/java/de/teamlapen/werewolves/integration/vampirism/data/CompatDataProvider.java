package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.common.core.ModRegistries;
import de.teamlapen.vampirism.data.provider.ModRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CompatDataProvider {

    /**
     * Registered in {@link de.teamlapen.werewolves.integration.vampirism.common.CompatServices}.
     * Only runs in the {@code dataVampirism} run, which writes into src/integrations/vampirism/generated/resources.
     */
    public static void onGatherData(GatherDataEvent.Client event) {
        if (!"vampirism".equals(System.getProperty("werewolves.datagen.integration"))) return;
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProviderFuture = event.getLookupProvider();


        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(packOutput, lookupProviderFuture, ModRegistries.DATA_BUILDER, Set.of(REFERENCE.MODID));
        lookupProviderFuture = provider.getRegistryProvider();

        CompatTagsProvider.register(generator, packOutput, lookupProviderFuture);
        generator.addProvider(true, new CompatRecipeProvider.Runner(packOutput, lookupProviderFuture));
        generator.addProvider(true, new ModModelProvider(packOutput));
        generator.addProvider(true, CompatLootTablesProvider.getProvider(packOutput, lookupProviderFuture));
    }
}
