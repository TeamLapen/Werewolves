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
import net.minecraft.tags.ItemTags;
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
            this.tag(ModTags.Blocks.SILVER_ORE).add(ModBlocks.silver_ore.get());
            this.tag(BlockTags.LOGS).add(ModBlocks.jacaranda_log.get(), ModBlocks.magic_log.get());
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.jacaranda_sapling.get(), ModBlocks.magic_sapling.get());
            this.tag(BlockTags.LEAVES).add(ModBlocks.jacaranda_leaves.get(), ModBlocks.magic_leaves.get());
            this.tag(BlockTags.PLANKS).add(ModBlocks.magic_planks.get());
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.stone_altar_fire_bowl.get(), ModBlocks.stone_altar.get());
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
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.silver_ingot.get());
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.silver_nugget.get());
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.liver.get(), ModItems.V.human_heart.get(), ModItems.V.weak_human_heart.get(), Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.silver_axe.get(), ModItems.silver_hoe.get(), ModItems.silver_sword.get(), ModItems.silver_pickaxe.get(), ModItems.silver_shovel.get());
        }
    }
}
