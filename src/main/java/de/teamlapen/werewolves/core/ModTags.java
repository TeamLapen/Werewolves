package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SameParameterValue")
public class ModTags {
    public static class Blocks extends de.teamlapen.vampirism.core.ModTags.Blocks {
        public static final TagKey<Block> SILVER_ORE = forge("ores/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = forge("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = forge("storage_blocks/raw_silver");
        public static final TagKey<Block> MAGIC_LOGS = forge("magic_logs");
        public static final TagKey<Block> JACARANDA_LOGS = forge("jacaranda_logs");
        public static final TagKey<Block> NEEDS_SILVER_TOOL = forge("needs_silver_tool");


        private static TagKey<Block> mc(ResourceLocation id) {
            return BlockTags.create(id);
        }

        private static TagKey<Block> werewolves(String id) {
            return BlockTags.create(new ResourceLocation(REFERENCE.MODID, id));
        }

        private static TagKey<Block> forge(String id) {
            return BlockTags.create(new ResourceLocation("forge", id));
        }
    }

    public static class Items extends de.teamlapen.vampirism.core.ModTags.Items {
        public static final TagKey<Item> SILVER_ORE = forge("ores/silver");
        public static final TagKey<Item> SILVER_INGOT = forge("ingots/silver");
        public static final TagKey<Item> SILVER_NUGGET = forge("nuggets/silver");
        public static final TagKey<Item> RAWMEATS = forge("rawmeats");
        public static final TagKey<Item> COOKEDMEATS = forge("cookedmeats");
        public static final TagKey<Item> SILVER_TOOL = werewolves("tools/silver");
        public static final TagKey<Item> RAW_MATERIALS_SILVER = forge("raw_materials/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = forge("storage_blocks/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = forge("storage_blocks/raw_silver");
        public static final TagKey<Item> MAGIC_LOGS = forge("magic_logs");
        public static final TagKey<Item> JACARANDA_LOGS = forge("jacaranda_logs");

        private static TagKey<Item> mc(ResourceLocation id) {
            return ItemTags.create(id);
        }

        private static TagKey<Item> werewolves(String id) {
            return ItemTags.create(new ResourceLocation(REFERENCE.MODID, id));
        }

        private static TagKey<Item> forge(String id) {
            return ItemTags.create(new ResourceLocation("forge", id));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> IS_WEREWOLF_BIOME = tag("has_faction/werewolf");

        public static class HasGen {
            public static final TagKey<Biome> SILVER_ORE = tag("has_gen/silver_ore");
            public static final TagKey<Biome> WOLFSBANE = tag("has_gen/wolfsbane");

        }
        public static class HasSpawn {
            public static final TagKey<Biome> WEREWOLF = tag("has_spawn/werewolf");
            public static final TagKey<Biome> HUMAN_WEREWOLF = tag("has_spawn/human_werewolf");
        }

        public static class NoSpawn {
            public static final TagKey<Biome> WEREWOLF = tag("no_spawn/werewolf");
            public static final TagKey<Biome> HUMAN_WEREWOLF = tag("no_spawn/human_werewolf");
        }

        private static @NotNull TagKey<Biome> tag(@NotNull String name) {
            return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(REFERENCE.MODID, name));
        }
    }

    public static class PoiTypes {
        public static final TagKey<PoiType> IS_WEREWOLF = tag("is_werewolf");

        private static @NotNull TagKey<PoiType> tag(@NotNull String name) {
            return TagKey.create(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, new ResourceLocation(REFERENCE.MODID, name));
        }

    }

    public static class Professions {
        public static final TagKey<VillagerProfession> IS_WEREWOLF = tag("is_werewolf");


        private static @NotNull TagKey<VillagerProfession> tag(@NotNull String name) {
            return TagKey.create(Registry.VILLAGER_PROFESSION_REGISTRY, new ResourceLocation(REFERENCE.MODID, name));
        }

    }
}
