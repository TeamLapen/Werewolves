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
            this.getBuilder(WTags.BlockTags.SILVER).add(WBlocks.silver_ore);
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn) {
            super(generatorIn);
        }

        @Override
        protected void registerTags() {
            this.copy(WTags.BlockTags.SILVER, WTags.ItemTags.SILVER_BLOCK);
            this.getBuilder(WTags.ItemTags.SILVER_INGOT).add(WItems.silver_ingot);
        }
    }
}
