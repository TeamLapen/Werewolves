package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlocks;
import de.teamlapen.werewolves.misc.mixin.VanillaBlockLootAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class CompatLootTablesProvider {

    public static LootTableProvider getProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(output, Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(CompatLootTablesProvider.CompatBlockLootTables::new, LootContextParamSets.BLOCK)
                ), lookupProvider);
    }

    private static class CompatBlockLootTables extends BlockLootSubProvider {

        protected CompatBlockLootTables(HolderLookup.Provider provider) {
            super(VanillaBlockLootAccessor.getEXPLOSION_RESISTANT(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER.get());
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get());
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER_STRONG.get());
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER_CORE.get());
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER_CORE_STRONG.get());
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER_CORE_LONG.get());
        }

        protected Iterable<Block> getKnownBlocks() {
            return CompatBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
        }

    }
}
