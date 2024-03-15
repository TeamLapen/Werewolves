package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.player.task.Task;
import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ModTagsProvider {

    public static void register(DataGenerator gen, @NotNull GatherDataEvent event, PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper existingFileHelper) {
        BlockTagsProvider blocks = new ModBlockTagsProvider(output, future, existingFileHelper);
        gen.addProvider(event.includeServer(), blocks);
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(output, future, blocks.contentsGetter(), existingFileHelper));
        gen.addProvider(event.includeServer(), new ModBiomeTagsProvider(output, future, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModPoiTypesProvider(output, future, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModVillageProfessionProvider(output, future, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModDamageTypeProvider(output, future, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModTasksTagProvider(output, future, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModEntityTagProvider(output, future, existingFileHelper));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider holderLookup) {
            this.tag(ModTags.Blocks.SILVER_ORE).add(ModBlocks.SILVER_ORE.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get());
            this.tag(BlockTags.LOGS).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.MAGIC_LOG.get());
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.JACARANDA_SAPLING.get(), ModBlocks.MAGIC_SAPLING.get());
            this.tag(BlockTags.LEAVES).add(ModBlocks.JACARANDA_LEAVES.get(), ModBlocks.MAGIC_LEAVES.get());
            this.tag(BlockTags.PLANKS).add(ModBlocks.MAGIC_PLANKS.get());
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), ModBlocks.STONE_ALTAR.get());
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.STONE_ALTAR.get(), ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModBlocks.SILVER_ORE.get(), ModBlocks.SILVER_BLOCK.get(), ModBlocks.RAW_SILVER_BLOCK.get());
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.MAGIC_LOG.get(), ModBlocks.MAGIC_PLANKS.get());
            this.tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModBlocks.SILVER_ORE.get(), ModBlocks.SILVER_BLOCK.get(), ModBlocks.RAW_SILVER_BLOCK.get());
            this.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(ModBlocks.DEEPSLATE_SILVER_ORE.get(), ModBlocks.SILVER_ORE.get());
            this.tag(ModTags.Blocks.STORAGE_BLOCKS_SILVER).add(ModBlocks.SILVER_BLOCK.get());
            this.tag(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER).add(ModBlocks.RAW_SILVER_BLOCK.get());
            this.tag(ModTags.Blocks.MAGIC_LOG).add(ModBlocks.MAGIC_LOG.get(), ModBlocks.STRIPPED_MAGIC_LOG.get(), ModBlocks.MAGIC_WOOD.get(), ModBlocks.STRIPPED_MAGIC_WOOD.get());
            this.tag(ModTags.Blocks.JACARANDA_LOG).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.STRIPPED_JACARANDA_LOG.get(), ModBlocks.JACARANDA_WOOD.get(), ModBlocks.STRIPPED_JACARANDA_WOOD.get());
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
            tag(BlockTags.LOGS_THAT_BURN).addTags(ModTags.Blocks.MAGIC_LOG, ModTags.Blocks.JACARANDA_LOG);
            tag(BlockTags.LOGS).addTags(ModTags.Blocks.MAGIC_LOG, ModTags.Blocks.JACARANDA_LOG);
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.JACARANDA_PRESSURE_PLATE.get(), ModBlocks.MAGIC_PRESSURE_PLATE.get());
            tag(BlockTags.WOODEN_DOORS).add(ModBlocks.JACARANDA_DOOR.get(), ModBlocks.MAGIC_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.JACARANDA_TRAPDOOR.get(), ModBlocks.MAGIC_TRAPDOOR.get());

            this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
            ;
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(ModBlocks.WOLFSBANE_DIFFUSER.get())
                    .add(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get())
                    .add(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get())
            ;
            this.tag(BlockTags.MINEABLE_WITH_AXE)
            ;
            this.tag(BlockTags.MINEABLE_WITH_HOE)
            ;
            this.tag(BlockTags.NEEDS_STONE_TOOL)
            ;
            this.tag(BlockTags.NEEDS_IRON_TOOL)
            ;
            this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                    .add(ModBlocks.WOLFSBANE_DIFFUSER.get())
                    .add(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get())
                    .add(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get())
            ;
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider holderProvider) {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, ModTags.Items.STORAGE_BLOCKS_RAW_SILVER);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);
            this.copy(ModTags.Blocks.MAGIC_LOG, ModTags.Items.MAGIC_LOG);
            this.copy(ModTags.Blocks.JACARANDA_LOG, ModTags.Items.JACARANDA_LOG);
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

            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.SILVER_INGOT.get());
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.SILVER_NUGGET.get());
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.LIVER.get(), ModItems.V.HUMAN_HEART.get(), ModItems.V.WEAK_HUMAN_HEART.get(), Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_SWORD.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get());
            this.tag(ModTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get());
            this.tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.WOLFSBANE.get().asItem(), ModBlocks.DAFFODIL.get().asItem());
            tag(ItemTags.BOATS).add(ModItems.JACARANDA_BOAT.get(), ModItems.MAGIC_BOAT.get());
            tag(ItemTags.CHEST_BOATS).add(ModItems.JACARANDA_CHEST_BOAT.get(), ModItems.MAGIC_CHEST_BOAT.get());
            this.tag(ModTags.Items.RAWMEATS,"occultism:datura", "ars_nouveau:mana_berry", "artifacts:everlasting_beef", "upgrade_aquatic:perch", "upgrade_aquatic:pike", "upgrade_aquatic:lionfish", "abnormals_delight:perch_slice", "abnormals_delight:pike_slice", "blue_skies:grittle_flatfish", "blue_skies:municipal_monkfish", "blue_skies:charscale_moki", "blue_skies:horizofin_tunid", "blue_skies:venison", "blue_skies:carabeef", "blue_skies:monitor_tail", "twilightforest:raw_meef", "twilightforest:raw_venison", "twilightforest:experiment_115", "undergarden:raw_dweller_meat", "undergarden:raw_gwibling", "undergarden:raw_gloomper_leg", "quark:crab_leg", "quark:frog_leg", "farmersdelight:mutton_chops", "farmersdelight:salmon_slice", "farmersdelight:cod_slice", "farmersdelight:bacon", "farmersdelight:chicken_cuts", "farmersdelight:ham", "farmersdelight:minced_beef", "forbidden_arcanus:tentacle", "forbidden_arcanus:bat_wing", "autumnity:turkey", "autumnity:turkey_piece", "potionsmaster:bezoar", "potionsmaster:gallbladder", "neapolitan:mint_chops", "biomemakeover:raw_toad", "aquaculture:fish_fillet_raw", "aquaculture:frog_legs_raw", "croptopia:anchovy", "croptopia:shrimp", "croptopia:calamari", "croptopia:glowing_calamari", "croptopia:tuna", "croptopia:bacon", "croptopia:crab", "croptopia:clam", "croptopia:oyster", "croptopia:frog_legs", "croptopia:sausage", "alexsmobs:raw_catfish", "alexsmobs:moose_ribs", "alexsmobs:blobfish", "alexsmobs:lobster_tail", "alexsmobs:flying_fish", "alexsmobs:kangaroo_meat", "alexsdelight:raw_bison", "alexsdelight:raw_bunfungus", "alexsdelight:raw_bunfungus_drumstick", "alexsdelight:raw_catfish_slice", "alexsdelight:kangaroo_shank", "alexsdelight:loose_moose_rib", "alexsdelight:bison_mince", "betteranimalsplus:calamari_raw", "betteranimalsplus:eel_meat_raw", "betteranimalsplus:turkey_leg_raw", "betteranimalsplus:crab_meat_raw", "betteranimalsplus:pheasant_raw", "betteranimalsplus:venison_raw", "culturaldelights:squid", "culturaldelights:glow_squid", "culturaldelights:raw_calamari", "delightful:venison_chops", "delightful:raw_goat", "nethersdelight:raw_stuffed_hoglin", "nethersdelight:ground_strider", "nethersdelight:strider_slice", "nethersdelight:hoglin_loin", "shepherdsdelight:raw_charque", "shepherdsdelight:raw_charque_strips", "shepherdsdelight:raw_equin", "shepherdsdelight:raw_equin_chunk", "shepherdsdelight:raw_meatloaf", "shepherdsdelight:ghast_tendril", "shepherdsdelight:ghast_fillet", "shepherdsdelight:cured_pufferfish", "shepherdsdelight:cure_pufferfish_cut", "shepherdsdelight:sausage", "shepherdsdelight:strider_meat_roll", "twilightdelight:raw_meef_slice", "twilightdelight:raw_venison_rib", "twilightdelight:experiment_113", "twilightdelight:experiment_110", "twilightdelight:hydra_piece");
            this.tag(ModTags.Items.COOKEDMEATS,"artifacts:eternal_steak", "upgrade_aquatic:cooked_perch", "upgrade_aquatic:cooked_pike", "turtlemancy:rabbit_stew", "twilightforest:cooked_meef", "twilightforest:cooked_venison", "twilightforest:meef_stroganoff", "twilightforest:hydra_chop", "upgrade_aquatic:cooked_lionfish", "abnormals_delight:cooked_perch_slice", "abnormals_delight:cooked_pike_slice", "quark:cooked_crab_leg", "quark:cooked_frog_leg", "turtlemancy:cooked_oyster", "forbidden_arcanus:bat_soup", "forbidden_arcanus:cooked_tentacle", "farmersdelight:cooked_mutton_chops", "farmersdelight:cooked_salmon_slice", "farmersdelight:cooked_cod_slice", "farmersdelight:cooked_bacon", "farmersdelight:cooked_chicken_cuts", "farmersdelight:smoked_ham", "farmersdelight:barbeque_stick", "farmersdelight:roast_chicken", "farmersdelight:honey_glazed_ham", "farmersdelight:shepherds_pie", "undergarden:dweller_steak", "undergarden:cooked_gwibling", "undergarden:gloomper_leg", "blue_skies:cooked_grittle_flatfish", "blue_skies:cooked_municipal_monkfish", "blue_skies:cooked_charscale_moki", "blue_skies:cooked_horizofin_tunid", "blue_skies:cooked_venison", "blue_skies:cooked_carabeef", "blue_skies:cooked_monitor_tail", "farmersdelight:dog_food", "farmersdelight:beef_patty", "farmersdelight:fried_egg", "autumnity:cooked_turkey", "autumnity:cooked_turkey_piece", "neapolitan:cooked_mint_chops", "biomemakeover:cooked_toad", "biomemakeover:cooked_glowfish", "alexsmobs:cooked_catfish", "alexsmobs:cooked_moose_ribs", "alexsmobs:cooked_lobster_tail", "alexsmobs:cooked_kangaroo_meat", "alexsdelight:cooked_kangaroo_shank", "alexsdelight:cooked_loose_moose_rib", "alexsdelight:bison_patty", "alexsdelight:cooked_bison", "alexsdelight:cooked_bunfungus", "alexsdelight:cooked_bunfungus_drumstick", "alexsdelight:cooked_catfish_slice", "alexsdelight:cooked_centipede_leg", "aquaculture:fish_fillet_cooked", "aquaculture:frog_legs_cooked", "croptopia:cooked_anchovy", "croptopia:cooked_shrimp", "croptopia:cooked_calamari", "croptopia:cooked_tuna", "croptopia:cooked_bacon", "croptopia:steamed_crab", "croptopia:crab_legs", "croptopia:steamed_clams", "croptopia:grilled_oysters", "croptopia:sunny_side_eggs", "croptopia:fried_frog_legs", "culturaldelights:cooked_squid", "culturaldelights:cooked_calamari", "delightful:cooked_venison_chops", "delightful:cooked_goat", "delightful:crab_rangoon", "ecologics:crab_meat", "betteranimalsplus:calamari_cooked", "betteranimalsplus:eel_meat_cooked", "betteranimalsplus:turkey_leg_cooked", "betteranimalsplus:crab_meat_cooked", "betteranimalsplus:pheasant_cooked", "betteranimalsplus:venison_cooked", "betteranimalsplus:turkey_cooked", "betteranimalsplus:fried_egg", "nethersdelight:hoglin_ear", "nethersdelight:grilled_strider", "shepherdsdelight:roast_charque", "shepherdsdelight:roast_charque_strips", "shepherdsdelight:equin_steak", "shepherdsdelight:equin_steak_chunk", "shepherdsdelight:cooked_meatloaf", "shepherdsdelight:cooked_fugu", "shepherdsdelight:cooked_fugu_cut", "shepherdsdelight:smoked_sausage", "twilightdelight:cooked_meef_slice", "twilightdelight:cooked_venison_rib");
            this.tag(ModTags.Items.WOLF_PELT_ARMOR_NORMAL).add(ModItems.PELT_HELMET.get(), ModItems.PELT_CHESTPLATE.get(), ModItems.PELT_LEGGINGS.get(), ModItems.PELT_BOOTS.get());
            this.tag(ModTags.Items.WOLF_PELT_ARMOR_ENHANCED).add(ModItems.DARK_PELT_HELMET.get(), ModItems.DARK_PELT_CHESTPLATE.get(), ModItems.DARK_PELT_LEGGINGS.get(), ModItems.DARK_PELT_BOOTS.get());
            this.tag(ModTags.Items.WOLF_PELT_ARMOR_ULTIMATE).add(ModItems.WHITE_PELT_HELMET.get(), ModItems.WHITE_PELT_CHESTPLATE.get(), ModItems.WHITE_PELT_LEGGINGS.get(), ModItems.WHITE_PELT_BOOTS.get());
            this.tag(ModTags.Items.WOLF_PELT_ARMOR).addTags(ModTags.Items.WOLF_PELT_ARMOR_NORMAL, ModTags.Items.WOLF_PELT_ARMOR_ENHANCED, ModTags.Items.WOLF_PELT_ARMOR_ULTIMATE);
            this.tag(ModTags.Items.SILVER_ARMOR).add(ModItems.SILVER_CHESTPLATE.get(), ModItems.SILVER_HELMET.get(), ModItems.SILVER_LEGGINGS.get(), ModItems.SILVER_BOOTS.get());
            this.tag(ModTags.Items.SILVER_ITEM).addTags(ModTags.Items.SILVER_INGOT, ModTags.Items.SILVER_TOOL, ModTags.Items.SILVER_NUGGET, ModTags.Items.RAW_MATERIALS_SILVER, ModTags.Items.STORAGE_BLOCKS_RAW_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER, ModTags.Items.SILVER_ARMOR);
            this.tag(ModTags.Items.MEAT).addTags(ModTags.Items.RAWMEATS, ModTags.Items.COOKEDMEATS);
        }

        @SuppressWarnings("UnusedReturnValue")
        private TagAppender<Item> tag(TagKey<Item> tag, String... locations) {
            return Arrays.stream(locations).map(ResourceLocation::new).reduce((TagsProvider.TagAppender<Item>)this.tag(tag), TagAppender::addOptional, (a, b) -> a);
        }
    }

    private static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider holderProvider) {
            this.tag(ModTags.Biomes.IS_WEREWOLF_BIOME).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(ModTags.Biomes.HasSpawn.WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModTags.Biomes.NoSpawn.WEREWOLF).addTag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME);
            this.tag(ModTags.Biomes.HasSpawn.HUMAN_WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModTags.Biomes.NoSpawn.HUMAN_WEREWOLF).addTag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME);
            this.tag(ModTags.Biomes.HasGen.SILVER_ORE).addTag(BiomeTags.IS_OVERWORLD);
            this.tag(ModTags.Biomes.HasGen.WOLFSBANE).addTag(BiomeTags.IS_FOREST);
            this.tag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME).addTag(ModTags.Biomes.IS_WEREWOLF_BIOME);
            this.tag(BiomeTags.IS_OVERWORLD).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(Tags.Biomes.IS_DENSE).add(ModBiomes.WEREWOLF_FOREST);
            this.tag(Tags.Biomes.IS_MAGICAL).add(ModBiomes.WEREWOLF_FOREST);
        }

        @Nonnull
        @Override
        public String getName() {
            return "Werewolves " + super.getName();
        }
    }

    private static class ModPoiTypesProvider extends PoiTypeTagsProvider {

        public ModPoiTypesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider holderProvider) {
            this.tag(ModTags.PoiTypes.IS_WEREWOLF).add(ModVillage.WEREWOLF_FACTION.getKey());
            this.tag(ModTags.PoiTypes.HAS_FACTION).addTags(ModTags.PoiTypes.IS_WEREWOLF);
            tag(PoiTypeTags.ACQUIRABLE_JOB_SITE).add(ModVillage.WEREWOLF_FACTION.getKey());
            tag(PoiTypeTags.VILLAGE).add(ModVillage.WEREWOLF_FACTION.getKey());
        }
    }

    private static class ModVillageProfessionProvider extends TagsProvider<VillagerProfession> {
        public ModVillageProfessionProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, ForgeRegistries.Keys.VILLAGER_PROFESSIONS, lookupProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider holderProvider) {
            this.tag(ModTags.Professions.IS_WEREWOLF).add(ModVillage.WEREWOLF_EXPERT.getKey());
            this.tag(de.teamlapen.vampirism.core.ModTags.Professions.HAS_FACTION).addTag(ModTags.Professions.IS_WEREWOLF);
        }
    }

    private static class ModDamageTypeProvider extends TagsProvider<DamageType> {

        public ModDamageTypeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, Registries.DAMAGE_TYPE, lookupProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            this.tag(ModTags.DamageTypes.WEREWOLF_FUR_IMMUNE).add(DamageTypes.SWEET_BERRY_BUSH, DamageTypes.CACTUS, DamageTypes.HOT_FLOOR);
            this.tag(DamageTypeTags.WITCH_RESISTANT_TO).add(ModDamageTypes.BLOOD_LOSS);
            this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypes.BLOOD_LOSS);
        }
    }

    private static class ModTasksTagProvider extends TagsProvider<Task> {

        public ModTasksTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, VampirismRegistries.TASK_ID, lookupProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            this.tag(ModTags.Tasks.AWARDS_LORD_LEVEL).add(ModTasks.WEREWOLF_LORD_1, ModTasks.WEREWOLF_LORD_2, ModTasks.WEREWOLF_LORD_3, ModTasks.WEREWOLF_LORD_4, ModTasks.WEREWOLF_LORD_5);
            this.tag(ModTags.Tasks.HAS_FACTION).addTags(ModTags.Tasks.IS_WEREWOLF);
            this.tag(ModTags.Tasks.IS_WEREWOLF).add(ModTasks.WEREWOLF_LORD_1, ModTasks.WEREWOLF_LORD_2, ModTasks.WEREWOLF_LORD_3, ModTasks.WEREWOLF_LORD_4, ModTasks.WEREWOLF_LORD_5, ModTasks.WEREWOLF_MINION_BINDING, ModTasks.WEREWOLF_MINION_UPGRADE_SIMPLE, ModTasks.WEREWOLF_MINION_UPGRADE_ENHANCED, ModTasks.WEREWOLF_MINION_UPGRADE_SPECIAL, ModTasks.RANDOM_REFINEMENT_1, ModTasks.RANDOM_REFINEMENT_2, ModTasks.RANDOM_REFINEMENT_3, ModTasks.RANDOM_RARE_REFINEMENT);
        }
    }

    private static class ModEntityTagProvider extends EntityTypeTagsProvider {


        public ModEntityTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider pProvider) {
            this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(ModEntities.WEREWOLF_SURVIVALIST.get());
            this.tag(ModTags.Entities.WEREWOLF).add(ModEntities.ALPHA_WEREWOLF.get(), ModEntities.WEREWOLF_BEAST.get(), ModEntities.WEREWOLF_SURVIVALIST.get(), ModEntities.HUMAN_WEREWOLF.get(), ModEntities.WEREWOLF_MINION.get(), ModEntities.TASK_MASTER_WEREWOLF.get());
        }
    }
}
