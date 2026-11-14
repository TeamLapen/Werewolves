package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.common.world.blocks.TotemTopBlock;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.blocks.*;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.common.world.features.WTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(REFERENCE.MODID);

    public static final DeferredBlock<DropExperienceBlock> SILVER_ORE = BLOCKS.registerBlock("silver_ore", (props) -> new DropExperienceBlock(ConstantInt.of(0), props.mapColor(MapColor.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SILVER_ORE = BLOCKS.registerBlock("deepslate_silver_ore", (props) -> new DropExperienceBlock(ConstantInt.of(0), props.mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final DeferredBlock<WolfsbaneBlock> WOLFSBANE = BLOCKS.registerBlock("wolfsbane", WolfsbaneBlock::new);
    public static final DeferredBlock<Block> SILVER_BLOCK = BLOCKS.registerBlock("silver_block", (props) -> new Block(props.mapColor(MapColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK = BLOCKS.registerBlock("raw_silver_block", (props) -> new Block(props.mapColor(MapColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0f, 6.0f)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WOLFSBANE = BLOCKS.registerBlock("potted_wolfsbane", (props) ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WOLFSBANE, props);
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(WOLFSBANE.getId(), () -> pot);
        return pot;
    }, () -> BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT));
    public static final DeferredBlock<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF = BLOCKS.registerBlock("totem_top_werewolves_werewolf", (props) -> new TotemTopBlock(props, false, ModFactions.WEREWOLF), () -> TotemTopBlock.properties(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED = BLOCKS.registerBlock("totem_top_werewolves_werewolf_crafted", (props) -> new TotemTopBlock(props, true, ModFactions.WEREWOLF), () -> TotemTopBlock.properties(BlockBehaviour.Properties.of()));
    public static final DeferredBlock<StoneAltarBlock> STONE_ALTAR = BLOCKS.registerBlock("stone_altar", StoneAltarBlock::new);
    public static final DeferredBlock<StoneAltarFireBowlBlock> STONE_ALTAR_FIRE_BOWL = BLOCKS.registerBlock("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);
    public static final DeferredBlock<Block> DAFFODIL = BLOCKS.registerBlock("daffodil", DaffodilBlock::new);
    public static final DeferredBlock<Block> POTTED_DAFFODIL = BLOCKS.registerBlock("potted_daffodil", (props) ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DAFFODIL, props.pushReaction(PushReaction.DESTROY).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(DAFFODIL.getId(), () -> pot);
        return pot;
    });
    public static final DeferredBlock<Block> WOLF_BERRY_BUSH = BLOCKS.registerBlock("wolf_berry_bush", (props) -> new WolfBerryBushBlock(props.isViewBlocking(ModBlocks::noView).pushReaction(PushReaction.DESTROY).randomTicks().noCollision().sound(SoundType.SWEET_BERRY_BUSH)));

    public static final DeferredBlock<LeavesBlock> JACARANDA_LEAVES = BLOCKS.registerBlock("jacaranda_leaves", (props) -> new UntintedParticleLeavesBlock(0.1f, ModParticles.JACARANDA_LEAVES.get(), props), () -> Block.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES).mapColor(MapColor.COLOR_PINK));
    public static final DeferredBlock<LeavesBlock> MAGIC_LEAVES = BLOCKS.registerBlock("magic_leaves", (props) -> new UntintedParticleLeavesBlock(0.1f, ModParticles.MAGIC_LEAVES.get(), props), () -> Block.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<Block> JACARANDA_PLANKS = BLOCKS.registerBlock("jacaranda_planks", Block::new, (props) -> props.mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<Block> MAGIC_PLANKS = BLOCKS.registerBlock("magic_planks", Block::new, (pro) -> pro.mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD));
    public static final DeferredBlock<LogBlock> STRIPPED_JACARANDA_LOG = BLOCKS.registerBlock("stripped_jacaranda_log", (properties) -> new LogBlock(properties, MapColor.COLOR_BLACK, MapColor.COLOR_PINK));
    public static final DeferredBlock<LogBlock> STRIPPED_MAGIC_LOG = BLOCKS.registerBlock("stripped_magic_log", (props) -> new LogBlock(props, MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<LogBlock> JACARANDA_LOG = BLOCKS.registerBlock("jacaranda_log", (props) -> new StrippableLogBlock(props, MapColor.COLOR_PINK, MapColor.COLOR_PINK, STRIPPED_JACARANDA_LOG));
    public static final DeferredBlock<LogBlock> MAGIC_LOG = BLOCKS.registerBlock("magic_log", (props) -> new StrippableLogBlock(props, MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE, STRIPPED_MAGIC_LOG));
    public static final DeferredBlock<SaplingBlock> JACARANDA_SAPLING = BLOCKS.registerBlock("jacaranda_sapling", (props) -> new SaplingBlock(WTreeGrower.JACARANDA, props.isViewBlocking(ModBlocks::noView).ignitedByLava().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_PINK).noCollision().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<SaplingBlock> MAGIC_SAPLING = BLOCKS.registerBlock("magic_sapling", (props) -> new SaplingBlock(WTreeGrower.MAGIC, props.isViewBlocking(ModBlocks::noView).ignitedByLava().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_BLUE).noCollision().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final DeferredBlock<DoorBlock> JACARANDA_DOOR = BLOCKS.registerBlock("jacaranda_door", (props) -> new DoorBlock(LogBlock.JACARANDA_BLOCK_TYPE, props.mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<DoorBlock> MAGIC_DOOR = BLOCKS.registerBlock("magic_door", (props) -> new DoorBlock(LogBlock.MAGIC_BLOCK_TYPE, props.ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final DeferredBlock<TrapDoorBlock> JACARANDA_TRAPDOOR = BLOCKS.registerBlock("jacaranda_trapdoor", (props) -> new TrapDoorBlock(WBlockSetTypes.JACARANDA, props.ignitedByLava().mapColor(MapColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(Blocks::never)));
    public static final DeferredBlock<TrapDoorBlock> MAGIC_TRAPDOOR = BLOCKS.registerBlock("magic_trapdoor", (props) -> new TrapDoorBlock(WBlockSetTypes.MAGIC, props.ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(Blocks::never)));
    public static final DeferredBlock<StairBlock> JACARANDA_STAIRS = BLOCKS.registerBlock("jacaranda_stairs", (props) -> new StairBlock(JACARANDA_PLANKS.get().defaultBlockState(), props), () ->BlockBehaviour.Properties.ofFullCopy(JACARANDA_PLANKS.get()) );
    public static final DeferredBlock<StairBlock> MAGIC_STAIRS = BLOCKS.registerBlock("magic_stairs", (props) -> new StairBlock(MAGIC_PLANKS.get().defaultBlockState(), props), () -> BlockBehaviour.Properties.ofFullCopy(MAGIC_PLANKS.get()));
    public static final DeferredBlock<LogBlock> STRIPPED_JACARANDA_WOOD = BLOCKS.registerBlock("stripped_jacaranda_wood", (props) -> new LogBlock(props, MapColor.COLOR_PINK, MapColor.COLOR_PINK));
    public static final DeferredBlock<LogBlock> STRIPPED_MAGIC_WOOD = BLOCKS.registerBlock("stripped_magic_wood", (props) -> new LogBlock(props, MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final DeferredBlock<LogBlock> JACARANDA_WOOD = BLOCKS.registerBlock("jacaranda_wood", (props) -> new StrippableLogBlock(props, MapColor.COLOR_PINK, MapColor.COLOR_PINK, STRIPPED_JACARANDA_WOOD));
    public static final DeferredBlock<LogBlock> MAGIC_WOOD = BLOCKS.registerBlock("magic_wood", (props) -> new StrippableLogBlock(props, MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE, STRIPPED_MAGIC_WOOD));
    public static final DeferredBlock<StandingSignBlock> JACARANDA_SIGN = BLOCKS.registerBlock("jacaranda_sign", (props) -> new StandingSignBlock(LogBlock.JACARANDA, props.ignitedByLava().mapColor(MapColor.COLOR_PINK).noCollision().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<StandingSignBlock> MAGIC_SIGN = BLOCKS.registerBlock("magic_sign", (props) -> new StandingSignBlock(LogBlock.MAGIC, props.mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().noCollision().strength(1.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<WallSignBlock> JACARANDA_WALL_SIGN = BLOCKS.registerBlock("jacaranda_wall_sign", (props) -> new WallSignBlock(LogBlock.JACARANDA, props.mapColor(MapColor.COLOR_PINK).ignitedByLava().noCollision().strength(1.0F).sound(SoundType.WOOD).overrideLootTable(JACARANDA_SIGN.value().getLootTable())));
    public static final DeferredBlock<WallSignBlock> MAGIC_WALL_SIGN = BLOCKS.registerBlock("magic_wall_sign", (props) -> new WallSignBlock(LogBlock.MAGIC, props.mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().noCollision().strength(1.0F).sound(SoundType.WOOD).overrideLootTable(MAGIC_SIGN.value().getLootTable())));
    public static final DeferredBlock<PressurePlateBlock> JACARANDA_PRESSURE_PLATE = BLOCKS.registerBlock("jacaranda_pressure_plate", (props) -> new PressurePlateBlock(LogBlock.JACARANDA_BLOCK_TYPE, props.ignitedByLava().mapColor(MapColor.COLOR_PINK).noCollision().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<PressurePlateBlock> MAGIC_PRESSURE_PLATE = BLOCKS.registerBlock("magic_pressure_plate", (props) -> new PressurePlateBlock(LogBlock.MAGIC_BLOCK_TYPE, props.ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollision().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<ButtonBlock> JACARANDA_BUTTON = BLOCKS.registerBlock("jacaranda_button", (props) -> new ButtonBlock(LogBlock.JACARANDA_BLOCK_TYPE, 30, props.pushReaction(PushReaction.DESTROY).isViewBlocking(ModBlocks::noView).noCollision().strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<ButtonBlock> MAGIC_BUTTON = BLOCKS.registerBlock("magic_button", (props) -> new ButtonBlock(LogBlock.MAGIC_BLOCK_TYPE, 30, props.pushReaction(PushReaction.DESTROY).isViewBlocking(ModBlocks::noView).noCollision().noOcclusion().isValidSpawn(ModBlocks::noSpawn).strength(0.5F).sound(SoundType.WOOD)));
    public static final DeferredBlock<SlabBlock> JACARANDA_SLAB = BLOCKS.registerBlock("jacaranda_slab", (props) -> new SlabBlock(props.mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<SlabBlock> MAGIC_SLAB = BLOCKS.registerBlock("magic_slab", (props) -> new SlabBlock(props.mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<FenceGateBlock> JACARANDA_FENCE_GATE = BLOCKS.registerBlock("jacaranda_fence_gate", (props) -> new FenceGateBlock(props.mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final DeferredBlock<FenceGateBlock> MAGIC_FENCE_GATE = BLOCKS.registerBlock("magic_fence_gate", (props) -> new FenceGateBlock(props.mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final DeferredBlock<FenceBlock> JACARANDA_FENCE = BLOCKS.registerBlock("jacaranda_fence", (props) -> new FenceBlock(props.mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<FenceBlock> MAGIC_FENCE = BLOCKS.registerBlock("magic_fence", (props) -> new FenceBlock(props.mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<CeilingHangingSignBlock> JACARANDA_HANGING_SIGN = BLOCKS.registerBlock("jacaranda_hanging_sign", props -> new CeilingHangingSignBlock(LogBlock.JACARANDA, props), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_HANGING_SIGN).mapColor(JACARANDA_PLANKS.get().defaultMapColor()));
    public static final DeferredBlock<WallHangingSignBlock> JACARANDA_WALL_HANGING_SIGN = BLOCKS.registerBlock("jacaranda_wall_hanging_sign", props -> new WallHangingSignBlock(LogBlock.JACARANDA, props), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_HANGING_SIGN).mapColor(JACARANDA_PLANKS.get().defaultMapColor()).overrideLootTable(JACARANDA_HANGING_SIGN.get().getLootTable()));
    public static final DeferredBlock<CeilingHangingSignBlock> MAGIC_HANGING_SIGN = BLOCKS.registerBlock("magic_hanging_sign", props -> new CeilingHangingSignBlock(LogBlock.MAGIC, props), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_HANGING_SIGN).mapColor(MAGIC_PLANKS.get().defaultMapColor()));
    public static final DeferredBlock<WallHangingSignBlock> MAGIC_WALL_HANGING_SIGN = BLOCKS.registerBlock("magic_wall_hanging_sign", props -> new WallHangingSignBlock(LogBlock.MAGIC, props), () -> BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_HANGING_SIGN).mapColor(MAGIC_PLANKS.get().defaultMapColor()).overrideLootTable(MAGIC_HANGING_SIGN.get().getLootTable()));

    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER = BLOCKS.registerBlock("wolfsbane_diffuser_normal", (props) -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.NORMAL, props));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_LONG = BLOCKS.registerBlock("wolfsbane_diffuser_long", (props) -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.LONG, props));
    public static final DeferredBlock<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_IMPROVED = BLOCKS.registerBlock("wolfsbane_diffuser_improved", (props) -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.IMPROVED, props));


    public static class V {
        public static final DeferredHolder<Block, Block> MED_CHAIR = DeferredBlock.create(ResourceKey.create(Registries.BLOCK, WIdentifier.loc("vampirism", "med_chair")));

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static Set<Block> getAllBlocks() {
        return BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }

        private static boolean noView(BlockState var1, BlockGetter var2, BlockPos var3) {
            return false;
        }

    private static boolean noSpawn(BlockState var1, BlockGetter var2, BlockPos var3, EntityType<?> var4) {
        return false;
    }
}
