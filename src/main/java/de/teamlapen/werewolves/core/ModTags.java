package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags extends de.teamlapen.vampirism.core.ModTags {

    public static class Blocks extends de.teamlapen.vampirism.core.ModTags.Blocks {
        public static final Tag<Block> SILVER_ORE = forge("ores/silver");

        private static Tag<Block> getTag(ResourceLocation id) {
            return new BlockTags.Wrapper(id);
        }

        private static Tag<Block> werewolves(String id) {
            return new BlockTags.Wrapper(new ResourceLocation(REFERENCE.MODID, id));
        }

        private static Tag<Block> forge(String id) {
            return new BlockTags.Wrapper(new ResourceLocation("forge", id));
        }
    }

    public static class Items extends de.teamlapen.vampirism.core.ModTags.Items {
        public static final Tag<Item> SILVER_ORE = forge("ores/silver");
        public static final Tag<Item> SILVER_INGOT = forge("ingots/silver");
        public static final Tag<Item> RAWMEATS = forge("rawmeats");

        private static Tag<Item> getTag(ResourceLocation id) {
            return new ItemTags.Wrapper(id);
        }

        private static Tag<Item> werewolves(String id) {
            return new ItemTags.Wrapper(new ResourceLocation(REFERENCE.MODID, id));
        }

        private static Tag<Item> forge(String id) {
            return new ItemTags.Wrapper(new ResourceLocation("forge", id));
        }
    }
}
