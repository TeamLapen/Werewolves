package de.teamlapen.werewolves.data.provider;

import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.refinements.IRefinementSet;
import de.teamlapen.faction.api.factions.skills.ISkillTree;
import de.teamlapen.faction.api.factions.tasks.Task;
import de.teamlapen.faction.api.tags.FactionRefinementSetTags;
import de.teamlapen.faction.api.tags.FactionSkillTreeTags;
import de.teamlapen.faction.common.tags.FactionEffectTags;
import de.teamlapen.faction.common.tags.FactionPoiTypeTags;
import de.teamlapen.faction.common.tags.FactionProfessionTags;
import de.teamlapen.werewolves.common.core.*;
import de.teamlapen.werewolves.common.tags.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.objectweb.asm.tree.ModuleExportNode;

import java.util.concurrent.CompletableFuture;

public class ModTagsProvider {

    public static void register(DataGenerator gen, GatherDataEvent event, PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        BlockTagsProvider blocks = new ModBlockTagsProvider(output, future);
        gen.addProvider(true, blocks);
        gen.addProvider(true, new ModItemTagsProvider(output, future, blocks.contentsGetter()));
        gen.addProvider(true, new ModBiomeTagsProvider(output, future));
        gen.addProvider(true, new ModPoiTypesProvider(output, future));
        gen.addProvider(true, new ModVillageProfessionProvider(output, future));
        gen.addProvider(true, new ModDamageTypeProvider(output, future));
        gen.addProvider(true, new ModTasksTagProvider(output, future));
        gen.addProvider(true, new ModRefinementSetTagsProvider(output, future));
        gen.addProvider(true, new ModEntityTagProvider(output, future));
        gen.addProvider(true, new ModVillageTradesTagProvider(output, future));
        gen.addProvider(true, new ModSkillTreeTagsProvider(output, future));
        gen.addProvider(true, new ModFactionTagProvider(output, future));
        gen.addProvider(true, new ModEffectTagsProvider(output, future));
        gen.addProvider(true, new ModDataComponentTagsProvider(output, future));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, REFERENCE.MODID);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void addTags(HolderLookup.Provider holderLookup) {
            this.tag(ModBlockTags.SILVER_ORE).add(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get());
            this.tag(BlockTags.LOGS).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.MAGIC_LOG.get());
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.JACARANDA_SAPLING.get(), ModBlocks.MAGIC_SAPLING.get());
            this.tag(BlockTags.LEAVES).add(ModBlocks.JACARANDA_LEAVES.get(), ModBlocks.MAGIC_LEAVES.get());
            this.tag(BlockTags.PLANKS).add(ModBlocks.MAGIC_PLANKS.get());
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), ModBlocks.STONE_ALTAR.get());
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.STONE_ALTAR.get(), ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModBlocks.SILVER_ORE.get(), ModBlocks.SILVER_BLOCK.get(), ModBlocks.RAW_SILVER_BLOCK.get());
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.MAGIC_LOG.get(), ModBlocks.MAGIC_PLANKS.get());
            this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModBlocks.SILVER_ORE.get(), ModBlocks.SILVER_BLOCK.get(), ModBlocks.RAW_SILVER_BLOCK.get());
            this.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModBlocks.SILVER_ORE.get());
            this.tag(ModBlockTags.STORAGE_BLOCKS_SILVER).add(ModBlocks.SILVER_BLOCK.get());
            this.tag(ModBlockTags.STORAGE_BLOCKS_RAW_SILVER).add(ModBlocks.RAW_SILVER_BLOCK.get());
            this.tag(ModBlockTags.MAGIC_LOG).add(ModBlocks.MAGIC_LOG.get(), ModBlocks.STRIPPED_MAGIC_LOG.get(), ModBlocks.MAGIC_WOOD.get(), ModBlocks.STRIPPED_MAGIC_WOOD.get());
            this.tag(ModBlockTags.JACARANDA_LOG).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.STRIPPED_JACARANDA_LOG.get(), ModBlocks.JACARANDA_WOOD.get(), ModBlocks.STRIPPED_JACARANDA_WOOD.get());
            this.tag(BlockTags.FLOWER_POTS).add(ModBlocks.POTTED_WOLFSBANE.get(), ModBlocks.POTTED_DAFFODIL.get());

            tag(BlockTags.LEAVES).add(ModBlocks.JACARANDA_LEAVES.get(), ModBlocks.MAGIC_LEAVES.get());
            tag(BlockTags.SAPLINGS).add(ModBlocks.JACARANDA_SAPLING.get(), ModBlocks.MAGIC_SAPLING.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.JACARANDA_TRAPDOOR.get(), ModBlocks.MAGIC_TRAPDOOR.get());
            tag(BlockTags.WOODEN_DOORS).add(ModBlocks.JACARANDA_DOOR.get(), ModBlocks.MAGIC_DOOR.get());
            tag(BlockTags.PLANKS).add(ModBlocks.JACARANDA_PLANKS.get(), ModBlocks.MAGIC_PLANKS.get());
            tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.JACARANDA_BUTTON.get(), ModBlocks.MAGIC_BUTTON.get());
            tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.JACARANDA_STAIRS.get(), ModBlocks.MAGIC_STAIRS.get());
            tag(BlockTags.WOODEN_SLABS).add(ModBlocks.JACARANDA_SLAB.get(), ModBlocks.MAGIC_SLAB.get());
            tag(BlockTags.WOODEN_FENCES).add(ModBlocks.JACARANDA_FENCE.get(), ModBlocks.MAGIC_FENCE.get());
            tag(BlockTags.LOGS_THAT_BURN).addTags(ModBlockTags.MAGIC_LOG, ModBlockTags.JACARANDA_LOG);
            tag(BlockTags.LOGS).addTags(ModBlockTags.MAGIC_LOG, ModBlockTags.JACARANDA_LOG);
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.JACARANDA_PRESSURE_PLATE.get(), ModBlocks.MAGIC_PRESSURE_PLATE.get());
            tag(BlockTags.WOODEN_DOORS).add(ModBlocks.JACARANDA_DOOR.get(), ModBlocks.MAGIC_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.JACARANDA_TRAPDOOR.get(), ModBlocks.MAGIC_TRAPDOOR.get());

            this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
            ;

            this.tag(BlockTags.MINEABLE_WITH_AXE)
            ;
            this.tag(BlockTags.MINEABLE_WITH_HOE)
            ;
            this.tag(BlockTags.NEEDS_STONE_TOOL)
            ;
            this.tag(BlockTags.NEEDS_IRON_TOOL)
            ;

            this.tag(ModBlockTags.INCORRECT_FOR_SILVER_TOOL).addTag(BlockTags.NEEDS_DIAMOND_TOOL);
        }
    }

    private static class ModItemTagsProvider extends BlockTagCopyingItemTagProvider {
        public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blocks) {
            super(output, lookupProvider, blocks, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider holderProvider) {
            this.copy(ModBlockTags.SILVER_ORE, ModItemTags.SILVER_ORE);
            this.copy(ModBlockTags.STORAGE_BLOCKS_RAW_SILVER, ModItemTags.STORAGE_BLOCKS_RAW_SILVER);
            this.copy(ModBlockTags.STORAGE_BLOCKS_SILVER, ModItemTags.STORAGE_BLOCKS_SILVER);
            this.copy(ModBlockTags.MAGIC_LOG, ModItemTags.MAGIC_LOG);
            this.copy(ModBlockTags.JACARANDA_LOG, ModItemTags.JACARANDA_LOG);
            this.copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
            this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
            this.copy(BlockTags.LOGS, ItemTags.LOGS);
            this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
            this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
            this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
            this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
            this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
            this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
            this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
            this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
            this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            this.copy(BlockTags.LEAVES, ItemTags.LEAVES);

            this.tag(ModItemTags.SILVER_INGOT).add(ModItems.SILVER_INGOT.get());
            this.tag(ModItemTags.SILVER_NUGGET).add(ModItems.SILVER_NUGGET.get());
            this.tag(ModItemTags.RAW_MEAT).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.LIVER.get(), Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModItemTags.COOKED_MEAT).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModItemTags.RAW_FISH).add(Items.COD, Items.SALMON, Items.PUFFERFISH, Items.TROPICAL_FISH);
            this.tag(ModItemTags.COOKED_FISH).add(Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModItemTags.SILVER_TOOL).add(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_SWORD.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get());
            this.tag(ModItemTags.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get());
            this.tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.WOLFSBANE.get().asItem(), ModBlocks.DAFFODIL.get().asItem());
            tag(ItemTags.BOATS).add(ModItems.JACARANDA_BOAT.get(), ModItems.MAGIC_BOAT.get());
            tag(ItemTags.CHEST_BOATS).add(ModItems.JACARANDA_CHEST_BOAT.get(), ModItems.MAGIC_CHEST_BOAT.get());
