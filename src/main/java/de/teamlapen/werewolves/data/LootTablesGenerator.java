package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModLootTables;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTablesGenerator extends LootTableProvider {

    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};


    public LootTablesGenerator(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK), Pair.of(ModEntityLootTables::new, LootParameterSets.ENTITY), Pair.of(InjectLootTables::new, LootParameterSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((resourceLocation, lootTable) -> LootTableManager.validateLootTable(validationtracker, resourceLocation, lootTable));
    }

    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {
            this.registerLootTable(ModBlocks.totem_top_werewolves_werewolf, LootTable.builder());
            this.registerLootTable(ModBlocks.totem_top_werewolves_werewolf_crafted, dropping(de.teamlapen.vampirism.core.ModBlocks.totem_top));
            this.registerDropSelfLootTable(ModBlocks.jacaranda_sapling);
            this.registerDropSelfLootTable(ModBlocks.magic_sapling);
            this.registerDropSelfLootTable(ModBlocks.wolfsbane);
            this.registerDropSelfLootTable(ModBlocks.silver_block);
            this.registerDropSelfLootTable(ModBlocks.silver_ore);
            this.registerFlowerPot(ModBlocks.potted_wolfsbane);
            this.registerDropSelfLootTable(ModBlocks.jacaranda_log);
            this.registerDropSelfLootTable(ModBlocks.magic_log);
            this.registerDropSelfLootTable(ModBlocks.stone_altar);
            this.registerDropSelfLootTable(ModBlocks.magic_planks);
            this.registerLootTable(ModBlocks.jacaranda_leaves, (block -> droppingWithChancesAndSticks(block, ModBlocks.jacaranda_sapling, DEFAULT_SAPLING_DROP_RATES)));
            this.registerLootTable(ModBlocks.magic_leaves, (block -> droppingWithChancesAndSticks(block, ModBlocks.magic_leaves, DEFAULT_SAPLING_DROP_RATES)));
            this.registerDropSelfLootTable(ModBlocks.stone_altar_fire_bowl);
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.getAllBlocks();
        }
    }

    private static class ModEntityLootTables extends EntityLootTables {
        private ModEntityLootTables() {
        }

        @Override
        protected void addTables() {
            this.registerLootTable(ModEntities.task_master_werewolf, LootTable.builder());
            this.registerLootTable(ModEntities.werewolf_survivalist, LootTable.builder().addLootPool(LootPool.builder().name("general").acceptCondition(KilledByPlayer.builder()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ModItems.bone).weight(4)).addEntry(ItemLootEntry.builder(ModItems.liver).weight(1))));
            this.registerLootTable(ModEntities.werewolf_beast, LootTable.builder().addLootPool(LootPool.builder().name("general").acceptCondition(KilledByPlayer.builder()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ModItems.bone).weight(4)).addEntry(ItemLootEntry.builder(ModItems.liver).weight(1))));
            this.registerLootTable(ModEntities.wolf, LootTable.builder());
            this.registerLootTable(ModEntities.human_werewolf, LootTable.builder().addLootPool(LootPool.builder().name("general").acceptCondition(KilledByPlayer.builder()).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ModItems.bone).weight(4)).addEntry(ItemLootEntry.builder(ModItems.liver).weight(1))));
        }

        @Nonnull
        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return ModEntities.getAllEntities();
        }
    }

    private static class InjectLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(ModLootTables.villager, LootTable.builder()
                    .addLootPool(LootPool.builder().name("liver").rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(ModItems.liver).weight(1).acceptCondition(RandomChance.builder(0.5f)))));
            consumer.accept(ModLootTables.skeleton, LootTable.builder()
                    .addLootPool(LootPool.builder().name("bones").rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(ModItems.bone).weight(1).acceptCondition(RandomChance.builder(0.1f)))));
        }
    }
}
