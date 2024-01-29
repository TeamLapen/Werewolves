package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.world.loot.functions.AddBookNbtFunction;
import de.teamlapen.vampirism.world.loot.functions.RefinementSetFunction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.blocks.WolfBerryBushBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModEntities;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModLootTables;
import de.teamlapen.werewolves.mixin.VanillaBlockLootAccessor;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class LootTablesGenerator extends LootTableProvider {

    private static final float[] DEFAULT_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

    public LootTablesGenerator(PackOutput output) {
        super(output, ModLootTables.getLootTables(), List.of(new SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY), new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK), new SubProviderEntry(ChestLootTables::new, LootContextParamSets.CHEST), new SubProviderEntry(InjectLootTables::new, LootContextParamSets.ENTITY)));
    }

    private static class ModBlockLootTables extends BlockLootSubProvider {

        protected ModBlockLootTables() {
            super(VanillaBlockLootAccessor.getEXPLOSION_RESISTANT(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            this.add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), LootTable.lootTable());
            this.add(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), createSingleItemTable(de.teamlapen.vampirism.core.ModBlocks.TOTEM_TOP.get()));
            this.dropSelf(ModBlocks.WOLFSBANE.get());
            this.dropSelf(ModBlocks.SILVER_BLOCK.get());
            this.dropSelf(ModBlocks.SILVER_ORE.get());
            this.dropSelf(ModBlocks.RAW_SILVER_BLOCK.get());
            this.dropPottedContents(ModBlocks.POTTED_WOLFSBANE.get());
            this.dropSelf(ModBlocks.STONE_ALTAR.get());
            this.dropSelf(ModBlocks.STONE_ALTAR_FIRE_BOWL.get());
            this.add(ModBlocks.SILVER_ORE.get(), (block) -> {
                return createOreDrop(block, ModItems.RAW_SILVER.get());
            });
            this.add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), (block) -> {
                return createOreDrop(block, ModItems.RAW_SILVER.get());
            });
            this.dropSelf(ModBlocks.DAFFODIL.get());

            this.add(ModBlocks.WOLF_BERRY_BUSH.get(), (p_124086_) -> {
                return applyExplosionDecay(p_124086_, LootTable.lootTable().withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WOLF_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WolfBerryBushBlock.AGE, 3))).add(LootItem.lootTableItem(ModItems.WOLF_BERRIES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))).withPool(LootPool.lootPool().when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WOLF_BERRY_BUSH.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WolfBerryBushBlock.AGE, 2))).add(LootItem.lootTableItem(ModItems.WOLF_BERRIES.get())).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
            });

            dropPottedContents(ModBlocks.POTTED_WOLFSBANE.get());
            dropPottedContents(ModBlocks.POTTED_DAFFODIL.get());

            this.dropSelf(ModBlocks.JACARANDA_LOG.get());
            this.dropSelf(ModBlocks.MAGIC_LOG.get());
            this.dropSelf(ModBlocks.JACARANDA_STAIRS.get());
            this.dropSelf(ModBlocks.MAGIC_STAIRS.get());
            this.dropSelf(ModBlocks.JACARANDA_WOOD.get());
            this.dropSelf(ModBlocks.MAGIC_WOOD.get());
            this.dropSelf(ModBlocks.STRIPPED_JACARANDA_WOOD.get());
            this.dropSelf(ModBlocks.STRIPPED_MAGIC_WOOD.get());
            this.dropSelf(ModBlocks.JACARANDA_SIGN.get());
            this.dropSelf(ModBlocks.MAGIC_SIGN.get());
            this.dropSelf(ModBlocks.JACARANDA_WALL_SIGN.get());
            this.dropSelf(ModBlocks.MAGIC_WALL_SIGN.get());
            this.dropSelf(ModBlocks.JACARANDA_PRESSURE_PLATE.get());
            this.dropSelf(ModBlocks.MAGIC_PRESSURE_PLATE.get());
            this.dropSelf(ModBlocks.JACARANDA_BUTTON.get());
            this.dropSelf(ModBlocks.MAGIC_BUTTON.get());
            this.dropSelf(ModBlocks.JACARANDA_SLAB.get());
            this.dropSelf(ModBlocks.MAGIC_SLAB.get());
            this.dropSelf(ModBlocks.JACARANDA_FENCE_GATE.get());
            this.dropSelf(ModBlocks.MAGIC_FENCE_GATE.get());
            this.dropSelf(ModBlocks.JACARANDA_FENCE.get());
            this.dropSelf(ModBlocks.MAGIC_FENCE.get());
            this.dropSelf(ModBlocks.STRIPPED_JACARANDA_LOG.get());
            this.dropSelf(ModBlocks.STRIPPED_MAGIC_LOG.get());
            this.dropSelf(ModBlocks.JACARANDA_PLANKS.get());
            this.dropSelf(ModBlocks.MAGIC_PLANKS.get());
            this.dropSelf(ModBlocks.JACARANDA_TRAPDOOR.get());
            this.dropSelf(ModBlocks.MAGIC_TRAPDOOR.get());
            this.add(ModBlocks.JACARANDA_DOOR.get(), this::createDoorTable);
            this.add(ModBlocks.MAGIC_DOOR.get(), this::createDoorTable);
            this.add(ModBlocks.JACARANDA_LEAVES.get(), (block) -> createLeavesDrops(block, ModBlocks.JACARANDA_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));
            this.add(ModBlocks.MAGIC_LEAVES.get(), (block) -> createLeavesDrops(block, ModBlocks.MAGIC_SAPLING.get(), DEFAULT_SAPLING_DROP_RATES));
            this.dropSelf(ModBlocks.JACARANDA_SAPLING.get());
            this.dropSelf(ModBlocks.MAGIC_SAPLING.get());
            this.dropSelf(ModBlocks.WOLFSBANE_DIFFUSER.get());
            this.dropSelf(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get());
            this.dropSelf(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get());
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.getAllBlocks();
        }
    }

    private static class ModEntityLootTables extends EntityLootSubProvider {
        protected ModEntityLootTables() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate() {
            this.add(ModEntities.TASK_MASTER_WEREWOLF.get(), LootTable.lootTable());
            this.add(ModEntities.WEREWOLF_MINION.get(), LootTable.lootTable());
            LootTable.Builder werewolf = LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.LIVER.get())))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(40)))
                    .withPool(LootPool.lootPool()
                            .name("general3")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(ModItems.PELT.get()).setWeight(40))
                            .add(LootItem.lootTableItem(ModItems.DARK_PELT.get()).setWeight(2))
                            .add(EmptyLootItem.emptyItem().setWeight(60)))
                    .withPool(LootPool.lootPool()
                            .name("accessories")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceCondition.randomChance(0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.BONE_NECKLACE.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(LootItem.lootTableItem(ModItems.CHARM_BRACELET.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                            .add(LootItem.lootTableItem(ModItems.DREAM_CATCHER.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION))));
            this.add(ModEntities.WEREWOLF_SURVIVALIST.get(), werewolf);
            this.add(ModEntities.WEREWOLF_BEAST.get(), werewolf);
            this.add(ModEntities.HUMAN_WEREWOLF.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.LIVER.get())))
                    .withPool(LootPool.lootPool()
                            .name("general2")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.33f, 0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(6)))
                    .withPool(LootPool.lootPool()
                            .name("hunter")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.015f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.V.VAMPIRE_BOOK.get()).setWeight(1)))
            );
            this.add(ModEntities.WOLF.get(), LootTable.lootTable());
            this.add(ModEntities.ALPHA_WEREWOLF.get(), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("general")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .setRolls(UniformGenerator.between(1, 2)))
                    .withPool(LootPool.lootPool()
                            .name("pelt")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(ModItems.DARK_PELT.get()).setWeight(40))
                            .add(LootItem.lootTableItem(ModItems.WHITE_PELT.get()).setWeight(1)))
                    .withPool(LootPool.lootPool()
                            .name("vampire_book")
                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.015f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.V.VAMPIRE_BOOK.get()).apply(AddBookNbtFunction.builder()).setWeight(1))
                    )
            );
        }

        @Override
        protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
            return ModEntities.getAllEntities().stream();
        }
    }

    private static class InjectLootTables implements LootTableSubProvider {
        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(ModLootTables.villager, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("liver").setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.LIVER.get()).setWeight(1).when(LootItemRandomChanceCondition.randomChance(0.5f)))));
            consumer.accept(ModLootTables.skeleton, LootTable.lootTable()
                    .withPool(LootPool.lootPool().name("bones").setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(1).when(LootItemRandomChanceCondition.randomChance(0.1f)))));
        }
    }

    private static class ChestLootTables implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder accessories = LootPool.lootPool()
                    .name("accessories")
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.BONE_NECKLACE.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(LootItem.lootTableItem(ModItems.CHARM_BRACELET.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)))
                    .add(LootItem.lootTableItem(ModItems.DREAM_CATCHER.get()).setWeight(1).apply(RefinementSetFunction.builder(WReference.WEREWOLF_FACTION)));
            consumer.accept(ModLootTables.abandoned_mineshaft, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
                    .withPool(LootPool.lootPool()
                            .name("werewolf_pelt_upgrade")
                            .when(LootItemRandomChanceCondition.randomChance(0.02f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE.get())))
            );
            consumer.accept(ModLootTables.desert_pyramid, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.LIVER.get()).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
            );
            consumer.accept(ModLootTables.jungle_temple, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.CRACKED_BONE.get()).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
                    .withPool(LootPool.lootPool()
                            .name("werewolf_pelt_upgrade")
                            .when(LootItemRandomChanceCondition.randomChance(0.02f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE.get())))
            );
            consumer.accept(ModLootTables.stronghold_corridor, LootTable.lootTable()
                    .withPool(accessories)
                    .withPool(LootPool.lootPool()
                            .name("main")
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.WEREWOLF_TOOTH.get()).setWeight(5))
                            .add(EmptyLootItem.emptyItem().setWeight(10)))
                    .withPool(LootPool.lootPool()
                            .name("werewolf_pelt_upgrade")
                            .when(LootItemRandomChanceCondition.randomChance(0.02f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE.get())))
            );
            consumer.accept(ModLootTables.stronghold_library, LootTable.lootTable()
                    .withPool(accessories)
            );
            consumer.accept(ModLootTables.nether_bridge, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .name("werewolf_pelt_upgrade")
                            .when(LootItemRandomChanceCondition.randomChance(0.05f))
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(ModItems.WHITE_PELT_UPGRADE_SMITHING_TEMPLATE.get())))
            );
        }
    }

}
