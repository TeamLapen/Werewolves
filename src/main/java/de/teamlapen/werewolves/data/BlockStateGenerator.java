package de.teamlapen.werewolves.data;

import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfBerryBushBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(@NotNull PackOutput packOutput, @NotNull ExistingFileHelper exFileHelper) {
        super(packOutput, REFERENCE.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ResourceLocation cutout = new ResourceLocation("cutout");

        Set<Block> blocks = new HashSet<>() {{
            add(ModBlocks.SILVER_ORE.get());
            add(ModBlocks.DEEPSLATE_SILVER_ORE.get());
            add(ModBlocks.SILVER_BLOCK.get());
            add(ModBlocks.RAW_SILVER_BLOCK.get());
            add(ModBlocks.MAGIC_PLANKS.get());
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(ModBlocks.WOLFSBANE.get(), models().cross("wolfsbane", modLoc("block/wolfsbane")).renderType(cutout));
        simpleBlock(ModBlocks.POTTED_WOLFSBANE.get(), models().withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")).renderType(cutout));
        simpleBlock(ModBlocks.JACARANDA_SAPLING.get(), models().cross("jacaranda_sapling", modLoc("block/jacaranda_sapling")).renderType(cutout));
        simpleBlock(ModBlocks.JACARANDA_LEAVES.get(), models().withExistingParent(modId("block/jacaranda_leaves"), "minecraft:block/leaves").texture("all", modId("block/jacaranda_leaves")).renderType(cutout));
        logBlock(ModBlocks.JACARANDA_LOG.get());
        simpleBlock(ModBlocks.MAGIC_SAPLING.get(), models().cross("magic_sapling", modLoc("block/magic_sapling")).renderType(cutout));
        simpleBlock(ModBlocks.MAGIC_LEAVES.get(), models().withExistingParent(modId("block/magic_leaves"), "minecraft:block/leaves").texture("all", modId("block/magic_leaves")).renderType(cutout));
        logBlock(ModBlocks.MAGIC_LOG.get());
        simpleBlock(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), models().getBuilder("totem_top_werewolves_werewolf").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top"))).renderType(cutout));
        simpleBlock(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), models().getBuilder("totem_top_werewolves_werewolf_crafted").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top_crafted"))).renderType(cutout));

        getMultipartBuilder(ModBlocks.STONE_ALTAR.get())
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.NORTH).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).rotationY(90).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.EAST).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).rotationY(180).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.SOUTH).end()
                .part().modelFile(models().getExistingFile(modLoc("block/stone_altar"))).rotationY(270).addModel().condition(StoneAltarBlock.HORIZONTAL_FACING, Direction.WEST).end();
        getMultipartBuilder(ModBlocks.STONE_ALTAR_FIRE_BOWL.get())
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

        simpleBlock(ModBlocks.DAFFODIL.get(), models().cross("daffodil", modLoc("block/daffodil")).renderType(cutout));
        simpleBlock(ModBlocks.POTTED_DAFFODIL.get(), models().withExistingParent(modId("block/potted_daffodil"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/daffodil")).renderType(cutout));

        getVariantBuilder(ModBlocks.WOLF_BERRY_BUSH.get())
                .partialState().with(WolfBerryBushBlock.AGE, 0).modelForState().modelFile(models().cross("wolf_berry_bush_stage0", modLoc("block/wolf_berry_bush_stage0")).renderType(cutout)).addModel()
                .partialState().with(WolfBerryBushBlock.AGE, 1).modelForState().modelFile(models().cross("wolf_berry_bush_stage1", modLoc("block/wolf_berry_bush_stage1")).renderType(cutout)).addModel()
                .partialState().with(WolfBerryBushBlock.AGE, 2).modelForState().modelFile(models().cross("wolf_berry_bush_stage2", modLoc("block/wolf_berry_bush_stage2")).renderType(cutout)).addModel()
                .partialState().with(WolfBerryBushBlock.AGE, 3).modelForState().modelFile(models().cross("wolf_berry_bush_stage3", modLoc("block/wolf_berry_bush_stage3")).renderType(cutout)).addModel();
    }

    private String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }

    private String vampirismId(String path) {
        return REFERENCE.VMODID + ":" + path;
    }
}
