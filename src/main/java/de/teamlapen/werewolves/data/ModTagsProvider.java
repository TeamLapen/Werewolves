package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ModTagsProvider {

    public static void addProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        BlockTagsProvider blocks = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blocks);
        generator.addProvider(new ModItemTagsProvider(generator, blocks, existingFileHelper));
        generator.addProvider(new ModBiomeTagsProvider(generator, existingFileHelper));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Blocks.SILVER_ORE).add(ModBlocks.silver_ore);
            this.tag(BlockTags.LOGS).add(ModBlocks.jacaranda_log, ModBlocks.magic_log);
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.jacaranda_sapling, ModBlocks.magic_sapling);
            this.tag(BlockTags.LEAVES).add(ModBlocks.jacaranda_leaves, ModBlocks.magic_leaves);
            this.tag(BlockTags.PLANKS).add(ModBlocks.magic_planks);
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.stone_altar_fire_bowl, ModBlocks.stone_altar);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.stone_altar, ModBlocks.stone_altar_fire_bowl);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.jacaranda_log, ModBlocks.magic_log, ModBlocks.magic_planks);
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
            this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
            this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            this.copy(BlockTags.LOGS, ItemTags.LOGS);
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.silver_ingot);
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.silver_nugget);
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.liver, ModItems.V.human_heart, ModItems.V.weak_human_heart, Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.silver_axe, ModItems.silver_hoe, ModItems.silver_sword, ModItems.silver_pickaxe, ModItems.silver_shovel);
        }
    }

    private static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Biomes.WEREWOLF_BIOME).add(ModBiomes.WEREWOLF_HEAVEN);
        }

        @Nonnull
        @Override
        public String getName() {
            return "Werewoloves " + super.getName();
        }
    }
}
