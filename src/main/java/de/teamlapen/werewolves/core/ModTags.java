package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("SameParameterValue")
public class ModTags extends de.teamlapen.vampirism.core.ModTags {
    public static class Blocks extends de.teamlapen.vampirism.core.ModTags.Blocks {
        public static final INamedTag<Block> SILVER_ORE = forge("ores/silver");

        private static INamedTag<Block> getTag(ResourceLocation id) {
            return BlockTags.bind(id.toString());
        }

        private static INamedTag<Block> werewolves(String id) {
            return BlockTags.bind(new ResourceLocation(REFERENCE.MODID, id).toString());
        }

        private static INamedTag<Block> forge(String id) {
            return BlockTags.bind(new ResourceLocation("forge", id).toString());
        }
    }

    public static class Items extends de.teamlapen.vampirism.core.ModTags.Items {
        public static final INamedTag<Item> SILVER_ORE = forge("ores/silver");
        public static final INamedTag<Item> SILVER_INGOT = forge("ingots/silver");
        public static final INamedTag<Item> RAWMEATS = forge("rawmeats");
        public static final INamedTag<Item> COOKEDMEATS = forge("cookedmeats");
        public static final INamedTag<Item> SILVER_TOOL = werewolves("tools/silver");

        private static INamedTag<Item> getTag(ResourceLocation id) {
            return ItemTags.bind(id.toString());
        }

        private static INamedTag<Item> werewolves(String id) {
            return ItemTags.bind(new ResourceLocation(REFERENCE.MODID, id).toString());
        }

        private static INamedTag<Item> forge(String id) {
            return ItemTags.bind(new ResourceLocation("forge", id).toString());
        }
    }
}
