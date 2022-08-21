package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ModTagsProvider {

    public static void addProvider(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        BlockTagsProvider blocks = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(generator, blocks, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPoiTypesProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModVillageProfessionProvider(generator, existingFileHelper));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Blocks.SILVER_ORE).add(ModBlocks.silver_ore.get(), ModBlocks.deepslate_silver_ore.get());
            this.tag(BlockTags.LOGS).add(ModBlocks.jacaranda_log.get(), ModBlocks.magic_log.get());
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.jacaranda_sapling.get(), ModBlocks.magic_sapling.get());
            this.tag(BlockTags.LEAVES).add(ModBlocks.jacaranda_leaves.get(), ModBlocks.magic_leaves.get());
            this.tag(BlockTags.PLANKS).add(ModBlocks.magic_planks.get());
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.stone_altar_fire_bowl.get(), ModBlocks.stone_altar.get());
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.stone_altar.get(), ModBlocks.stone_altar_fire_bowl.get(), ModBlocks.deepslate_silver_ore.get(), ModBlocks.silver_ore.get(), ModBlocks.silver_block.get(), ModBlocks.raw_silver_block.get());
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.jacaranda_log.get(), ModBlocks.magic_log.get(), ModBlocks.magic_planks.get());
            this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.deepslate_silver_ore.get(), ModBlocks.silver_ore.get(), ModBlocks.silver_block.get(), ModBlocks.raw_silver_block.get());
            this.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(ModBlocks.deepslate_silver_ore.get(), ModBlocks.silver_ore.get());
            this.tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.silver_block.get());
            this.tag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER).add(ModBlocks.raw_silver_block.get());
            this.tag(ModTags.Blocks.MAGIC_LOGS).add(ModBlocks.magic_log.get());
            this.tag(ModTags.Blocks.JACARANDA_LOGS).add(ModBlocks.jacaranda_log.get());
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, ModTags.Items.STORAGE_BLOCKS_RAW_SILVER);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);
            this.copy(ModTags.Blocks.MAGIC_LOGS, ModTags.Items.MAGIC_LOGS);
            this.copy(ModTags.Blocks.JACARANDA_LOGS, ModTags.Items.JACARANDA_LOGS);
            this.copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
            this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
            this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
            this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            this.copy(BlockTags.LOGS, ItemTags.LOGS);
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.silver_ingot.get());
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.silver_nugget.get());
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.liver.get(), ModItems.V.human_heart.get(), ModItems.V.weak_human_heart.get(), Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.silver_axe.get(), ModItems.silver_hoe.get(), ModItems.silver_sword.get(), ModItems.silver_pickaxe.get(), ModItems.silver_shovel.get());
            this.tag(ModTags.Items.RAW_MATERIALS_SILVER).add(ModItems.raw_silver.get());
        }
    }

    private static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Biomes.IS_WEREWOLF_BIOME).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(ModTags.Biomes.HasSpawn.WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModTags.Biomes.NoSpawn.WEREWOLF).addTag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME);
            this.tag(ModTags.Biomes.HasSpawn.HUMAN_WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModTags.Biomes.NoSpawn.HUMAN_WEREWOLF).addTag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME);
            this.tag(ModTags.Biomes.HasGen.SILVER_ORE).addTag(BiomeTags.IS_OVERWORLD);
            this.tag(ModTags.Biomes.HasGen.WOLFSBANE).addTag(BiomeTags.IS_FOREST);
            this.tag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME).addTag(ModTags.Biomes.IS_WEREWOLF_BIOME);
            this.tag(BiomeTags.IS_OVERWORLD).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(Tags.Biomes.IS_DENSE).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(Tags.Biomes.IS_MAGICAL).add(ModBiomes.WEREWOLF_HEAVEN.get());
        }

        @Nonnull
        @Override
        public String getName() {
            return "Werewolves " + super.getName();
        }
    }

    private static class ModPoiTypesProvider extends PoiTypeTagsProvider {

        public ModPoiTypesProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.PoiTypes.IS_WEREWOLF).add(ModVillage.werewolf_faction.get());
            this.tag(de.teamlapen.vampirism.core.ModTags.PoiTypes.HAS_FACTION).addTag(ModTags.PoiTypes.IS_WEREWOLF);
        }
    }

    private static class ModVillageProfessionProvider extends ForgeRegistryTagsProvider<VillagerProfession> {
        public ModVillageProfessionProvider(@NotNull DataGenerator p_236434_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_236434_, ForgeRegistries.VILLAGER_PROFESSIONS, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Professions.IS_WEREWOLF).add(ModVillage.werewolf_expert.get());
            this.tag(de.teamlapen.vampirism.core.ModTags.Professions.HAS_FACTION).addTag(ModTags.Professions.IS_WEREWOLF);
        }
    }
}
