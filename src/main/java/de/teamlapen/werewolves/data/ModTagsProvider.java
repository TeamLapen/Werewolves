package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBiomes;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class ModTagsProvider {

    public static void addProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        BlockTagsProvider blocks = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blocks);
        generator.addProvider(new ModItemTagsProvider(generator, blocks, existingFileHelper));
        generator.addProvider(new ModBiomeTagsProvider(generator, existingFileHelper));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Blocks.SILVER_ORE).add(ModBlocks.silver_ore, ModBlocks.deepslate_silver_ore);
            this.tag(BlockTags.LOGS).add(ModBlocks.jacaranda_log, ModBlocks.magic_log);
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.jacaranda_sapling, ModBlocks.magic_sapling);
            this.tag(BlockTags.LEAVES).add(ModBlocks.jacaranda_leaves, ModBlocks.magic_leaves);
            this.tag(BlockTags.PLANKS).add(ModBlocks.magic_planks);
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.stone_altar_fire_bowl, ModBlocks.stone_altar);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.stone_altar, ModBlocks.stone_altar_fire_bowl, ModBlocks.deepslate_silver_ore, ModBlocks.silver_ore, ModBlocks.silver_block, ModBlocks.raw_silver_block);
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.jacaranda_log, ModBlocks.magic_log, ModBlocks.magic_planks);
            this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.deepslate_silver_ore, ModBlocks.silver_ore, ModBlocks.silver_block, ModBlocks.raw_silver_block);
            this.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(ModBlocks.deepslate_silver_ore, ModBlocks.silver_ore);
            this.tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.silver_block);
            this.tag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER).add(ModBlocks.raw_silver_block);
            this.tag(ModTags.Blocks.MAGIC_LOGS).add(ModBlocks.magic_log);
            this.tag(ModTags.Blocks.JACARANDA_LOGS).add(ModBlocks.jacaranda_log);
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, ModTags.Items.STORAGE_BLOCKS_RAW_SILVER);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);
            this.copy(ModTags.Blocks.MAGIC_LOGS, ModTags.Items.MAGIC_LOGS);
            this.copy(ModTags.Blocks.JACARANDA_LOGS, ModTags.Items.JACARANDA_LOGS);
            this.copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
            this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
            this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
            this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            this.copy(BlockTags.LOGS, ItemTags.LOGS);
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.silver_ingot);
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.silver_nugget);
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.liver, ModItems.V.human_heart, ModItems.V.weak_human_heart, Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.silver_axe, ModItems.silver_hoe, ModItems.silver_sword, ModItems.silver_pickaxe, ModItems.silver_shovel);
            this.tag(ModTags.Items.RAW_MATERIALS_SILVER).add(ModItems.raw_silver);

            this.tag(ModTags.Items.RAWMEATS,"occultism:datura", "ars_nouveau:mana_berry", "artifacts:everlasting_beef", "upgrade_aquatic:perch", "upgrade_aquatic:pike", "upgrade_aquatic:lionfish", "abnormals_delight:perch_slice", "abnormals_delight:pike_slice", "blue_skies:grittle_flatfish", "blue_skies:municipal_monkfish", "blue_skies:charscale_moki", "blue_skies:horizofin_tunid", "blue_skies:venison", "blue_skies:carabeef", "blue_skies:monitor_tail", "twilightforest:raw_meef", "twilightforest:raw_venison", "twilightforest:experiment_115", "undergarden:raw_dweller_meat", "undergarden:raw_gwibling", "undergarden:raw_gloomper_leg", "quark:crab_leg", "quark:frog_leg", "farmersdelight:mutton_chops", "farmersdelight:salmon_slice", "farmersdelight:cod_slice", "farmersdelight:bacon", "farmersdelight:chicken_cuts", "farmersdelight:ham", "farmersdelight:minced_beef", "forbidden_arcanus:tentacle", "forbidden_arcanus:bat_wing", "autumnity:turkey", "autumnity:turkey_piece", "potionsmaster:bezoar", "potionsmaster:gallbladder", "neapolitan:mint_chops", "biomemakeover:raw_toad", "aquaculture:fish_fillet_raw", "aquaculture:frog_legs_raw", "croptopia:anchovy", "croptopia:shrimp", "croptopia:calamari", "croptopia:glowing_calamari", "croptopia:tuna", "croptopia:bacon", "croptopia:crab", "croptopia:clam", "croptopia:oyster", "croptopia:frog_legs", "croptopia:sausage", "alexsmobs:raw_catfish", "alexsmobs:moose_ribs", "alexsmobs:blobfish", "alexsmobs:lobster_tail", "alexsmobs:flying_fish", "alexsmobs:kangaroo_meat", "alexsdelight:raw_bison", "alexsdelight:raw_bunfungus", "alexsdelight:raw_bunfungus_drumstick", "alexsdelight:raw_catfish_slice", "alexsdelight:kangaroo_shank", "alexsdelight:loose_moose_rib", "alexsdelight:bison_mince", "betteranimalsplus:calamari_raw", "betteranimalsplus:eel_meat_raw", "betteranimalsplus:turkey_leg_raw", "betteranimalsplus:crab_meat_raw", "betteranimalsplus:pheasant_raw", "betteranimalsplus:venison_raw", "culturaldelights:squid", "culturaldelights:glow_squid", "culturaldelights:raw_calamari", "delightful:venison_chops", "delightful:raw_goat", "nethersdelight:raw_stuffed_hoglin", "nethersdelight:ground_strider", "nethersdelight:strider_slice", "nethersdelight:hoglin_loin", "shepherdsdelight:raw_charque", "shepherdsdelight:raw_charque_strips", "shepherdsdelight:raw_equin", "shepherdsdelight:raw_equin_chunk", "shepherdsdelight:raw_meatloaf", "shepherdsdelight:ghast_tendril", "shepherdsdelight:ghast_fillet", "shepherdsdelight:cured_pufferfish", "shepherdsdelight:cure_pufferfish_cut", "shepherdsdelight:sausage", "shepherdsdelight:strider_meat_roll", "twilightdelight:raw_meef_slice", "twilightdelight:raw_venison_rib", "twilightdelight:experiment_113", "twilightdelight:experiment_110", "twilightdelight:hydra_piece");
            this.tag(ModTags.Items.COOKEDMEATS,"artifacts:eternal_steak", "upgrade_aquatic:cooked_perch", "upgrade_aquatic:cooked_pike", "turtlemancy:rabbit_stew", "twilightforest:cooked_meef", "twilightforest:cooked_venison", "twilightforest:meef_stroganoff", "twilightforest:hydra_chop", "upgrade_aquatic:cooked_lionfish", "abnormals_delight:cooked_perch_slice", "abnormals_delight:cooked_pike_slice", "quark:cooked_crab_leg", "quark:cooked_frog_leg", "turtlemancy:cooked_oyster", "forbidden_arcanus:bat_soup", "forbidden_arcanus:cooked_tentacle", "farmersdelight:cooked_mutton_chops", "farmersdelight:cooked_salmon_slice", "farmersdelight:cooked_cod_slice", "farmersdelight:cooked_bacon", "farmersdelight:cooked_chicken_cuts", "farmersdelight:smoked_ham", "farmersdelight:barbeque_stick", "farmersdelight:roast_chicken", "farmersdelight:honey_glazed_ham", "farmersdelight:shepherds_pie", "undergarden:dweller_steak", "undergarden:cooked_gwibling", "undergarden:gloomper_leg", "blue_skies:cooked_grittle_flatfish", "blue_skies:cooked_municipal_monkfish", "blue_skies:cooked_charscale_moki", "blue_skies:cooked_horizofin_tunid", "blue_skies:cooked_venison", "blue_skies:cooked_carabeef", "blue_skies:cooked_monitor_tail", "farmersdelight:dog_food", "farmersdelight:beef_patty", "farmersdelight:fried_egg", "autumnity:cooked_turkey", "autumnity:cooked_turkey_piece", "neapolitan:cooked_mint_chops", "biomemakeover:cooked_toad", "biomemakeover:cooked_glowfish", "alexsmobs:cooked_catfish", "alexsmobs:cooked_moose_ribs", "alexsmobs:cooked_lobster_tail", "alexsmobs:cooked_kangaroo_meat", "alexsdelight:cooked_kangaroo_shank", "alexsdelight:cooked_loose_moose_rib", "alexsdelight:bison_patty", "alexsdelight:cooked_bison", "alexsdelight:cooked_bunfungus", "alexsdelight:cooked_bunfungus_drumstick", "alexsdelight:cooked_catfish_slice", "alexsdelight:cooked_centipede_leg", "aquaculture:fish_fillet_cooked", "aquaculture:frog_legs_cooked", "croptopia:cooked_anchovy", "croptopia:cooked_shrimp", "croptopia:cooked_calamari", "croptopia:cooked_tuna", "croptopia:cooked_bacon", "croptopia:steamed_crab", "croptopia:crab_legs", "croptopia:steamed_clams", "croptopia:grilled_oysters", "croptopia:sunny_side_eggs", "croptopia:fried_frog_legs", "culturaldelights:cooked_squid", "culturaldelights:cooked_calamari", "delightful:cooked_venison_chops", "delightful:cooked_goat", "delightful:crab_rangoon", "ecologics:crab_meat", "betteranimalsplus:calamari_cooked", "betteranimalsplus:eel_meat_cooked", "betteranimalsplus:turkey_leg_cooked", "betteranimalsplus:crab_meat_cooked", "betteranimalsplus:pheasant_cooked", "betteranimalsplus:venison_cooked", "betteranimalsplus:turkey_cooked", "betteranimalsplus:fried_egg", "nethersdelight:hoglin_ear", "nethersdelight:grilled_strider", "shepherdsdelight:roast_charque", "shepherdsdelight:roast_charque_strips", "shepherdsdelight:equin_steak", "shepherdsdelight:equin_steak_chunk", "shepherdsdelight:cooked_meatloaf", "shepherdsdelight:cooked_fugu", "shepherdsdelight:cooked_fugu_cut", "shepherdsdelight:smoked_sausage", "twilightdelight:cooked_meef_slice", "twilightdelight:cooked_venison_rib");
        }

        @SuppressWarnings("UnusedReturnValue")
        private TagAppender<Item> tag(TagKey<Item> tag, String... locations) {
            return Arrays.stream(locations).map(ResourceLocation::new).reduce(this.tag(tag), TagAppender::addOptional, (a, b) -> a);
        }
    }

    private static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Biomes.IS_WEREWOLF_BIOME).add(ModBiomes.WEREWOLF_HEAVEN);
            //noinspection unchecked
            tag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME).addTags(ModTags.Biomes.IS_WEREWOLF_BIOME);
        }

        @Nonnull
        @Override
        public String getName() {
            return "Werewoloves " + super.getName();
        }
    }
}
