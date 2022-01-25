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
import net.minecraft.loot.conditions.RandomChanceWithLooting;
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

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK), Pair.of(ModEntityLootTables::new, LootParameterSets.ENTITY), Pair.of(InjectLootTables::new, LootParameterSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((resourceLocation, lootTable) -> LootTableManager.validate(validationtracker, resourceLocation, lootTable));
    }

    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {
            this.add(ModBlocks.totem_top_werewolves_werewolf, LootTable.lootTable());
            this.add(ModBlocks.totem_top_werewolves_werewolf_crafted, createSingleItemTable(de.teamlapen.vampirism.core.ModBlocks.totem_top));
            this.dropSelf(ModBlocks.jacaranda_sapling);
            this.dropSelf(ModBlocks.magic_sapling);
            this.dropSelf(ModBlocks.wolfsbane);
            this.dropSelf(ModBlocks.silver_block);
            this.dropSelf(ModBlocks.silver_ore);
            this.dropPottedContents(ModBlocks.potted_wolfsbane);
            this.dropSelf(ModBlocks.jacaranda_log);
            this.dropSelf(ModBlocks.magic_log);
            this.dropSelf(ModBlocks.stone_altar);
            this.dropSelf(ModBlocks.magic_planks);
            this.add(ModBlocks.jacaranda_leaves, (block -> createLeavesDrops(block, ModBlocks.jacaranda_sapling, DEFAULT_SAPLING_DROP_RATES)));
            this.add(ModBlocks.magic_leaves, (block -> createLeavesDrops(block, ModBlocks.magic_sapling, DEFAULT_SAPLING_DROP_RATES)));
            this.dropSelf(ModBlocks.stone_altar_fire_bowl);
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
            this.add(ModEntities.task_master_werewolf, LootTable.lootTable());
            this.add(ModEntities.werewolf_minion, LootTable.lootTable());
            LootTable.Builder werewolf = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.liver)))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.bone).setWeight(40))
                            .add(ItemLootEntry.lootTableItem(ModItems.werewolf_tooth).setWeight(1)));
            this.add(ModEntities.werewolf_survivalist, werewolf);
            this.add(ModEntities.werewolf_beast, werewolf);
            this.add(ModEntities.human_werewolf, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.liver)))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.bone).setWeight(6)))
                    .withPool(LootPool.lootPool()
                            .name("hunter")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.V.vampire_book).setWeight(1)))
            );
            this.add(ModEntities.wolf, LootTable.lootTable());
            this.add(ModEntities.alpha_werewolf, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .setRolls(RandomValueRange.between(1,2))
                            .add(ItemLootEntry.lootTableItem(ModItems.werewolf_tooth)))
                    .withPool(LootPool.lootPool()
                            .name("vampire_book")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.V.vampire_book).setWeight(1))
                    )
            );
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
            consumer.accept(ModLootTables.villager, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("liver").setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.liver).setWeight(1).when(RandomChance.randomChance(0.5f)))));
            consumer.accept(ModLootTables.skeleton, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("bones").setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.bone).setWeight(1).when(RandomChance.randomChance(0.1f)))));
        }
    }

}
