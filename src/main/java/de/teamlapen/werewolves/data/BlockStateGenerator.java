package de.teamlapen.werewolves.data;

import de.teamlapen.vampirism.util.RegUtil;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfBerryBushBlock;
import de.teamlapen.werewolves.core.ModBlocks;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BlockStateGenerator extends BlockStateProvider {

    private static final ResourceLocation CUTOUT = new ResourceLocation("cutout");

    public BlockStateGenerator(@NotNull PackOutput packOutput, @NotNull ExistingFileHelper exFileHelper) {
        super(packOutput, REFERENCE.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Block> blocks = new HashSet<>() {{
            add(ModBlocks.SILVER_ORE.get());
            add(ModBlocks.DEEPSLATE_SILVER_ORE.get());
            add(ModBlocks.SILVER_BLOCK.get());
            add(ModBlocks.RAW_SILVER_BLOCK.get());
        }};
        blocks.forEach(this::simpleBlock);

        simpleBlock(ModBlocks.WOLFSBANE.get(), models().cross("wolfsbane", modLoc("block/wolfsbane")).renderType(CUTOUT));
        simpleBlock(ModBlocks.POTTED_WOLFSBANE.get(), models().withExistingParent(modId("block/potted_wolfsbane"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/wolfsbane")).renderType(CUTOUT));
        simpleBlock(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), models().getBuilder("totem_top_werewolves_werewolf").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top"))).renderType(CUTOUT));
        simpleBlock(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), models().getBuilder("totem_top_werewolves_werewolf_crafted").parent(new ModelFile.UncheckedModelFile(vampirismId("block/totem_top_crafted"))).renderType(CUTOUT));

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

        simpleBlock(ModBlocks.DAFFODIL.get(), models().cross("daffodil", modLoc("block/daffodil")).renderType(CUTOUT));
        simpleBlock(ModBlocks.POTTED_DAFFODIL.get(), models().withExistingParent(modId("block/potted_daffodil"), "minecraft:block/flower_pot_cross").texture("plant", modId("block/daffodil")).renderType(CUTOUT));

        getVariantBuilder(ModBlocks.WOLF_BERRY_BUSH.get())
                .partialState().with(WolfBerryBushBlock.AGE, 0).modelForState().modelFile(models().cross("wolf_berry_bush_stage0", modLoc("block/wolf_berry_bush_stage0")).renderType(CUTOUT)).addModel()
                .partialState().with(WolfBerryBushBlock.AGE, 1).modelForState().modelFile(models().cross("wolf_berry_bush_stage1", modLoc("block/wolf_berry_bush_stage1")).renderType(CUTOUT)).addModel()
                .partialState().with(WolfBerryBushBlock.AGE, 2).modelForState().modelFile(models().cross("wolf_berry_bush_stage2", modLoc("block/wolf_berry_bush_stage2")).renderType(CUTOUT)).addModel()
                .partialState().with(WolfBerryBushBlock.AGE, 3).modelForState().modelFile(models().cross("wolf_berry_bush_stage3", modLoc("block/wolf_berry_bush_stage3")).renderType(CUTOUT)).addModel();

        createWoodStates();

        horizontalBlock(ModBlocks.WOLFSBANE_DIFFUSER.get(), withExistingParent("wolfsbane_diffuser_normal", vampirismId("block/garlic_diffuser")).texture("wood_garlic_symbol", "werewolves:block/wolfsbane_diffuser_top").texture("garlic", "werewolves:block/wolfsbane_diffuser_inside").renderType(CUTOUT));
        horizontalBlock(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), withExistingParent("wolfsbane_diffuser_improved", vampirismId("block/garlic_diffuser")).texture("wood_garlic_symbol", "werewolves:block/wolfsbane_diffuser_top").texture("garlic", "werewolves:block/wolfsbane_diffuser_improved_inside").renderType(CUTOUT));
        horizontalBlock(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get(), withExistingParent("wolfsbane_diffuser_long", vampirismId("block/garlic_diffuser")).texture("wood_garlic_symbol", "werewolves:block/wolfsbane_diffuser_top").texture("garlic", "werewolves:block/wolfsbane_diffuser_inside").renderType(CUTOUT));
    }

    public BlockModelBuilder withExistingParent(String name, String parent) {
        try {
            return models().withExistingParent(name, parent);
        } catch (IllegalStateException e) {
            return withExistingParent(name, mcLoc(parent));
        }
    }

    public BlockModelBuilder withExistingParent(String name, ResourceLocation parent) {
        return models().getBuilder(name).parent(new ModelFile.UncheckedModelFile(parent));
    }

    private void createWoodStates() {
        simpleBlock(ModBlocks.JACARANDA_PLANKS.get());
        simpleBlock(ModBlocks.MAGIC_PLANKS.get());

        simpleBlock(ModBlocks.JACARANDA_SAPLING.get(), models().cross("jacaranda_sapling", modLoc("block/jacaranda_sapling")).renderType(CUTOUT));
        simpleBlock(ModBlocks.JACARANDA_LEAVES.get(), models().withExistingParent(modId("block/jacaranda_leaves"), "minecraft:block/leaves").texture("all", modId("block/jacaranda_leaves")).renderType(CUTOUT));

        simpleBlock(ModBlocks.MAGIC_SAPLING.get(), models().cross("magic_sapling", modLoc("block/magic_sapling")).renderType(CUTOUT));
        simpleBlock(ModBlocks.MAGIC_LEAVES.get(), models().withExistingParent(modId("block/magic_leaves"), "minecraft:block/leaves").texture("all", modId("block/magic_leaves")).renderType(CUTOUT));

        stairsBlock(ModBlocks.JACARANDA_STAIRS.get(), blockTexture(ModBlocks.JACARANDA_PLANKS.get()));
        stairsBlock(ModBlocks.MAGIC_STAIRS.get(), blockTexture(ModBlocks.MAGIC_PLANKS.get()));
        slabBlock(ModBlocks.JACARANDA_SLAB.get(), blockTexture(ModBlocks.JACARANDA_PLANKS.get()), blockTexture(ModBlocks.JACARANDA_PLANKS.get()));
        slabBlock(ModBlocks.MAGIC_SLAB.get(), blockTexture(ModBlocks.MAGIC_PLANKS.get()), blockTexture(ModBlocks.MAGIC_PLANKS.get()));

        fenceBlock(ModBlocks.JACARANDA_FENCE.get(), blockTexture(ModBlocks.JACARANDA_PLANKS.get()));
        fenceBlock(ModBlocks.MAGIC_FENCE.get(), blockTexture(ModBlocks.MAGIC_PLANKS.get()));
//        models().withExistingParent("jacaranda_fence_inventory", new ResourceLocation("block/fence_inventory")).texture("texture", "block/jacaranda_planks");
//        models().withExistingParent("magic_fence_inventory", new ResourceLocation("block/fence_inventory")).texture("texture", "block/magic_planks");
        fenceGateBlock(ModBlocks.JACARANDA_FENCE_GATE.get(), blockTexture(ModBlocks.JACARANDA_PLANKS.get()));
        fenceGateBlock(ModBlocks.MAGIC_FENCE_GATE.get(), blockTexture(ModBlocks.MAGIC_PLANKS.get()));

        logBlock(ModBlocks.JACARANDA_LOG.get());
        logBlock(ModBlocks.MAGIC_LOG.get());
        axisBlock(ModBlocks.JACARANDA_WOOD.get(), blockTexture(ModBlocks.JACARANDA_LOG.get()), blockTexture(ModBlocks.JACARANDA_LOG.get()));
        axisBlock(ModBlocks.MAGIC_WOOD.get(), blockTexture(ModBlocks.MAGIC_LOG.get()), blockTexture(ModBlocks.MAGIC_LOG.get()));
        logBlock(ModBlocks.STRIPPED_JACARANDA_LOG.get());
        logBlock(ModBlocks.STRIPPED_MAGIC_LOG.get());
        axisBlock(ModBlocks.STRIPPED_JACARANDA_WOOD.get(), blockTexture(ModBlocks.STRIPPED_JACARANDA_LOG.get()), blockTexture(ModBlocks.STRIPPED_JACARANDA_LOG.get()));
        axisBlock(ModBlocks.STRIPPED_MAGIC_WOOD.get(), blockTexture(ModBlocks.STRIPPED_MAGIC_LOG.get()), blockTexture(ModBlocks.STRIPPED_MAGIC_LOG.get()));

        buttonBlock(ModBlocks.JACARANDA_BUTTON.get(), blockTexture(ModBlocks.JACARANDA_PLANKS.get()));
        buttonBlock(ModBlocks.MAGIC_BUTTON.get(), blockTexture(ModBlocks.MAGIC_PLANKS.get()));

        pressurePlate(ModBlocks.JACARANDA_PRESSURE_PLATE.get(), blockTexture(ModBlocks.JACARANDA_PLANKS.get()));
        pressurePlate(ModBlocks.MAGIC_PRESSURE_PLATE.get(), blockTexture(ModBlocks.MAGIC_PLANKS.get()));

        simpleBlock(ModBlocks.JACARANDA_WALL_SIGN.get(), models().getBuilder("werewolves:jacaranda_wall_sign").texture("particle", "werewolves:block/jacaranda_planks"));
        simpleBlock(ModBlocks.MAGIC_WALL_SIGN.get(), models().getBuilder("werewolves:magic_wall_sign").texture("particle", "werewolves:block/magic_planks"));
        simpleBlock(ModBlocks.JACARANDA_SIGN.get(), models().getBuilder("werewolves:jacaranda_sign").texture("particle", "werewolves:block/jacaranda_planks"));
        simpleBlock(ModBlocks.MAGIC_SIGN.get(), models().getBuilder("werewolves:magic_sign").texture("particle", "werewolves:block/magic_planks"));

        trapdoorBlock(ModBlocks.JACARANDA_TRAPDOOR.get(), new ResourceLocation(REFERENCE.MODID, "block/jacaranda_trapdoor"), true);
        trapdoorBlock(ModBlocks.MAGIC_TRAPDOOR.get(), new ResourceLocation(REFERENCE.MODID, "block/magic_trapdoor"), true);

        doorBlock(ModBlocks.JACARANDA_DOOR.get(), new ResourceLocation(REFERENCE.MODID, "block/jacaranda_door_bottom"), new ResourceLocation(REFERENCE.MODID, "block/jacaranda_door_top"));
        doorBlock(ModBlocks.MAGIC_DOOR.get(), new ResourceLocation(REFERENCE.MODID, "block/magic_door_bottom"), new ResourceLocation(REFERENCE.MODID, "block/magic_door_top"));

    }

    private void pressurePlate(Block block, @NotNull ResourceLocation texture) {
        ResourceLocation id = RegUtil.id(block);
        ModelFile pressure_plate = models().withExistingParent("block/" + id.getPath(), new ResourceLocation("block/pressure_plate_up")).texture("texture", texture.getPath());
        ModelFile pressure_plate_down = models().withExistingParent("block/" + id.getPath() + "_down", new ResourceLocation("block/pressure_plate_down")).texture("texture", texture.getPath());

        getVariantBuilder(block)
                .partialState().with(PressurePlateBlock.POWERED, false).modelForState().modelFile(pressure_plate).addModel()
                .partialState().with(PressurePlateBlock.POWERED, true).modelForState().modelFile(pressure_plate_down).addModel();
    }

    private @NotNull String modId(String path) {
        return REFERENCE.MODID + ":" + path;
    }

    private @NotNull String vampirismId(String path) {
        return REFERENCE.VMODID + ":" + path;
    }
}
