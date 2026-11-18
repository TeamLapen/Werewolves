package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.faction.common.core.FactionLootContextParamSets;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModLootTables;
import de.teamlapen.werewolves.data.provider.LootTablesProvider;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlocks;
import de.teamlapen.werewolves.misc.mixin.VanillaBlockLootAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class CompatLootTablesProvider {

    public static LootTableProvider getProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(output, ModLootTables.getLootTables(),
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
            this.dropSelf(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get());
        }
    }
}
