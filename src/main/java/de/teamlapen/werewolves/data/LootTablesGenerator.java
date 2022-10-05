package de.teamlapen.werewolves.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.world.loot.AddBookNbt;
import de.teamlapen.vampirism.world.loot.RefinementSetFunction;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.util.WReference;
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
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LootTablesGenerator extends LootTableProvider {

    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};


    public LootTablesGenerator(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK),
                Pair.of(ModEntityLootTables::new, LootParameterSets.ENTITY),
                Pair.of(InjectLootTables::new, LootParameterSets.ENTITY),
                Pair.of(ChestLootTables::new, LootParameterSets.CHEST)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((resourceLocation, lootTable) -> LootTableManager.validate(validationtracker, resourceLocation, lootTable));
    }

    private static class ModBlockLootTables extends BlockLootTables {
        @Override
        protected void addTables() {
            this.add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), LootTable.lootTable());
            this.add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), createSingleItemTable(de.teamlapen.vampirism.core.ModBlocks.TOTEM_TOP.get()));
            this.dropSelf(ModBlocks.JACARANDA_SAPLING.get());
            this.dropSelf(ModBlocks.MAGIC_SAPLING.get());
            this.dropSelf(ModBlocks.WOLFSBANE.get());
            this.dropSelf(ModBlocks.SILVER_BLOCK.get());
            this.dropSelf(ModBlocks.SILVER_ORE.get());
            this.dropPottedContents(ModBlocks.POTTED_WOLFSBANE.get());
            this.dropSelf(ModBlocks.JACARANDA_LOG.get());
            this.dropSelf(ModBlocks.MAGIC_LOG.get());
            this.dropSelf(ModBlocks.STONE_ALTAR.get());
            this.dropSelf(ModBlocks.MAGIC_PLANKS.get());
            this.add(ModBlocks.JACARANDA_LEAVES.get(), (block -> createLeavesDrops(block, ModBlocks.JACARANDA_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES)));
            this.add(ModBlocks.MAGIC_LEAVES.get(), (block -> createLeavesDrops(block, ModBlocks.MAGIC_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES)));
            this.dropSelf(ModBlocks.STONE_ALTAR_FIRE_BOWL.get());
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream().flatMap(RegistryObject::stream).collect(Collectors.toList());
        }
    }

    private static class ModEntityLootTables extends EntityLootTables {
        private ModEntityLootTables() {
        }

        @Override
        protected void addTables() {
            this.add(ModEntities.TASK_MASTER_WEREWOLF.get(), LootTable.lootTable());
            this.add(ModEntities.WEREWOLF_MINION.get(), LootTable.lootTable());
            LootTable.Builder werewolf = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.LIVER.get())))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(40)))
                    .withPool(LootPool.lootPool()
                            .name("accessories")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChance.randomChance(0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.BONE_NECKLACE.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(ItemLootEntry.lootTableItem(ModItems.CHARM_BRACELET.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(ItemLootEntry.lootTableItem(ModItems.DREAM_CATCHER.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION))));
            this.add(ModEntities.WEREWOLF_SURVIVALIST.get(), werewolf);
            this.add(ModEntities.WEREWOLF_BEAST.get(), werewolf);
            this.add(ModEntities.HUMAN_WEREWOLF.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.LIVER.get())))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(6)))
                    .withPool(LootPool.lootPool()
                            .name("hunter")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.V.VAMPIRE_BOOK.get()).apply(AddBookNbt.builder()).setWeight(1)))
            );
            this.add(ModEntities.WOLF.get(), LootTable.lootTable());
            this.add(ModEntities.ALPHA_WEREWOLF.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .setRolls(RandomValueRange.between(1,2))
                            .add(ItemLootEntry.lootTableItem(ModItems.WEREWOLF_TOOTH.get())))
                    .withPool(LootPool.lootPool()
                            .name("vampire_book")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.V.VAMPIRE_BOOK.get()).apply(AddBookNbt.builder()).setWeight(1))
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
                            .add(ItemLootEntry.lootTableItem(ModItems.LIVER.get()).setWeight(1).when(RandomChance.randomChance(0.5f)))));
            consumer.accept(ModLootTables.skeleton, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("bones").setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(1).when(RandomChance.randomChance(0.1f)))));
        }
    }

    private static class ChestLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder accessories = LootPool.lootPool()
                    .name("accessories")
                    .setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(ModItems.BONE_NECKLACE.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(ItemLootEntry.lootTableItem(ModItems.CHARM_BRACELET.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(ItemLootEntry.lootTableItem(ModItems.DREAM_CATCHER.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)));
            consumer.accept(ModLootTables.abandoned_mineshaft, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.desert_pyramid, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.LIVER.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.jungle_temple, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.stronghold_corridor, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.WEREWOLF_TOOTH.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.stronghold_library, LootTable.lootTable()
                    .withPool(accessories)
            );
        }
    }

}
