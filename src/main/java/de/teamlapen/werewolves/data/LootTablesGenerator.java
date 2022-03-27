package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.world.loot.AddBookNbt;
import de.teamlapen.vampirism.world.loot.RefinementSetFunction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                Pair.of(ModEntityLootTables::new, LootContextParamSets.ENTITY),
                Pair.of(InjectLootTables::new, LootContextParamSets.ENTITY),
                Pair.of(ChestLootTables::new, LootContextParamSets.CHEST)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext validationtracker) {
        map.forEach((resourceLocation, lootTable) -> LootTables.validate(validationtracker, resourceLocation, lootTable));
    }

    private static class ModBlockLootTables extends BlockLoot {
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

    private static class ModEntityLootTables extends EntityLoot {
        private ModEntityLootTables() {
        }

        @Override
        protected void addTables() {
            this.add(ModEntities.task_master_werewolf, LootTable.lootTable());
            this.add(ModEntities.werewolf_minion, LootTable.lootTable());
            LootTable.Builder werewolf = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.liver)))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.cracked_bone).setWeight(40)))
                    .withPool(LootPool.lootPool()
                            .name("accessories")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceCondition.randomChance(0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.bone_necklace).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(LootItem.lootTableItem(ModItems.charm_bracelet).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(LootItem.lootTableItem(ModItems.dream_catcher).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION))));
            this.add(ModEntities.werewolf_survivalist, werewolf);
            this.add(ModEntities.werewolf_beast, werewolf);
            this.add(ModEntities.human_werewolf, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.liver)))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.cracked_bone).setWeight(6)))
                    .withPool(LootPool.lootPool()
                            .name("hunter")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.V.vampire_book).setWeight(1)))
            );
            this.add(ModEntities.wolf, LootTable.lootTable());
            this.add(ModEntities.alpha_werewolf, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(ModItems.werewolf_tooth)))
                    .withPool(LootPool.lootPool()
                            .name("vampire_book")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.V.vampire_book).apply(AddBookNbt.builder()).setWeight(1))
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
                    .withPool(LootPool.lootPool().name("liver").setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.liver).setWeight(1).when(LootItemRandomChanceCondition.randomChance(0.5f)))));
            consumer.accept(ModLootTables.skeleton, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("bones").setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.cracked_bone).setWeight(1).when(LootItemRandomChanceCondition.randomChance(0.1f)))));
        }
    }

    private static class ChestLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder accessories = LootPool.lootPool()
                    .name("accessories")
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.bone_necklace).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(LootItem.lootTableItem(ModItems.charm_bracelet).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(LootItem.lootTableItem(ModItems.dream_catcher).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)));
            consumer.accept(ModLootTables.abandoned_mineshaft, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.cracked_bone).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.desert_pyramid, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.liver).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.jungle_temple, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.cracked_bone).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.stronghold_corridor, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.werewolf_tooth).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.stronghold_library, LootTable.lootTable()
                    .withPool(accessories)
            );
        }
    }

}
