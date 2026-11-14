package de.teamlapen.werewolves.data.provider;

import de.teamlapen.vampirism.data.ModBlockFamilies;
import de.teamlapen.werewolves.common.core.ModRegistries;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
@EventBusSubscriber(modid = REFERENCE.MODID)
public class ModDataProvider {

    @SuppressWarnings("UnreachableCode")
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModBlockFamilies.init();
        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, ModRegistries.DATA_BUILDER, Set.of(REFERENCE.MODID));
        lookupProvider = provider.getRegistryProvider();
        generator.addProvider(true, provider);
        ModTagsProvider.register(generator, event, packOutput, lookupProvider);
        generator.addProvider(true, new ModRecipeProvider.Runner(packOutput, lookupProvider));
        generator.addProvider(true, LootTablesProvider.getProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModGlobalLootModifierProvider(packOutput, lookupProvider));
        generator.addProvider(true, new SkillTreeProvider(packOutput, lookupProvider));
        generator.addProvider(true, new ModAdvancementProvider(packOutput, lookupProvider));
    }

//    private static CompletableFuture<HolderLookup.Provider> addVampirismRegistries(CompletableFuture<HolderLookup.Provider> lookupProvider, PackOutput packOutput) {
//        DatapackBuiltinEntriesProvider vampirism = new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, de.teamlapen.vampirism.core.ModRegistries.DATA_BUILDER, Set.of(de.teamlapen.vampirism.REFERENCE.MODID));
//        //noinspection DataFlowIssue
//        return ((RegistriesDatapackGeneratorAccessor) vampirism).getRegistries();
//    }
}
