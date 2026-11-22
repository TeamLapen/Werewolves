package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.common.core.ModItems;
import de.teamlapen.vampirism.data.loot.functions.SetVampireBookFunction;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import java.util.function.BiConsumer;
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

    /**
     * Added to the alpha werewolf loot by {@link CompatGlobalLootModifierProvider}
     */
    public static final ResourceKey<LootTable> ALPHA_WEREWOLF_VAMPIRE_BOOK = ResourceKey.create(Registries.LOOT_TABLE, WIdentifier.mod("alpha_werewolf_vampire_book"));

    public static LootTableProvider getProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(output, Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(CompatLootTablesProvider.CompatBlockLootTables::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(CompatLootTablesProvider.CompatEntityLootTables::new, LootContextParamSets.ENTITY)
                ), lookupProvider);
    }

    private record CompatEntityLootTables(HolderLookup.Provider registries) implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            output.accept(ALPHA_WEREWOLF_VAMPIRE_BOOK, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("vampire_book")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.05f, 0.025f))
                            .add(LootItem.lootTableItem(ModItems.VAMPIRE_BOOK.get()).apply(SetVampireBookFunction.special()))));
        }
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
