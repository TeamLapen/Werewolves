package de.teamlapen.werewolves.core;

import de.teamlapen.lib.lib.util.UtilLib;
import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.SaplingBlock;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfsbaneBlock;
import de.teamlapen.werewolves.mixin.BlocksAccessor;
import de.teamlapen.werewolves.mixin.FireBlockAccessor;
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
    public static final RegistryObject<SaplingBlock> JACARANDA_SAPLING = registerWithItem("jacaranda_sapling", () -> new SaplingBlock(new JacarandaTree()));
    public static final RegistryObject<LeavesBlock> JACARANDA_LEAVES = registerWithItem("jacaranda_leaves", () -> new LeavesBlock(Block.Properties.of().isViewBlocking(UtilLib::never).pushReaction(PushReaction.DESTROY).ignitedByLava().strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> JACARANDA_LOG = registerWithItem("jacaranda_log", () -> {
        RotatedPillarBlock log = BlocksAccessor.createLogBlock_werewolves(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log, 5, 5);
        return log;
    });
    public static final RegistryObject<net.minecraft.world.level.block.SaplingBlock> MAGIC_SAPLING = registerWithItem("magic_sapling", () -> new SaplingBlock(new MagicTree()));
    public static final RegistryObject<LeavesBlock> MAGIC_LEAVES = registerWithItem("magic_leaves", () -> new LeavesBlock(Block.Properties.of().isViewBlocking(UtilLib::never).pushReaction(PushReaction.DESTROY).ignitedByLava().strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> MAGIC_LOG = registerWithItem("magic_log", () -> {
        RotatedPillarBlock log = BlocksAccessor.createLogBlock_werewolves(MapColor.COLOR_BLUE, MapColor.COLOR_BLUE);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log, 5, 5);
        return log;
    });
    public static final RegistryObject<Block> MAGIC_PLANKS = registerWithItem("magic_planks", () -> new Block(Block.Properties.of().ignitedByLava().mapColor(MapColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StoneAltarBlock> STONE_ALTAR = registerWithItem("stone_altar", StoneAltarBlock::new);
    public static final RegistryObject<StoneAltarFireBowlBlock> STONE_ALTAR_FIRE_BOWL = registerWithItem("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);

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
