package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.core.ModItems;
import de.teamlapen.werewolves.core.ModTags;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Arrays;

public class ModTagsProvider {

    public static void addProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        BlockTagsProvider blocks = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blocks);
        generator.addProvider(new ModItemTagsProvider(generator, blocks, existingFileHelper));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Blocks.SILVER_ORE).add(ModBlocks.SILVER_ORE.get());
            this.tag(BlockTags.LOGS).add(ModBlocks.JACARANDA_LOG.get(), ModBlocks.MAGIC_LOG.get());
            this.tag(BlockTags.SAPLINGS).add(ModBlocks.JACARANDA_SAPLING.get(), ModBlocks.MAGIC_SAPLING.get());
            this.tag(BlockTags.LEAVES).add(ModBlocks.JACARANDA_LEAVES.get(), ModBlocks.MAGIC_LEAVES.get());
            this.tag(BlockTags.PLANKS).add(ModBlocks.MAGIC_PLANKS.get());
            this.tag(BlockTags.CAMPFIRES).add(ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), ModBlocks.STONE_ALTAR.get());
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
            this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
            this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            this.copy(BlockTags.LOGS, ItemTags.LOGS);
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.SILVER_INGOT.get());
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.SILVER_NUGGET.get());
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.LIVER.get(), ModItems.V.HUMAN_HEART.get(), ModItems.V.WEAK_HUMAN_HEART.get(), Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_SWORD.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get());

            this.tag(ModTags.Items.RAWMEATS, "occultism:datura", "ars_nouveau:mana_berry", "artifacts:everlasting_beef", "upgrade_aquatic:perch", "upgrade_aquatic:pike", "upgrade_aquatic:lionfish", "abnormals_delight:perch_slice", "abnormals_delight:pike_slice", "blue_skies:grittle_flatfish", "blue_skies:municipal_monkfish", "blue_skies:charscale_moki", "blue_skies:horizofin_tunid", "blue_skies:venison", "blue_skies:carabeef", "blue_skies:monitor_tail", "wyrmroost:raw_lowtier_meat", "wyrmroost:raw_common_meat", "wyrmroost:raw_apex_meat", "wyrmroost:raw_behemoth_meat", "wyrmroost:desert_wyrm", "twilightforest:raw_meef", "twilightforest:raw_venison", "undergarden:raw_dweller_meat", "undergarden:raw_gwibling", "undergarden:raw_gloomper_leg", "quark:crab_leg", "quark:frog_leg", "mysticalworld:raw_squid", "farmersdelight:mutton_chops", "farmersdelight:salmon_slice", "farmersdelight:cod_slice", "farmersdelight:bacon", "farmersdelight:chicken_cuts", "forbidden_arcanus:tentacle", "forbidden_arcanus:bat_wing", "autumnity:turkey", "autumnity:turkey_piece", "potionsmaster:bezoar", "potionsmaster:gallbladder", "farmersdelight:ham", "farmersdelight:minced_beef", "neapolitan:mint_chops");
            this.tag(ModTags.Items.COOKEDMEATS,"artifacts:eternal_steak", "upgrade_aquatic:cooked_perch", "upgrade_aquatic:cooked_pike", "turtlemancy:rabbit_stew", "twilightforest:cooked_meef", "twilightforest:cooked_venison", "twilightforest:meef_stroganoff", "twilightforest:hydra_chop", "upgrade_aquatic:cooked_lionfish", "abnormals_delight:cooked_perch_slice", "abnormals_delight:cooked_pike_slice", "wyrmroost:cooked_lowtier_meat", "wyrmroost:cooked_common_meat", "wyrmroost:cooked_apex_meat", "wyrmroost:cooked_behemoth_meat", "wyrmroost:cooked_desert_wyrm", "quark:cooked_crab_leg", "quark:cooked_frog_leg", "turtlemancy:cooked_oyster", "mysticalworld:cooked_squid", "mysticalworld:epic_squid", "mysticalworld:cooked_venison", "forbidden_arcanus:bat_soup", "forbidden_arcanus:cooked_tentacle", "farmersdelight:cooked_mutton_chops", "farmersdelight:cooked_salmon_slice", "farmersdelight:cooked_cod_slice", "farmersdelight:cooked_bacon", "farmersdelight:cooked_chicken_cuts", "farmersdelight:smoked_ham", "farmersdelight:barbeque_stick", "farmersdelight:roast_chicken", "farmersdelight:honey_glazed_ham", "farmersdelight:shepherds_pie", "undergarden:dweller_steak", "undergarden:cooked_gwibling", "undergarden:gloomper_leg", "blue_skies:cooked_grittle_flatfish", "blue_skies:cooked_municipal_monkfish", "blue_skies:cooked_charscale_moki", "blue_skies:cooked_horizofin_tunid", "blue_skies:cooked_venison", "blue_skies:cooked_carabeef", "blue_skies:cooked_monitor_tail", "farmersdelight:dog_food", "farmersdelight:beef_patty", "farmersdelight:fried_egg", "autumnity:cooked_turkey", "autumnity:cooked_turkey_piece", "theabyss_cooked_fish", "theabyss:cooked_deer_beef", "neapolitan:cooked_mint_chops");
        }

        @SuppressWarnings("UnusedReturnValue")
        private Builder<Item> tag(ITag.INamedTag<Item> tag, String... locations) {
            return Arrays.stream(locations).map(ResourceLocation::new).reduce(this.tag(tag), Builder::addOptional, (a, b) -> a);
        }
    }
}