//            this.tag(ModTags.Items.RAW_MEAT,"occultism:datura", "ars_nouveau:mana_berry", "artifacts:everlasting_beef",  "blue_skies:venison", "blue_skies:carabeef", "blue_skies:monitor_tail", "twilightforest:raw_meef", "twilightforest:raw_venison", "twilightforest:experiment_115", "undergarden:raw_dweller_meat", "undergarden:raw_gwibling", "undergarden:raw_gloomper_leg", "quark:crab_leg", "quark:frog_leg", "farmersdelight:mutton_chops", "farmersdelight:bacon", "farmersdelight:chicken_cuts", "farmersdelight:ham", "farmersdelight:minced_beef",  "forbidden_arcanus:bat_wing", "autumnity:turkey", "autumnity:turkey_piece", "potionsmaster:bezoar", "potionsmaster:gallbladder", "neapolitan:mint_chops", "biomemakeover:raw_toad", "aquaculture:frog_legs_raw", "croptopia:anchovy",  "croptopia:bacon",  "croptopia:frog_legs", "croptopia:sausage", "alexsmobs:raw_catfish", "alexsmobs:moose_ribs",  "alexsmobs:kangaroo_meat", "alexsdelight:raw_bison", "alexsdelight:raw_bunfungus", "alexsdelight:raw_bunfungus_drumstick", "alexsdelight:kangaroo_shank", "alexsdelight:loose_moose_rib", "alexsdelight:bison_mince",  "betteranimalsplus:turkey_leg_raw", "betteranimalsplus:pheasant_raw", "betteranimalsplus:venison_raw",  "delightful:venison_chops", "delightful:raw_goat", "nethersdelight:raw_stuffed_hoglin", "nethersdelight:ground_strider", "nethersdelight:strider_slice", "nethersdelight:hoglin_loin", "shepherdsdelight:raw_charque", "shepherdsdelight:raw_charque_strips", "shepherdsdelight:raw_equin", "shepherdsdelight:raw_equin_chunk", "shepherdsdelight:raw_meatloaf", "shepherdsdelight:ghast_tendril", "shepherdsdelight:ghast_fillet", "shepherdsdelight:cured_pufferfish", "shepherdsdelight:cure_pufferfish_cut", "shepherdsdelight:sausage", "shepherdsdelight:strider_meat_roll", "twilightdelight:raw_meef_slice", "twilightdelight:raw_venison_rib", "twilightdelight:experiment_113", "twilightdelight:experiment_110", "twilightdelight:hydra_piece");
//            this.tag(ModTags.Items.COOKED_MEAT,"artifacts:eternal_steak", "upgrade_aquatic:cooked_perch", "upgrade_aquatic:cooked_pike", "turtlemancy:rabbit_stew", "twilightforest:cooked_meef", "twilightforest:cooked_venison", "twilightforest:meef_stroganoff", "twilightforest:hydra_chop",  "quark:cooked_frog_leg", "turtlemancy:cooked_oyster", "forbidden_arcanus:bat_soup",  "farmersdelight:cooked_mutton_chops",  "farmersdelight:cooked_bacon", "farmersdelight:cooked_chicken_cuts", "farmersdelight:smoked_ham", "farmersdelight:barbeque_stick", "farmersdelight:roast_chicken", "farmersdelight:honey_glazed_ham", "farmersdelight:shepherds_pie", "undergarden:dweller_steak", "undergarden:cooked_gwibling", "undergarden:gloomper_leg",  "blue_skies:cooked_venison", "blue_skies:cooked_carabeef", "blue_skies:cooked_monitor_tail", "farmersdelight:dog_food", "farmersdelight:beef_patty", "farmersdelight:fried_egg", "autumnity:cooked_turkey", "autumnity:cooked_turkey_piece", "neapolitan:cooked_mint_chops", "biomemakeover:cooked_toad",  "alexsmobs:cooked_moose_ribs",  "alexsmobs:cooked_kangaroo_meat", "alexsdelight:cooked_kangaroo_shank", "alexsdelight:cooked_loose_moose_rib", "alexsdelight:bison_patty", "alexsdelight:cooked_bison", "alexsdelight:cooked_bunfungus", "alexsdelight:cooked_bunfungus_drumstick",  "alexsdelight:cooked_centipede_leg", "aquaculture:frog_legs_cooked", "croptopia:cooked_anchovy",  "croptopia:cooked_bacon",  "croptopia:sunny_side_eggs", "croptopia:fried_frog_legs", "delightful:cooked_venison_chops", "delightful:cooked_goat",  "betteranimalsplus:turkey_leg_cooked", "betteranimalsplus:pheasant_cooked", "betteranimalsplus:venison_cooked", "betteranimalsplus:turkey_cooked", "betteranimalsplus:fried_egg", "nethersdelight:hoglin_ear", "nethersdelight:grilled_strider", "shepherdsdelight:roast_charque", "shepherdsdelight:roast_charque_strips", "shepherdsdelight:equin_steak", "shepherdsdelight:equin_steak_chunk", "shepherdsdelight:cooked_meatloaf", "shepherdsdelight:cooked_fugu", "shepherdsdelight:cooked_fugu_cut", "shepherdsdelight:smoked_sausage", "twilightdelight:cooked_meef_slice", "twilightdelight:cooked_venison_rib");
//            this.tag(ModTags.Items.RAW_FISH, "upgrade_aquatic:perch", "upgrade_aquatic:pike", "upgrade_aquatic:lionfish", "abnormals_delight:perch_slice", "abnormals_delight:pike_slice", "blue_skies:grittle_flatfish", "blue_skies:municipal_monkfish", "blue_skies:charscale_moki", "blue_skies:horizofin_tunid", "farmersdelight:salmon_slice", "farmersdelight:cod_slice","forbidden_arcanus:tentacle", "aquaculture:fish_fillet_raw","croptopia:shrimp", "croptopia:calamari", "croptopia:glowing_calamari", "croptopia:tuna","croptopia:crab", "croptopia:clam", "croptopia:oyster","alexsmobs:blobfish", "alexsmobs:lobster_tail", "alexsmobs:flying_fish","alexsdelight:raw_catfish_slice", "betteranimalsplus:calamari_raw", "betteranimalsplus:eel_meat_raw", "betteranimalsplus:crab_meat_raw","culturaldelights:squid", "culturaldelights:glow_squid", "culturaldelights:raw_calamari");
//            this.tag(ModTags.Items.COOKED_FISH, "upgrade_aquatic:cooked_lionfish", "abnormals_delight:cooked_perch_slice", "abnormals_delight:cooked_pike_slice", "quark:cooked_crab_leg","forbidden_arcanus:cooked_tentacle","farmersdelight:cooked_salmon_slice", "farmersdelight:cooked_cod_slice","blue_skies:cooked_grittle_flatfish", "blue_skies:cooked_municipal_monkfish", "blue_skies:cooked_charscale_moki", "blue_skies:cooked_horizofin_tunid","biomemakeover:cooked_glowfish", "alexsmobs:cooked_catfish","alexsmobs:cooked_lobster_tail","alexsdelight:cooked_catfish_slice","aquaculture:fish_fillet_cooked", "croptopia:cooked_shrimp", "croptopia:cooked_calamari", "croptopia:cooked_tuna","croptopia:steamed_crab", "croptopia:crab_legs", "croptopia:steamed_clams", "croptopia:grilled_oysters","culturaldelights:cooked_squid", "culturaldelights:cooked_calamari","delightful:crab_rangoon", "ecologics:crab_meat", "betteranimalsplus:calamari_cooked", "betteranimalsplus:eel_meat_cooked", "betteranimalsplus:crab_meat_cooked");
//            this.tags(ModTags.Items.RAW_MEAT, "forge:raw_bacon", "forge:raw_beef", "forge:raw_chicken", "forge:raw_mutton", "forge:raw_pork");
//            this.tags(ModTags.Items.RAW_FISH, "forge:raw_fishes");
//            this.tags(ModTags.Items.COOKED_MEAT, "forge:cooked_bacon", "forge:cooked_beef", "forge:cooked_chicken", "forge:cooked_mutton", "forge:cooked_pork");
//            this.tags(ModTags.Items.COOKED_FISH, "forge:cooked_fishes");
            this.tag(ModItemTags.WOLF_PELT_ARMOR_NORMAL).add(ModItems.PELT_HELMET.get(), ModItems.PELT_CHESTPLATE.get(), ModItems.PELT_LEGGINGS.get(), ModItems.PELT_BOOTS.get());
            this.tag(ModItemTags.WOLF_PELT_ARMOR_ENHANCED).add(ModItems.DARK_PELT_HELMET.get(), ModItems.DARK_PELT_CHESTPLATE.get(), ModItems.DARK_PELT_LEGGINGS.get(), ModItems.DARK_PELT_BOOTS.get());
            this.tag(ModItemTags.WOLF_PELT_ARMOR_ULTIMATE).add(ModItems.WHITE_PELT_HELMET.get(), ModItems.WHITE_PELT_CHESTPLATE.get(), ModItems.WHITE_PELT_LEGGINGS.get(), ModItems.WHITE_PELT_BOOTS.get());
            this.tag(ModItemTags.WOLF_PELT_ARMOR).addTags(ModItemTags.WOLF_PELT_ARMOR_NORMAL, ModItemTags.WOLF_PELT_ARMOR_ENHANCED, ModItemTags.WOLF_PELT_ARMOR_ULTIMATE);
            this.tag(ModItemTags.SILVER_ARMOR).add(ModItems.SILVER_CHESTPLATE.get(), ModItems.SILVER_HELMET.get(), ModItems.SILVER_LEGGINGS.get(), ModItems.SILVER_BOOTS.get());
            this.tag(ModItemTags.SILVER_ITEM).addTags(ModItemTags.SILVER_INGOT, ModItemTags.SILVER_TOOL, ModItemTags.SILVER_NUGGET, ModItemTags.RAW_MATERIALS_SILVER, ModItemTags.STORAGE_BLOCKS_RAW_SILVER, ModItemTags.STORAGE_BLOCKS_SILVER, ModItemTags.SILVER_ARMOR);
            this.tag(ModItemTags.RAW_FOOD).addTags(ModItemTags.RAW_MEAT, ModItemTags.RAW_FISH);
            this.tag(ModItemTags.COOKED_FOOD).addTags(ModItemTags.COOKED_MEAT, ModItemTags.COOKED_FISH);
            this.tag(ModItemTags.WEREWOLF_FOOD).addTags(ModItemTags.RAW_FOOD, ModItemTags.COOKED_FISH).addTag(ItemTags.MEAT).add(ModItems.WOLF_BERRIES.get(), Items.SWEET_BERRIES, Items.GLOW_BERRIES);
            this.tag(ItemTags.SWORDS).add(ModItems.SILVER_SWORD.get());
            this.tag(ItemTags.AXES).add(ModItems.SILVER_AXE.get());
            this.tag(ItemTags.PICKAXES).add(ModItems.SILVER_PICKAXE.get());
            this.tag(ItemTags.SHOVELS).add(ModItems.SILVER_SHOVEL.get());
            this.tag(ItemTags.HOES).add(ModItems.SILVER_HOE.get());
            this.tag(ItemTags.HEAD_ARMOR).add(ModItems.SILVER_HELMET.get(), ModItems.PELT_HELMET.get(), ModItems.DARK_PELT_HELMET.get(), ModItems.WHITE_PELT_HELMET.get());
            this.tag(ItemTags.CHEST_ARMOR).add(ModItems.SILVER_CHESTPLATE.get(), ModItems.PELT_CHESTPLATE.get(), ModItems.DARK_PELT_CHESTPLATE.get(), ModItems.WHITE_PELT_CHESTPLATE.get());
            this.tag(ItemTags.LEG_ARMOR).add(ModItems.SILVER_LEGGINGS.get(), ModItems.PELT_LEGGINGS.get(), ModItems.DARK_PELT_LEGGINGS.get(), ModItems.WHITE_PELT_LEGGINGS.get());
            this.tag(ItemTags.FOOT_ARMOR).add(ModItems.SILVER_BOOTS.get(), ModItems.PELT_BOOTS.get(), ModItems.DARK_PELT_BOOTS.get(), ModItems.WHITE_PELT_BOOTS.get());
            this.tag(Tags.Items.MELEE_WEAPON_TOOLS).add(ModItems.SILVER_SWORD.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_AXE.get(), ModItems.SILVER_SHOVEL.get(), ModItems.SILVER_HOE.get());
        }
    }

    private static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider holderProvider) {
            this.tag(ModBiomeTags.IS_WEREWOLF_BIOME).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(ModBiomeTags.HasSpawn.WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModBiomeTags.NoSpawn.WEREWOLF).addTag(ModBiomeTags.IS_FACTION_BIOME);
            this.tag(ModBiomeTags.HasSpawn.HUMAN_WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModBiomeTags.NoSpawn.HUMAN_WEREWOLF).addTag(ModBiomeTags.IS_FACTION_BIOME);
            this.tag(ModBiomeTags.HasGen.SILVER_ORE).addTag(BiomeTags.IS_OVERWORLD);
            this.tag(ModBiomeTags.HasGen.WOLFSBANE).addTag(BiomeTags.IS_FOREST);
            this.tag(ModBiomeTags.IS_FACTION_BIOME).addTag(ModBiomeTags.IS_WEREWOLF_BIOME);
            this.tag(BiomeTags.IS_OVERWORLD).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(Tags.Biomes.IS_DENSE_VEGETATION_OVERWORLD).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(Tags.Biomes.IS_MAGICAL).add(ModBiomes.WEREWOLF_FOREST);
        }

        @Override
        public String getName() {
            return "Werewolves " + super.getName();
        }
    }

    private static class ModPoiTypesProvider extends PoiTypeTagsProvider {

        public ModPoiTypesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider holderProvider) {
            this.tag(ModPoiTypeTags.IS_WEREWOLF).add(ModVillage.WEREWOLF_FACTION.getKey());
            this.tag(FactionPoiTypeTags.HAS_FACTION).addTags(ModPoiTypeTags.IS_WEREWOLF);
            tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(ModVillage.WEREWOLF_FACTION.getKey());
            tag(PoiTypeTags.VILLAGE).add(ModVillage.WEREWOLF_FACTION.getKey());
        }
    }

    private static class ModVillageProfessionProvider extends KeyTagProvider<VillagerProfession> {
        public ModVillageProfessionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, Registries.VILLAGER_PROFESSION, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider holderProvider) {
            this.tag(ModProfessionTags.IS_WEREWOLF).add(ModVillage.WEREWOLF_EXPERT.getKey());
            this.tag(FactionProfessionTags.HAS_FACTION).addTag(ModProfessionTags.IS_WEREWOLF);
        }
    }

    private static class ModDamageTypeProvider extends KeyTagProvider<DamageType> {

        public ModDamageTypeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, Registries.DAMAGE_TYPE, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            this.tag(ModDamageTypeTags.WEREWOLF_FUR_IMMUNE).add(DamageTypes.SWEET_BERRY_BUSH, DamageTypes.CACTUS, DamageTypes.HOT_FLOOR);
            this.tag(DamageTypeTags.WITCH_RESISTANT_TO).add(ModDamageTypes.BLOOD_LOSS);
            this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypes.BLOOD_LOSS);
            this.tag(ModDamageTypeTags.WEREWOLF_ARMOR_REDUCTION).addTag(DamageTypeTags.IS_PLAYER_ATTACK).add(ModDamageTypes.BITE);
            this.tag(ModDamageTypeTags.BITE).add(ModDamageTypes.BITE);
        }
    }

    private static class ModTasksTagProvider extends KeyTagProvider<Task> {

        public ModTasksTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, FactionRegistries.Keys.TASK, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            this.tag(ModTaskTags.AWARDS_LORD_LEVEL).add(ModTasks.WEREWOLF_LORD_1, ModTasks.WEREWOLF_LORD_2, ModTasks.WEREWOLF_LORD_3, ModTasks.WEREWOLF_LORD_4, ModTasks.WEREWOLF_LORD_5);
            this.tag(ModTaskTags.HAS_FACTION).addTags(ModTaskTags.IS_WEREWOLF);
            this.tag(ModTaskTags.IS_WEREWOLF).add(ModTasks.WEREWOLF_LORD_1, ModTasks.WEREWOLF_LORD_2, ModTasks.WEREWOLF_LORD_3, ModTasks.WEREWOLF_LORD_4, ModTasks.WEREWOLF_LORD_5, ModTasks.WEREWOLF_MINION_BINDING, ModTasks.WEREWOLF_MINION_UPGRADE_SIMPLE, ModTasks.WEREWOLF_MINION_UPGRADE_ENHANCED, ModTasks.WEREWOLF_MINION_UPGRADE_SPECIAL, ModTasks.RANDOM_REFINEMENT_1, ModTasks.RANDOM_REFINEMENT_2, ModTasks.RANDOM_REFINEMENT_3, ModTasks.RANDOM_RARE_REFINEMENT);
        }
    }

    private static class ModRefinementSetTagsProvider extends KeyTagProvider<IRefinementSet> {

        public ModRefinementSetTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, FactionRegistries.Keys.REFINEMENT_SET, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            rarity(FactionRefinementSetTags.COMMON,
                    ModRefinementSets.ARMOR1,
                    ModRefinementSets.HEALTH1,
                    ModRefinementSets.SPEED1,
                    ModRefinementSets.ATTACK_SPEED1,
                    ModRefinementSets.DAMAGE1,
                    ModRefinementSets.DAMAGE1_ATTACK_SPEED1_N_ARMOR2,
                    ModRefinementSets.ARMOR1_HEALTH1_N_ATTACK_SPEED2,
                    ModRefinementSets.HEALTH_AFTER_KILL
            );
            rarity(FactionRefinementSetTags.UNCOMMON,
                    ModRefinementSets.ARMOR2_N_HEALTH2,
                    ModRefinementSets.HEALTH2_N_DAMAGE1,
                    ModRefinementSets.ATTACK_SPEED2_N_ARMOR1,
                    ModRefinementSets.DAMAGE2_N_SPEED1,
                    ModRefinementSets.SPEED2_N_DAMAGE1,
                    ModRefinementSets.ARMOR3_N_HEALTH3,
                    ModRefinementSets.HEALTH3_N_DAMAGE2,
                    ModRefinementSets.ATTACK_SPEED3_N_ARMOR2,
                    ModRefinementSets.DAMAGE3_N_SPEED2,
                    ModRefinementSets.SPEED3_N_DAMAGE2,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_SURVIVAL_1,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_BEAST_1,
                    ModRefinementSets.MORE_WOLVES,
                    ModRefinementSets.GREATER_DOGE_CHANCE
            );
            rarity(FactionRefinementSetTags.RARE,
                    ModRefinementSets.ARMOR3_N_HEALTH2,
                    ModRefinementSets.HEALTH3_N_ARMOR1,
                    ModRefinementSets.ATTACK_SPEED3_N_SPEED1,
                    ModRefinementSets.SPEED1_ARMOR1_HEALTH1,
                    ModRefinementSets.DAMAGE3_N_ARMOR1,
                    ModRefinementSets.SPEED3_N_ATTACK_SPEED1,
                    ModRefinementSets.DAMAGE1_ATTACK_SPEED1,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_GENERAL_1_N_DAMAGE_N_SPEED,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_SURVIVAL_2,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_BEAST_2,
                    ModRefinementSets.HEALTH,
                    ModRefinementSets.STUN_BITE
            );
            rarity(FactionRefinementSetTags.EPIC,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_GENERAL_2_N_DAMAGE_N_SPEED,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_GENERAL_1,
                    ModRefinementSets.BLEEDING_BITE,
                    ModRefinementSets.VARIABLE_BITE
            );
            rarity(FactionRefinementSetTags.LEGENDARY,
                    ModRefinementSets.WEREWOLF_FORM_DURATION_GENERAL_2,
                    ModRefinementSets.RAGE_FURY
            );
        }

        @SafeVarargs
        private void rarity(TagKey<IRefinementSet> rarity, ResourceKey<IRefinementSet>... sets) {
            this.tag(rarity).add(sets);
            this.tag(ModRefinementSetTags.IS_WEREWOLF).add(sets);
        }
    }

    private static class ModEntityTagProvider extends EntityTypeTagsProvider {


        public ModEntityTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider) {
            super(pOutput, pProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(ModEntities.WEREWOLF_SURVIVALIST.get());
            this.tag(ModEntityTypeTags.WEREWOLF).add(ModEntities.ALPHA_WEREWOLF.get(), ModEntities.WEREWOLF_BEAST.get(), ModEntities.WEREWOLF_SURVIVALIST.get(), ModEntities.HUMAN_WEREWOLF.get(), ModEntities.WEREWOLF_MINION.get(), ModEntities.TASK_MASTER_WEREWOLF.get());
            this.tag(ModEntityTypeTags.WEREWOLF_DEFAULT_TARGETS)
                    .addTag(EntityTypeTags.SKELETONS);
            this.tag(ModEntityTypeTags.WEREWOLF_VILLAGE_GUARDS)
                    .add(ModEntities.WEREWOLF_SURVIVALIST.get(), ModEntities.WEREWOLF_BEAST.get());
        }
    }

    private static class ModSkillTreeTagsProvider extends KeyTagProvider<ISkillTree> {

        public ModSkillTreeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, FactionRegistries.Keys.SKILL_TREE, provider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(FactionSkillTreeTags.DEFAULT).add(ModSkills.Trees.LEVEL, ModSkills.Trees.LORD);
            tag(FactionSkillTreeTags.LEVEL).add(ModSkills.Trees.LEVEL);
            tag(FactionSkillTreeTags.LORD).add(ModSkills.Trees.LORD);
            tag(ModSkillTreeTags.WEREWOLF).add(ModSkills.Trees.LEVEL, ModSkills.Trees.LORD);
        }
    }

    private static class ModVillageTradesTagProvider extends KeyTagProvider<VillagerTrade> {

        protected ModVillageTradesTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, Registries.VILLAGER_TRADE, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            this.tag(ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_1)
                    .add(ModTrades.WEREWOLF_EXPERT_1_LIVER_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_1_CRACKED_BONE_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_1_EMERALD_LIVER)
                    .add(ModTrades.WEREWOLF_EXPERT_1_EMERALD_CRACKED_BONE)
                    ;
            this.tag(ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_2)
                    .add(ModTrades.WEREWOLF_EXPERT_2_LIVER_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_2_CRACKED_BONE_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_2_EMERALD_LIVER)
                    .add(ModTrades.WEREWOLF_EXPERT_2_EMERALD_WEREWOLF_TOOTH)
                    .add(ModTrades.WEREWOLF_EXPERT_2_EMERALD_CRACKED_BONE)
                    ;
            this.tag(ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_3)
                    .add(ModTrades.WEREWOLF_EXPERT_3_LIVER_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_3_CRACKED_BONE_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_3_EMERALD_LIVER)
                    .add(ModTrades.WEREWOLF_EXPERT_3_EMERALD_CRACKED_BONE)
                    ;
            this.tag(ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_4)
                    .add(ModTrades.WEREWOLF_EXPERT_4_LIVER_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_4_CRACKED_BONE_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_4_EMERALD_LIVER)
                    .add(ModTrades.WEREWOLF_EXPERT_4_EMERALD_CRACKED_BONE)
                    ;
            this.tag(ModVillagerTradeTags.WEREWOLF_EXPERT_LEVEL_5)
                    .add(ModTrades.WEREWOLF_EXPERT_5_LIVER_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_5_CRACKED_BONE_EMERALD)
                    .add(ModTrades.WEREWOLF_EXPERT_5_EMERALD_LIVER)
                    .add(ModTrades.WEREWOLF_EXPERT_5_EMERALD_CRACKED_BONE)
                    ;
        }
    }

    private static class ModFactionTagProvider extends KeyTagProvider<IFaction<?>> {

        public ModFactionTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, FactionRegistries.Keys.FACTION, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            this.tag(ModFactionsTags.IS_WEREWOLF).add(ModFactions.WEREWOLF.getRawKey());
            this.tag(ModFactionsTags.WEREWOLF_MINION_TARGETS);
        }
    }

    private static class ModEffectTagsProvider extends KeyTagProvider<MobEffect> {

        protected ModEffectTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, Registries.MOB_EFFECT, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            tag(ModEffectTags.DISABLES_ACTIONS_WEREWOLF).addTag(FactionEffectTags.DISABLES_ACTIONS);
        }
    }

    private static class ModDataComponentTagsProvider extends KeyTagProvider<DataComponentType<?>> {

        protected ModDataComponentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, Registries.DATA_COMPONENT_TYPE, lookupProvider, REFERENCE.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider registries) {
            this.tag(ModDataComponentTags.FACTION_FOOD).addTag(ModDataComponentTags.WEREWOLF_FOOD);
            this.tag(ModDataComponentTags.WEREWOLF_FOOD);
        }
    }
}
