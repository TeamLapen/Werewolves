package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;

public class ModTagsProvider {

    public static void addProvider(DataGenerator generator) {
        generator.addProvider(new ModBlockTagsProvider(generator));
        generator.addProvider(new ModItemTagsProvider(generator));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerTags() {
            this.getBuilder(ModTags.Blocks.SILVER_ORE).add(ModBlocks.silver_ore);
            this.getBuilder(BlockTags.LOGS).add(ModBlocks.jacaranda_log, ModBlocks.magic_log);
            this.getBuilder(BlockTags.SAPLINGS).add(ModBlocks.jacaranda_sapling, ModBlocks.magic_sapling);
            this.getBuilder(BlockTags.LEAVES).add(ModBlocks.jacaranda_leaves, ModBlocks.magic_leaves);
            this.getBuilder(BlockTags.PLANKS).add(ModBlocks.magic_planks);
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.getBuilder(ModTags.Items.SILVER_INGOT).add(ModItems.silver_ingot);
            this.getBuilder(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.liver);
        }
    }
}
