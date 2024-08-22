package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.werewolves.api.WResourceLocation;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("SameParameterValue")
public class ModTags {
    public static class Blocks extends de.teamlapen.vampirism.core.ModTags.Blocks {
        public static final TagKey<Block> SILVER_ORE = common("ores/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = common("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = common("storage_blocks/raw_silver");
        public static final TagKey<Block> MAGIC_LOG = common("magic_log");
        public static final TagKey<Block> JACARANDA_LOG = common("jacaranda_log");
        public static final TagKey<Block> NEEDS_SILVER_TOOL = common("needs_silver_tool");
        public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = common("incorrect_for_silver_tool");


        private static TagKey<Block> mc(ResourceLocation id) {
            return BlockTags.create(id);
        }

        private static TagKey<Block> werewolves(String id) {
            return BlockTags.create(WResourceLocation.mod(id));
        }

        private static TagKey<Block> common(String id) {
            return BlockTags.create(WResourceLocation.common(id));
        }
    }

    public static class Items extends de.teamlapen.vampirism.core.ModTags.Items {
        public static final TagKey<Item> SILVER_ORE = common("ores/silver");
        public static final TagKey<Item> SILVER_INGOT = common("ingots/silver");
        public static final TagKey<Item> SILVER_NUGGET = common("nuggets/silver");
        public static final TagKey<Item> RAW_MEAT = common("rawmeats");
        public static final TagKey<Item> COOKED_MEAT = common("cookedmeats");
        public static final TagKey<Item> RAW_FISH = werewolves("raw_fish");
        public static final TagKey<Item> COOKED_FISH = werewolves("cooked_fish");
        public static final TagKey<Item> RAW_FOOD = werewolves("raw_food");
        public static final TagKey<Item> WEREWOLF_FOOD = werewolves("werewolf_food");
        public static final TagKey<Item> SILVER_TOOL = werewolves("tools/silver");
        public static final TagKey<Item> SILVER_ARMOR = werewolves("armor/silver");
        public static final TagKey<Item> SILVER_ITEM = werewolves("type/silver");
        public static final TagKey<Item> RAW_MATERIALS_SILVER = common("raw_materials/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = common("storage_blocks/silver");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = common("storage_blocks/raw_silver");
        public static final TagKey<Item> MAGIC_LOG = common("magic_log");
        public static final TagKey<Item> JACARANDA_LOG = common("jacaranda_log");
        public static final TagKey<Item> WOLF_PELT_ARMOR_NORMAL = werewolves("armor/wolf_pelt/normal");
        public static final TagKey<Item> WOLF_PELT_ARMOR_ENHANCED = werewolves("armor/wolf_pelt/enhanced");
        public static final TagKey<Item> WOLF_PELT_ARMOR_ULTIMATE = werewolves("armor/wolf_pelt/ultimate");
        public static final TagKey<Item> WOLF_PELT_ARMOR = werewolves("armor/wolf_pelt");

        private static TagKey<Item> mc(ResourceLocation id) {
            return ItemTags.create(id);
        }

        private static TagKey<Item> werewolves(String id) {
            return ItemTags.create(WResourceLocation.mod( id));
        }

        private static TagKey<Item> common(String id) {
            return ItemTags.create(WResourceLocation.common(id));
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
            return TagKey.create(Registries.BIOME, WResourceLocation.mod( name));
        }
    }

    public static class PoiTypes {
        public static final TagKey<PoiType> IS_WEREWOLF = tag("is_werewolf");
        public static final TagKey<PoiType> HAS_FACTION = vampirism("has_faction");

        private static @NotNull TagKey<PoiType> tag(@NotNull String name) {
            return tag(REFERENCE.MODID, name);
        }

        private static @NotNull TagKey<PoiType> vampirism(@NotNull String name) {
            return tag(de.teamlapen.vampirism.REFERENCE.MODID, name);
        }

        private static @NotNull TagKey<PoiType> tag(@NotNull String modid, @NotNull String name) {
            return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, WResourceLocation.loc(modid, name));
        }

    }

    public static class Professions {
        public static final TagKey<VillagerProfession> IS_WEREWOLF = tag("is_werewolf");


        private static @NotNull TagKey<VillagerProfession> tag(@NotNull String name) {
            return TagKey.create(Registries.VILLAGER_PROFESSION,WResourceLocation.mod(name));
        }

    }

    public static class DamageTypes {
        public static final TagKey<DamageType> WEREWOLF_FUR_IMMUNE = tag("werewolf_fur_immune");
        public static final TagKey<DamageType> WEREWOLF_ARMOR_REDUCTION = tag("werewolf_armor_reduction");

        private static @NotNull TagKey<DamageType> tag(@NotNull String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, WResourceLocation.mod(name));
        }

    }

    public static class Tasks {
        public static final TagKey<Task> IS_WEREWOLF = tag("is_werewolf");
        public static final TagKey<Task> AWARDS_LORD_LEVEL = vampirism("awards_lord_level");
        public static final TagKey<Task> HAS_FACTION = vampirism("has_faction");

        private static @NotNull TagKey<Task> tag(@NotNull String name) {
            return TagKey.create(VampirismRegistries.Keys.TASK, WResourceLocation.mod(name));
        }

        private static @NotNull TagKey<Task> vampirism(@NotNull String name) {
            return TagKey.create(VampirismRegistries.Keys.TASK, WResourceLocation.v(name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> WEREWOLF = tag("werewolf");

        private static @NotNull TagKey<EntityType<?>> tag(@NotNull ResourceLocation resourceLocation) {
            return TagKey.create(Registries.ENTITY_TYPE, resourceLocation);
        }

        private static @NotNull TagKey<EntityType<?>> tag(@NotNull String name) {
            return tag(WResourceLocation.mod(name));
        }
    }
}
