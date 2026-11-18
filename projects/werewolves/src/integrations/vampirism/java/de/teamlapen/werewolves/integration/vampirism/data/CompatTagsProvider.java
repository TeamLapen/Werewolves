package de.teamlapen.werewolves.integration.vampirism.data;

import de.teamlapen.vampirism.common.core.ModItems;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.core.ModTags;
import de.teamlapen.werewolves.integration.vampirism.common.core.CompatBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.concurrent.CompletableFuture;

public class CompatTagsProvider {

    public static void register(DataGenerator gen, PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        gen.addProvider(true, new CompatBlockTagsProvider(output, future));
        gen.addProvider(true, new CompatItemTagsProvider(output, future));
    }

    private static class CompatBlockTagsProvider extends BlockTagsProvider {
        public CompatBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(CompatBlocks.WOLFSBANE_DIFFUSER.get())
                    .add(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get())
                    .add(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get())
            ;
            this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                    .add(CompatBlocks.WOLFSBANE_DIFFUSER.get())
                    .add(CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get())
                    .add(CompatBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get())
            ;

        }
    }

    private static class CompatItemTagsProvider extends ItemTagsProvider {

        public CompatItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            this.tag(ModTags.Items.RAW_MEAT)
                    .add(ModItems.HUMAN_HEART.get())
                    .add(ModItems.WEAK_HUMAN_HEART.get());
        }
    }
}
