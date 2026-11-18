package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.skills.ISkillTree;
import de.teamlapen.faction.api.factions.tasks.Task;
import de.teamlapen.faction.api.util.FIdentifier;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("SameParameterValue")
public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SILVER_ORE = common("ores/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = common("storage_blocks/silver");
        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = common("storage_blocks/raw_silver");
        public static final TagKey<Block> MAGIC_LOG = common("magic_log");
        public static final TagKey<Block> JACARANDA_LOG = common("jacaranda_log");
        public static final TagKey<Block> NEEDS_SILVER_TOOL = common("needs_silver_tool");
        public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = common("incorrect_for_silver_tool");


        private static TagKey<Block> mc(Identifier id) {
            return BlockTags.create(id);
        }

        private static TagKey<Block> werewolves(String id) {
            return BlockTags.create(WIdentifier.mod(id));
        }

        private static TagKey<Block> common(String id) {
            return BlockTags.create(WIdentifier.common(id));
        }
    }

    public static class Items {
        public static final TagKey<Item> SILVER_ORE = common("ores/silver");
        public static final TagKey<Item> SILVER_INGOT = common("ingots/silver");
        public static final TagKey<Item> SILVER_NUGGET = common("nuggets/silver");
        public static final TagKey<Item> RAW_MEAT = common("rawmeats");
        public static final TagKey<Item> COOKED_MEAT = common("cookedmeats");
        public static final TagKey<Item> RAW_FISH = werewolves("raw_fish");
        public static final TagKey<Item> COOKED_FISH = werewolves("cooked_fish");
        public static final TagKey<Item> RAW_FOOD = werewolves("raw_food");
        public static final TagKey<Item> COOKED_FOOD = werewolves("cooked_food");
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
        public static final TagKey<Item> WOLF_PELT = werewolves("pelt");
        public static final TagKey<Item> WOLF_DARK_PELT = werewolves("dark_pelt");
        public static final TagKey<Item> WOLF_WHITE_PELT = werewolves("white_pelt");
        public static final TagKey<Item> SILVER_REPAIRABLE = werewolves("repairable/silver");


        private static TagKey<Item> mc(Identifier id) {
            return ItemTags.create(id);
        }

        private static TagKey<Item> werewolves(String id) {
            return ItemTags.create(WIdentifier.mod( id));
        }

        private static TagKey<Item> common(String id) {
            return ItemTags.create(WIdentifier.common(id));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> IS_WEREWOLF_BIOME = tag("has_faction/werewolf");
        public static final TagKey<Biome> IS_FACTION_BIOME = faction("has_faction");

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

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, WIdentifier.mod( name));
        }
        private static TagKey<Biome> faction(String name) {
            return TagKey.create(Registries.BIOME, FIdentifier.mod( name));
        }
    }

    public static class PoiTypes {
        public static final TagKey<PoiType> IS_WEREWOLF = tag("is_werewolf");

        private static TagKey<PoiType> tag(String name) {
            return tag(REFERENCE.MODID, name);
        }

        private static TagKey<PoiType> tag(String modid, String name) {
            return TagKey.create(Registries.POINT_OF_INTEREST_TYPE, WIdentifier.loc(modid, name));
        }

    }

    public static class Professions {
        public static final TagKey<VillagerProfession> IS_WEREWOLF = tag("is_werewolf");


        private static TagKey<VillagerProfession> tag(String name) {
            return TagKey.create(Registries.VILLAGER_PROFESSION, WIdentifier.mod(name));
        }

    }

    public static class DamageTypes {
        public static final TagKey<DamageType> WEREWOLF_FUR_IMMUNE = tag("werewolf_fur_immune");
        public static final TagKey<DamageType> WEREWOLF_ARMOR_REDUCTION = tag("werewolf_armor_reduction");
        public static final TagKey<DamageType> BITE = tag("bite");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, WIdentifier.mod(name));
        }

    }

    public static class Tasks {
        public static final TagKey<Task> IS_WEREWOLF = tag("is_werewolf");
        public static final TagKey<Task> AWARDS_LORD_LEVEL = vampirism("awards_lord_level");
        public static final TagKey<Task> HAS_FACTION = vampirism("has_faction");

        private static TagKey<Task> tag(String name) {
            return TagKey.create(FactionRegistries.Keys.TASK, WIdentifier.mod(name));
        }

        private static TagKey<Task> vampirism(String name) {
            return TagKey.create(FactionRegistries.Keys.TASK, WIdentifier.loc("vampirism", name));
        }
    }

    public static class Entities {
        public static final TagKey<EntityType<?>> WEREWOLF = tag("werewolf");
        public static final TagKey<EntityType<?>> VAMPIRE = vampirism("vampire");
        public static final TagKey<EntityType<?>> HUNTER = vampirism("hunter");
        public static final TagKey<EntityType<?>> ADVANCED_HUNTER = vampirism("advanced_hunter");
        public static final TagKey<EntityType<?>> ADVANCED_VAMPIRE = vampirism("advanced_vampire");

        private static TagKey<EntityType<?>> tag(Identifier resourceLocation) {
            return TagKey.create(Registries.ENTITY_TYPE, resourceLocation);
        }

        private static TagKey<EntityType<?>> tag(String name) {
            return tag(WIdentifier.mod(name));
        }
        private static TagKey<EntityType<?>> vampirism(String name) {
            return tag(WIdentifier.loc("vampirism", name));
        }
    }

    public static class Factions {

        public static final TagKey<IFaction<?>> IS_WEREWOLF = tag("is_werewolf");
        public static final TagKey<IFaction<?>> WEREWOLF_MINION_TARGETS = tag("target/werewolf_minion");


        private static TagKey<IFaction<?>> tag(Identifier resourceLocation) {
            return TagKey.create(FactionRegistries.Keys.FACTION, resourceLocation);
        }

        private static TagKey<IFaction<?>> tag(String name) {
            return tag(WIdentifier.mod(name));
        }
    }

    public static class VillagerTradeTags {
        public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_1 = tag("werewolf_expert/level_1");
        public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_2 = tag("werewolf_expert/level_2");
        public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_3 = tag("werewolf_expert/level_3");
        public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_4 = tag("werewolf_expert/level_4");
        public static final TagKey<VillagerTrade> WEREWOLF_EXPERT_LEVEL_5 = tag("werewolf_expert/level_5");

        private static TagKey<VillagerTrade> tag(String name) {
            return TagKey.create(Registries.VILLAGER_TRADE, WIdentifier.mod(name));
        }

    }

    public static class SkillTreeTags {
        public static final TagKey<ISkillTree> WEREWOLF = tag("faction/werewolf");

        private static TagKey<ISkillTree> tag(String name) {
            return TagKey.create(FactionRegistries.Keys.SKILL_TREE, WIdentifier.mod(name));
        }

    }
}
