package de.teamlapen.werewolves.data.provider.generator;

import de.teamlapen.faction.data.provider.model.FactionsModelTemplates;
import de.teamlapen.faction.data.provider.model.FactionsTextureSlots;
import de.teamlapen.vampirism.data.provider.models.ModModelTemplates;
import de.teamlapen.vampirism.data.provider.models.ModTextureSlots;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.common.world.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.data.ModBlockFamilies;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.multipart.Condition;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static de.teamlapen.werewolves.api.WIdentifier.mod;


public class ModBlockModelGenerators extends BlockModelGenerators {

    public ModBlockModelGenerators(net.minecraft.client.data.models.BlockModelGenerators generators) {
        super(generators.blockStateOutput, generators.itemModelOutput, generators.modelOutput);
    }

    @Override
    public void run() {
        createFamilies(ModBlockFamilies.ALL.values());

        createDiffuser();
        createWood();
        createPlants();
        createTotem();
        createTrivialBlocks();
        createStoneAltar();
    }


    protected void createDiffuser() {
        Identifier defaultModel = mod("block/garlic_diffuser");
        this.blockStateOutput.accept(createSimpleBlock(ModBlocks.WOLFSBANE_DIFFUSER.get(), plainVariant(defaultModel)));
        createDefaultBlockItem(ModBlocks.WOLFSBANE_DIFFUSER.get(), defaultModel);

        Identifier strongModel = ModModelTemplates.GARLIC_DIFFUSER.create(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.mod("block/wolfsbane_diffuser_improved_inside"))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), plainVariant(strongModel)));
        createDefaultBlockItem(ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get(), strongModel);

        Identifier longModel = ModModelTemplates.GARLIC_DIFFUSER.create(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get(), new TextureMapping().put(ModTextureSlots.CORE, new Material(WIdentifier.mod("block/garlic_diffuser_core_long"))), this.modelOutput);
        this.blockStateOutput.accept(createSimpleBlock(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get(), plainVariant(longModel)));
        createDefaultBlockItem(ModBlocks.WOLFSBANE_DIFFUSER_LONG.get(), longModel);
    }

    protected void createWood() {
        this.woodProvider(ModBlocks.JACARANDA_LOG.get()).logWithHorizontal(ModBlocks.JACARANDA_LOG.get()).wood(ModBlocks.JACARANDA_WOOD.get());
        this.woodProvider(ModBlocks.STRIPPED_JACARANDA_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_JACARANDA_LOG.get()).wood(ModBlocks.STRIPPED_JACARANDA_WOOD.get());
        this.woodProvider(ModBlocks.MAGIC_LOG.get()).logWithHorizontal(ModBlocks.MAGIC_LOG.get()).wood(ModBlocks.MAGIC_WOOD.get());
        this.woodProvider(ModBlocks.STRIPPED_MAGIC_LOG.get()).logWithHorizontal(ModBlocks.STRIPPED_MAGIC_LOG.get()).wood(ModBlocks.STRIPPED_MAGIC_WOOD.get());

        this.createHangingSign(ModBlocks.JACARANDA_LOG.get(), ModBlocks.JACARANDA_HANGING_SIGN.get(), ModBlocks.JACARANDA_WALL_HANGING_SIGN.get());
        this.createHangingSign(ModBlocks.MAGIC_LOG.get(), ModBlocks.MAGIC_HANGING_SIGN.get(), ModBlocks.MAGIC_WALL_HANGING_SIGN.get());
    }

    protected void createPlants() {
        createPlantWithDefaultItem(ModBlocks.WOLFSBANE.get(), ModBlocks.POTTED_WOLFSBANE.get(), PlantType.NOT_TINTED);
        createPlantWithDefaultItem(ModBlocks.DAFFODIL.get(), ModBlocks.POTTED_DAFFODIL.get(), PlantType.NOT_TINTED);

        createCropBlock(ModBlocks.WOLF_BERRY_BUSH.get(), BlockStateProperties.AGE_3, 0,1,2,3);
    }

    protected void createTrivialBlocks() {
        Stream.of(
                ModBlocks.SILVER_ORE,
                ModBlocks.DEEPSLATE_SILVER_ORE,
                ModBlocks.SILVER_BLOCK,
                ModBlocks.RAW_SILVER_BLOCK
        ).map(DeferredHolder::get).forEach(this::createTrivialBlockWithItem);
    }

    protected void createTotem() {
        this.blockStateOutput.accept(createSimpleBlock(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), plainVariant(FactionsModelTemplates.TOTEM_TOP.create(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF.get(), new TextureMapping().putForced(FactionsTextureSlots.CORE, new Material(WIdentifier.mod("block/totem_top_core_werewolf"))), this.modelOutput))));
        this.blockStateOutput.accept(createSimpleBlock(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), plainVariant(FactionsModelTemplates.TOTEM_TOP_CRAFTED.create(ModBlocks.TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED.get(), new TextureMapping().putForced(FactionsTextureSlots.CORE, new Material(WIdentifier.mod("block/totem_top_core_werewolf"))), this.modelOutput))));
    }

    protected void createStoneAltar() {
        this.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.STONE_ALTAR.get(), plainVariant(WIdentifier.mod("block/stone_altar"))).with(ROTATION_HORIZONTAL_FACING));

        MultiPartGenerator stoneAltarFireBowl = MultiPartGenerator.multiPart(ModBlocks.STONE_ALTAR_FIRE_BOWL.get());

        MultiVariant body = plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.STONE_ALTAR_FIRE_BOWL.get()));
        MultiVariant fire = plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), "_fire"));
        MultiVariant soulFire = plainVariant(ModelLocationUtils.getModelLocation(ModBlocks.STONE_ALTAR_FIRE_BOWL.get(), "_fire_soul"));
        forEachHorizontalDirection((direction, rotation) -> {
            Condition facingCondition = condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).build();
            stoneAltarFireBowl.with(facingCondition, body.with(rotation));
        });
        forEachHorizontalDirection((direction, rotation) -> {
            Condition facingCondition = condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(StoneAltarFireBowlBlock.LIT, true).term(StoneAltarFireBowlBlock.SOUL_FIRE, false).build();
            stoneAltarFireBowl.with(facingCondition, fire.with(rotation));
        });
        forEachHorizontalDirection((direction, rotation) -> {
            Condition facingCondition = condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(StoneAltarFireBowlBlock.LIT, true).term(StoneAltarFireBowlBlock.SOUL_FIRE, true).build();
            stoneAltarFireBowl.with(facingCondition, soulFire.with(rotation));
        });
    }


    //<editor-fold description="helper">

    protected void createFamilies(Collection<BlockFamily> families) {
        for (BlockFamily family : families) {
            if (family.shouldGenerateModel()) {
                this.family(family.getBaseBlock()).generateFor(family);
                createDefaultBlockItem(family.getBaseBlock());
                Stream.of(BlockFamily.Variant.PRESSURE_PLATE, BlockFamily.Variant.FENCE_GATE, BlockFamily.Variant.CRACKED, BlockFamily.Variant.CHISELED).forEach(variant -> {
                    Block block = family.get(variant);
                    //noinspection ConstantValue
                    if (block != null) {
                        createDefaultBlockItem(block, ModelLocationUtils.getModelLocation(block));
                    }
                });
            }
        }
    }

    protected void createDefaultBlockItem(Block block) {
        createDefaultBlockItem(block, ModelLocationUtils.getModelLocation(block));
    }

    private final Set<Item> createdItemModels = new HashSet<>();


    protected void createDefaultBlockItem(Block block, Identifier model) {
        var item = block.asItem();
        if (item != Items.AIR && !createdItemModels.contains(item)) {
            this.createdItemModels.add(item);
            this.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(model));
        }
    }

    protected void createTrivialBlockWithItem(Block block) {
        createTrivialCube(block);
        createDefaultBlockItem(block);
    }
    //</editor-fold>
}
