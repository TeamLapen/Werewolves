package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.HashSet;
import java.util.Set;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, REFERENCE.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> blocks = new HashSet<Block>() {{
            add(ModBlocks.silver_ore);
            add(ModBlocks.silver_block);
            add(ModBlocks.magic_planks);
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(ModBlocks.wolfsbane, models().cross("wolfsbane", modLoc("block/wolfsbane")));
        simpleBlock(ModBlocks.potted_wolfsbane, models().withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")));
        simpleBlock(ModBlocks.jacaranda_sapling, models().cross("jacaranda_sapling", modLoc("block/jacaranda_sapling")));
        simpleBlock(ModBlocks.jacaranda_leaves);
        logBlock(ModBlocks.jacaranda_log);
        simpleBlock(ModBlocks.magic_sapling, models().cross("magic_sapling", modLoc("block/magic_sapling")));
        simpleBlock(ModBlocks.magic_leaves);
        logBlock(ModBlocks.magic_log);
        getMultipartBuilder(ModBlocks.stone_altar).part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).addModel().end().part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire"))).addModel().condition(StoneAltarBlock.LIT, true).end();
        simpleBlock(ModBlocks.totem_top_werewolves_werewolf, models().getBuilder("totem_top_werewolves_werewolf").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top"))));
        simpleBlock(ModBlocks.totem_top_werewolves_werewolf_crafted, models().getBuilder("totem_top_werewolves_werewolf_crafted").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top"))));
        getMultipartBuilder(ModBlocks.stone_altar_fire_bowl).part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl"))).addModel().end().part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire"))).addModel().condition(StoneAltarFireBowlBlock.LIT, true).end();
    }

    private String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }

    private String vampirismId(String path) {
        return de.teamlapen.vampirism.util.REFERENCE.MODID + ":" + path;
    }
}
