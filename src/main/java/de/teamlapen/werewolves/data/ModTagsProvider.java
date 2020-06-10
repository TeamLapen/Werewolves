package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.WBlocks;
import de.teamlapen.werewolves.core.WItems;
import de.teamlapen.werewolves.core.WTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;

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
            this.getBuilder(WTags.Blocks.SILVER_BLOCKS).add(WBlocks.silver_ore);
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerTags() {
            this.copy(WTags.Blocks.SILVER_BLOCKS, WTags.Items.SILVER_BLOCK);
            this.getBuilder(WTags.Items.SILVER_INGOT).add(WItems.silver_ingot);
            this.getBuilder(WTags.Items.RAWMEATS).add(net.minecraft.item.Items.BEEF, net.minecraft.item.Items.CHICKEN, net.minecraft.item.Items.MUTTON, net.minecraft.item.Items.PORKCHOP, net.minecraft.item.Items.RABBIT);
        }
    }
}
