package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModTagsProvider {

    public static void addProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        BlockTagsProvider blocks = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blocks);
        generator.addProvider(new ModItemTagsProvider(generator, blocks, existingFileHelper));
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
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.silver_ingot);
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.silver_nugget);
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.liver, ModItems.human_heart, ModItems.weak_human_heart, Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.silver_axe, ModItems.silver_hoe, ModItems.silver_sword, ModItems.silver_pickaxe, ModItems.silver_shovel);
        }
    }
}
