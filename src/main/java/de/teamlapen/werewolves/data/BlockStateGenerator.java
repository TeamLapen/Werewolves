package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.HashSet;
import java.util.Set;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, REFERENCE.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> blocks = new HashSet<>() {{
            add(ModBlocks.silver_ore.get());
            add(ModBlocks.deepslate_silver_ore.get());
            add(ModBlocks.silver_block.get());
            add(ModBlocks.raw_silver_block.get());
            add(ModBlocks.magic_planks.get());
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(ModBlocks.wolfsbane.get(), models().cross("wolfsbane", modLoc("block/wolfsbane")));
        simpleBlock(ModBlocks.potted_wolfsbane.get(), models().withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")));
        simpleBlock(ModBlocks.jacaranda_sapling.get(), models().cross("jacaranda_sapling", modLoc("block/jacaranda_sapling")));
        simpleBlock(ModBlocks.jacaranda_leaves.get());
        logBlock(ModBlocks.jacaranda_log.get());
        simpleBlock(ModBlocks.magic_sapling.get(), models().cross("magic_sapling", modLoc("block/magic_sapling")));
        simpleBlock(ModBlocks.magic_leaves.get());
        logBlock(ModBlocks.magic_log.get());
        simpleBlock(ModBlocks.totem_top_werewolves_werewolf.get(), models().getBuilder("totem_top_werewolves_werewolf").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top"))));
        simpleBlock(ModBlocks.totem_top_werewolves_werewolf_crafted.get(), models().getBuilder("totem_top_werewolves_werewolf_crafted").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top_crafted"))));

        getMultipartBuilder(ModBlocks.stone_altar.get())
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.NORTH).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).rotationY(90).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.EAST).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).rotationY(180).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.SOUTH).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).rotationY(270).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.WEST).end();
        getMultipartBuilder(ModBlocks.stone_altar_fire_bowl.get())
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl"))).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.NORTH).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl"))).rotationY(90).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.EAST).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl"))).rotationY(180).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.SOUTH).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl"))).rotationY(270).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.WEST).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire"))).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.NORTH).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, false).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire"))).rotationY(90).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.EAST).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, false).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire"))).rotationY(180).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.SOUTH).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, false).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire"))).rotationY(270).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.WEST).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, false).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire_soul"))).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.NORTH).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, true).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire_soul"))).rotationY(90).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.EAST).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, true).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire_soul"))).rotationY(180).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.SOUTH).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, true).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar_fire_bowl_fire_soul"))).rotationY(270).addModel().condition(StoneAltarFireBowlBlock.FACING, Direction.WEST).condition(StoneAltarFireBowlBlock.LIT, true).condition(StoneAltarFireBowlBlock.SOUL_FIRE, true).end();
    }

    private String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }

    private String vampirismId(String path) {
        return REFERENCE.VMODID + ":" + path;
    }
}
