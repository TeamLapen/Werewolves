package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.core.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModTagsProvider {

    public static void addProvider(@NotNull GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        BlockTagsProvider blocks = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(event.includeServer(), blocks);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(generator, blocks, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModPoiTypesProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModVillageProfessionProvider(generator, existingFileHelper));
    }

    private static class ModBlockTagsProvider extends BlockTagsProvider {
        public ModBlockTagsProvider(@NotNull DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
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
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.JACARANDA_PRESSURE_PLACE.get(), ModBlocks.MAGIC_PRESSURE_PLACE.get());
            tag(BlockTags.WOODEN_DOORS).add(ModBlocks.JACARANDA_DOOR.get(), ModBlocks.MAGIC_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.JACARANDA_TRAPDOOR.get(), ModBlocks.MAGIC_TRAPDOOR.get());
        }
    }

    private static class ModItemTagsProvider extends ItemTagsProvider {
        public ModItemTagsProvider(@NotNull DataGenerator generatorIn, @NotNull BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagsProvider, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.copy(ModTags.Blocks.SILVER_ORE, ModTags.Items.SILVER_ORE);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_RAW_SILVER, ModTags.Items.STORAGE_BLOCKS_RAW_SILVER);
            this.copy(ModTags.Blocks.STORAGE_BLOCKS_SILVER, ModTags.Items.STORAGE_BLOCKS_SILVER);
            this.copy(ModTags.Blocks.MAGIC_LOG, ModTags.Items.MAGIC_LOG);
            this.copy(ModTags.Blocks.JACARANDA_LOG, ModTags.Items.JACARANDA_LOG);
            this.copy(Tags.Blocks.ORE_RATES_SINGULAR, Tags.Items.ORE_RATES_SINGULAR);
            this.tag(ModTags.Items.SILVER_INGOT).add(ModItems.SILVER_INGOT.get());
            this.tag(ModTags.Items.SILVER_NUGGET).add(ModItems.SILVER_NUGGET.get());
            this.tag(ModTags.Items.RAWMEATS).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, ModItems.LIVER.get(), ModItems.V.HUMAN_HEART.get(), ModItems.V.WEAK_HUMAN_HEART.get(), Items.SALMON, Items.TROPICAL_FISH, Items.COD);
            this.tag(ModTags.Items.COOKEDMEATS).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_PORKCHOP, Items.COOKED_COD, Items.COOKED_SALMON);
            this.tag(ModTags.Items.SILVER_TOOL).add(ModItems.SILVER_AXE.get(), ModItems.SILVER_HOE.get(), ModItems.SILVER_SWORD.get(), ModItems.SILVER_PICKAXE.get(), ModItems.SILVER_SHOVEL.get());
            this.tag(ModTags.Items.RAW_MATERIALS_SILVER).add(ModItems.RAW_SILVER.get());
            this.tag(ItemTags.SMALL_FLOWERS).add(ModBlocks.WOLFSBANE.get().asItem(), ModBlocks.DAFFODIL.get().asItem());

            copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
            copy(BlockTags.LOGS, ItemTags.LOGS);
            copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
            copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
            copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
            copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
            copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
            copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
            copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
            copy(BlockTags.PLANKS, ItemTags.PLANKS);
            copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
            copy(BlockTags.LEAVES, ItemTags.LEAVES);

            tag(ItemTags.BOATS).add(ModItems.JACARANDA_BOAT.get(), ModItems.MAGIC_BOAT.get());
            tag(ItemTags.CHEST_BOATS).add(ModItems.JACARANDA_CHEST_BOAT.get(), ModItems.MAGIC_CHEST_BOAT.get());
        }
    }

    private static class ModBiomeTagsProvider extends BiomeTagsProvider {

        public ModBiomeTagsProvider(@NotNull DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Biomes.IS_WEREWOLF_BIOME).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(ModTags.Biomes.HasSpawn.WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModTags.Biomes.NoSpawn.WEREWOLF).addTag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME);
            this.tag(ModTags.Biomes.HasSpawn.HUMAN_WEREWOLF).addTag(BiomeTags.IS_TAIGA);
            this.tag(ModTags.Biomes.NoSpawn.HUMAN_WEREWOLF).addTag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME);
            this.tag(ModTags.Biomes.HasGen.SILVER_ORE).addTag(BiomeTags.IS_OVERWORLD);
            this.tag(ModTags.Biomes.HasGen.WOLFSBANE).addTag(BiomeTags.IS_FOREST);
            this.tag(de.teamlapen.vampirism.core.ModTags.Biomes.IS_FACTION_BIOME).addTag(ModTags.Biomes.IS_WEREWOLF_BIOME);
            this.tag(BiomeTags.IS_OVERWORLD).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(BiomeTags.IS_FOREST).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(Tags.Biomes.IS_DENSE).add(ModBiomes.WEREWOLF_HEAVEN.get());
            this.tag(Tags.Biomes.IS_MAGICAL).add(ModBiomes.WEREWOLF_HEAVEN.get());
        }

        @NotNull
        @Override
        public String getName() {
            return "Werewolves " + super.getName();
        }
    }

    private static class ModPoiTypesProvider extends PoiTypeTagsProvider {

        public ModPoiTypesProvider(@NotNull DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
            super(generatorIn, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.PoiTypes.IS_WEREWOLF).add(ModVillage.WEREWOLF_FACTION.get());
            this.tag(de.teamlapen.vampirism.core.ModTags.PoiTypes.HAS_FACTION).addTag(ModTags.PoiTypes.IS_WEREWOLF);
        }
    }

    private static class ModVillageProfessionProvider extends ForgeRegistryTagsProvider<VillagerProfession> {
        public ModVillageProfessionProvider(@NotNull DataGenerator p_236434_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_236434_, ForgeRegistries.VILLAGER_PROFESSIONS, REFERENCE.MODID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(ModTags.Professions.IS_WEREWOLF).add(ModVillage.WEREWOLF_EXPERT.get());
            this.tag(de.teamlapen.vampirism.core.ModTags.Professions.HAS_FACTION).addTag(ModTags.Professions.IS_WEREWOLF);
        }
    }
}
