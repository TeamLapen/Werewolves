package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.LeavesBlock;
import de.teamlapen.werewolves.blocks.*;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.tree.JacarandaTree;
import de.teamlapen.werewolves.world.tree.MagicTree;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REFERENCE.MODID);

    public static final RegistryObject<DropExperienceBlock> SILVER_ORE = registerWithItem("silver_ore", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_SILVER_ORE = registerWithItem("deepslate_silver_ore", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.DEEPSLATE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<WolfsbaneBlock> WOLFSBANE = registerWithItem("wolfsbane", WolfsbaneBlock::new);
    public static final RegistryObject<Block> SILVER_BLOCK = registerWithItem("silver_block", () -> new Block(Block.Properties.of().mapColor(MapColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerWithItem("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0f, 6.0f)));
    public static final RegistryObject<FlowerPotBlock> POTTED_WOLFSBANE = BLOCKS.register("potted_wolfsbane", () ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WOLFSBANE, Block.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion().strength(0f));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(WOLFSBANE.getId(), () -> pot);
        return pot;
    });
    public static final RegistryObject<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF = BLOCKS.register("totem_top_werewolves_werewolf", () -> new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED = BLOCKS.register("totem_top_werewolves_werewolf_crafted", () -> new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<StoneAltarBlock> STONE_ALTAR = registerWithItem("stone_altar", StoneAltarBlock::new);
    public static final RegistryObject<StoneAltarFireBowlBlock> STONE_ALTAR_FIRE_BOWL = registerWithItem("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);
    public static final RegistryObject<Block> DAFFODIL = registerWithItem("daffodil", DaffodilBlock::new);
    public static final RegistryObject<Block> POTTED_DAFFODIL = BLOCKS.register("potted_daffodil", () ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DAFFODIL, Block.Properties.of().pushReaction(PushReaction.DESTROY).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(DAFFODIL.getId(), () -> pot);
        return pot;
    });
    public static final RegistryObject<Block> WOLF_BERRY_BUSH = BLOCKS.register("wolf_berry_bush", () -> new WolfBerryBushBlock(Block.Properties.of().isViewBlocking(UtilLib::never).pushReaction(PushReaction.DESTROY).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));

    public static final RegistryObject<LeavesBlock> JACARANDA_LEAVES = registerWithItem("jacaranda_leaves", () -> new LeavesBlock(Block.Properties.of().mapColor(MapColor.COLOR_PINK).isViewBlocking(UtilLib::never).ignitedByLava().strength(0.2F).pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<LeavesBlock> MAGIC_LEAVES = registerWithItem("magic_leaves", () -> new LeavesBlock(Block.Properties.of().isViewBlocking(UtilLib::never).mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(0.2F).pushReaction(PushReaction.DESTROY).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> JACARANDA_PLANKS = registerWithItem("jacaranda_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> MAGIC_PLANKS = registerWithItem("magic_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<LogBlock> STRIPPED_JACARANDA_LOG = registerWithItem("stripped_jacaranda_log", () -> new LogBlock(MapColor.COLOR_BLACK, MapColor.COLOR_PINK));
    public static final RegistryObject<LogBlock> STRIPPED_MAGIC_LOG = registerWithItem("stripped_magic_log", () -> new LogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final RegistryObject<LogBlock> JACARANDA_LOG = registerWithItem("jacaranda_log", () -> new StrippableLogBlock(MapColor.COLOR_PINK, MapColor.COLOR_PINK, STRIPPED_JACARANDA_LOG));
    public static final RegistryObject<LogBlock> MAGIC_LOG = registerWithItem("magic_log", () -> new StrippableLogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE, STRIPPED_MAGIC_LOG));
    public static final RegistryObject<SaplingBlock> JACARANDA_SAPLING = registerWithItem("jacaranda_sapling", () -> new SaplingBlock(new JacarandaTree(), BlockBehaviour.Properties.of().isViewBlocking(UtilLib::never).ignitedByLava().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_PINK).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<SaplingBlock> MAGIC_SAPLING = registerWithItem("magic_sapling", () -> new SaplingBlock(new MagicTree(), BlockBehaviour.Properties.of().isViewBlocking(UtilLib::never).ignitedByLava().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<DoorBlock> JACARANDA_DOOR = registerWithItem("jacaranda_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(3.0F).sound(SoundType.WOOD).noOcclusion(), LogBlock.JACARANDA_BLOCK_TYPE));
    public static final RegistryObject<DoorBlock> MAGIC_DOOR = registerWithItem("magic_door", () -> new DoorBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), LogBlock.MAGIC_BLOCK_TYPE));
    public static final RegistryObject<TrapDoorBlock> JACARANDA_TRAPDOOR = registerWithItem("jacaranda_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_PINK).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), LogBlock.JACARANDA_BLOCK_TYPE));
    public static final RegistryObject<TrapDoorBlock> MAGIC_TRAPDOOR = registerWithItem("magic_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), LogBlock.MAGIC_BLOCK_TYPE));
    public static final RegistryObject<StairBlock> JACARANDA_STAIRS = registerWithItem("jacaranda_stairs", () -> new StairBlock(() -> JACARANDA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(JACARANDA_PLANKS.get())));
    public static final RegistryObject<StairBlock> MAGIC_STAIRS = registerWithItem("magic_stairs", () -> new StairBlock(() -> MAGIC_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(MAGIC_PLANKS.get())));
    public static final RegistryObject<LogBlock> STRIPPED_JACARANDA_WOOD = registerWithItem("stripped_jacaranda_wood", () -> new LogBlock(MapColor.COLOR_PINK, MapColor.COLOR_PINK));
    public static final RegistryObject<LogBlock> STRIPPED_MAGIC_WOOD = registerWithItem("stripped_magic_wood", () -> new LogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE));
    public static final RegistryObject<LogBlock> JACARANDA_WOOD = registerWithItem("jacaranda_wood", () -> new StrippableLogBlock(MapColor.COLOR_PINK, MapColor.COLOR_PINK, STRIPPED_JACARANDA_WOOD));
    public static final RegistryObject<LogBlock> MAGIC_WOOD = registerWithItem("magic_wood", () -> new StrippableLogBlock(MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE, STRIPPED_MAGIC_WOOD));
    public static final RegistryObject<StandingSignBlock> JACARANDA_SIGN = BLOCKS.register("jacaranda_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_PINK).noCollission().strength(1.0F).sound(SoundType.WOOD), LogBlock.JACARANDA));
    public static final RegistryObject<StandingSignBlock> MAGIC_SIGN = BLOCKS.register("magic_sign", () -> new StandingSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().noCollission().strength(1.0F).sound(SoundType.WOOD), LogBlock.MAGIC));
    public static final RegistryObject<WallSignBlock> JACARANDA_WALL_SIGN = BLOCKS.register("jacaranda_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(JACARANDA_SIGN), LogBlock.JACARANDA));
    public static final RegistryObject<WallSignBlock> MAGIC_WALL_SIGN = BLOCKS.register("magic_wall_sign", () -> new WallSignBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(MAGIC_SIGN), LogBlock.MAGIC));
    public static final RegistryObject<PressurePlateBlock> JACARANDA_PRESSURE_PLATE = registerWithItem("jacaranda_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_PINK).noCollission().strength(0.5F).sound(SoundType.WOOD), LogBlock.JACARANDA_BLOCK_TYPE));
    public static final RegistryObject<PressurePlateBlock> MAGIC_PRESSURE_PLATE = registerWithItem("magic_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().strength(0.5F).sound(SoundType.WOOD), LogBlock.MAGIC_BLOCK_TYPE));
    public static final RegistryObject<ButtonBlock> JACARANDA_BUTTON = registerWithItem("jacaranda_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).isViewBlocking(UtilLib::never).noCollission().strength(0.5F).sound(SoundType.WOOD), LogBlock.JACARANDA_BLOCK_TYPE,30, true));
    public static final RegistryObject<ButtonBlock> MAGIC_BUTTON = registerWithItem("magic_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).isViewBlocking(UtilLib::never).noCollission().noOcclusion().isValidSpawn(ModBlocks::never).strength(0.5F).sound(SoundType.WOOD),LogBlock.MAGIC_BLOCK_TYPE, 30, true));
    public static final RegistryObject<SlabBlock> JACARANDA_SLAB = registerWithItem("jacaranda_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> MAGIC_SLAB = registerWithItem("magic_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> JACARANDA_FENCE_GATE = registerWithItem("jacaranda_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<FenceGateBlock> MAGIC_FENCE_GATE = registerWithItem("magic_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN));
    public static final RegistryObject<FenceBlock> JACARANDA_FENCE = registerWithItem("jacaranda_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PINK).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> MAGIC_FENCE = registerWithItem("magic_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).ignitedByLava().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER = registerWithItem("wolfsbane_diffuser_normal", () -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.NORMAL));
    public static final RegistryObject<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_LONG = registerWithItem("wolfsbane_diffuser_long", () -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.LONG));
    public static final RegistryObject<WolfsbaneDiffuserBlock> WOLFSBANE_DIFFUSER_IMPROVED = registerWithItem("wolfsbane_diffuser_improved", () -> new WolfsbaneDiffuserBlock(WolfsbaneDiffuserBlock.Type.IMPROVED));


    public static class V {
        public static final RegistryObject<Block> MED_CHAIR = RegistryObject.create(new ResourceLocation("vampirism", "med_chair"), ForgeRegistries.Keys.BLOCKS, REFERENCE.MODID);

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        BLOCKS.register(bus);
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier) {
        return registerWithItem(name, supplier, new Item.Properties());
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ModItems.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static Set<Block> getAllBlocks() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet());
    }

    private static boolean never(BlockState pState, BlockGetter pLevel, BlockPos pPos, EntityType<?> pValue) {
        return false;
    }
}
