package de.teamlapen.werewolves.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class WTags {

    public static class BlockTags {
        public static final Tag<Block> SILVER = new net.minecraft.tags.BlockTags.Wrapper(new ResourceLocation("forge", "ores/silver"));
    }

    public static class ItemTags {
        public static final Tag<Item> SILVER_BLOCK = new net.minecraft.tags.ItemTags.Wrapper(new ResourceLocation("forge", "ores/silver"));
        public static final Tag<Item> SILVER_INGOT = new net.minecraft.tags.ItemTags.Wrapper(new ResourceLocation("forge", "ingots/silver"));
    }
}
