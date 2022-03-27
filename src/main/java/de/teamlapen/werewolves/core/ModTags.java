package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("SameParameterValue")
public class ModTags {
    public static class Blocks extends de.teamlapen.vampirism.core.ModTags.Blocks {
        public static final Named<Block> SILVER_ORE = forge("ores/silver");

        private static Named<Block> getTag(ResourceLocation id) {
            return BlockTags.bind(id.toString());
        }

        private static Named<Block> werewolves(String id) {
            return BlockTags.bind(new ResourceLocation(REFERENCE.MODID, id).toString());
        }

        private static Named<Block> forge(String id) {
            return BlockTags.bind(new ResourceLocation("forge", id).toString());
        }
    }

    public static class Items extends de.teamlapen.vampirism.core.ModTags.Items {
        public static final Named<Item> SILVER_ORE = forge("ores/silver");
        public static final Named<Item> SILVER_INGOT = forge("ingots/silver");
        public static final Named<Item> SILVER_NUGGET = forge("nuggets/silver");
        public static final Named<Item> RAWMEATS = forge("rawmeats");
        public static final Named<Item> COOKEDMEATS = forge("cookedmeats");
        public static final Named<Item> SILVER_TOOL = werewolves("tools/silver");

        private static Named<Item> getTag(ResourceLocation id) {
            return ItemTags.bind(id.toString());
        }

        private static Named<Item> werewolves(String id) {
            return ItemTags.bind(new ResourceLocation(REFERENCE.MODID, id).toString());
        }

        private static Named<Item> forge(String id) {
            return ItemTags.bind(new ResourceLocation("forge", id).toString());
        }
    }
}
