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
            this.add(ModBlocks.totem_top_werewolves_werewolf.get(), LootTable.lootTable());
            this.add(ModBlocks.totem_top_werewolves_werewolf_crafted.get(), createSingleItemTable(de.teamlapen.vampirism.core.ModBlocks.totem_top));
            this.dropSelf(ModBlocks.jacaranda_sapling.get());
            this.dropSelf(ModBlocks.magic_sapling.get());
            this.dropSelf(ModBlocks.wolfsbane.get());
            this.dropSelf(ModBlocks.silver_block.get());
            this.dropSelf(ModBlocks.silver_ore.get());
            this.dropPottedContents(ModBlocks.potted_wolfsbane.get());
            this.dropSelf(ModBlocks.jacaranda_log.get());
            this.dropSelf(ModBlocks.magic_log.get());
            this.dropSelf(ModBlocks.stone_altar.get());
            this.dropSelf(ModBlocks.magic_planks.get());
            this.add(ModBlocks.jacaranda_leaves.get(), (block -> createLeavesDrops(block, ModBlocks.jacaranda_sapling.get(), DEFAULT_SAPLING_DROP_RATES)));
            this.add(ModBlocks.magic_leaves.get(), (block -> createLeavesDrops(block, ModBlocks.magic_sapling.get(), DEFAULT_SAPLING_DROP_RATES)));
            this.dropSelf(ModBlocks.stone_altar_fire_bowl.get());
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
            this.add(ModEntities.task_master_werewolf.get(), LootTable.lootTable());
            this.add(ModEntities.werewolf_minion.get(), LootTable.lootTable());
            LootTable.Builder werewolf = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.liver.get())))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.cracked_bone.get()).setWeight(40)))
                    .withPool(LootPool.lootPool()
                            .name("accessories")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChance.randomChance(0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.bone_necklace.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(ItemLootEntry.lootTableItem(ModItems.charm_bracelet.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(ItemLootEntry.lootTableItem(ModItems.dream_catcher.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION))));
            this.add(ModEntities.werewolf_survivalist.get(), werewolf);
            this.add(ModEntities.werewolf_beast.get(), werewolf);
            this.add(ModEntities.human_werewolf.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.liver.get())))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.cracked_bone.get()).setWeight(6)))
                    .withPool(LootPool.lootPool()
                            .name("hunter")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.V.vampire_book.get()).apply(AddBookNbt.builder()).setWeight(1)))
            );
            this.add(ModEntities.wolf.get(), LootTable.lootTable());
            this.add(ModEntities.alpha_werewolf.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(KilledByPlayer.killedByPlayer())
                            .setRolls(RandomValueRange.between(1,2))
                            .add(ItemLootEntry.lootTableItem(ModItems.werewolf_tooth.get())))
                    .withPool(LootPool.lootPool()
                            .name("vampire_book")
                            .when(KilledByPlayer.killedByPlayer())
                            .when(RandomChanceWithLooting.randomChanceAndLootingBoost(0.1f, 0.1f))
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.V.vampire_book.get()).apply(AddBookNbt.builder()).setWeight(1))
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
                            .add(ItemLootEntry.lootTableItem(ModItems.liver.get()).setWeight(1).when(RandomChance.randomChance(0.5f)))));
            consumer.accept(ModLootTables.skeleton, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("bones").setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.cracked_bone.get()).setWeight(1).when(RandomChance.randomChance(0.1f)))));
        }
    }

    private static class ChestLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder accessories = LootPool.lootPool()
                    .name("accessories")
                    .setRolls(ConstantRange.exactly(1))
                    .add(ItemLootEntry.lootTableItem(ModItems.bone_necklace.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(ItemLootEntry.lootTableItem(ModItems.charm_bracelet.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(ItemLootEntry.lootTableItem(ModItems.dream_catcher.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)));
            consumer.accept(ModLootTables.abandoned_mineshaft, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.cracked_bone.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.desert_pyramid, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.liver.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.jungle_temple, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.cracked_bone.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.stronghold_corridor, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantRange.exactly(1))
                            .add(ItemLootEntry.lootTableItem(ModItems.werewolf_tooth.get()).setWeight(5))
                            .add(EmptyLootEntry.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.stronghold_library, LootTable.lootTable()
                    .withPool(accessories)
            );
        }
    }

}
