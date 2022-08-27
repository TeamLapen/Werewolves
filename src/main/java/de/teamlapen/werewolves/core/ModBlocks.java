package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.SaplingBlock;
import de.teamlapen.werewolves.blocks.*;
import de.teamlapen.werewolves.mixin.BlocksAccessor;
import de.teamlapen.werewolves.mixin.FireBlockAccessor;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import de.teamlapen.werewolves.world.tree.JacarandaTree;
import de.teamlapen.werewolves.world.tree.MagicTree;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REFERENCE.MODID);

    public static final RegistryObject<DropExperienceBlock> SILVER_ORE = registerWithItem("silver_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops()));
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_SILVER_ORE = registerWithItem("deepslate_silver_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<WolfsbaneBlock> WOLFSBANE = registerWithItem("wolfsbane", WolfsbaneBlock::new);
    public static final RegistryObject<Block> SILVER_BLOCK = registerWithItem("silver_block", () -> new Block(Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)));
    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerWithItem("raw_silver_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0f, 6.0f)));
    public static final RegistryObject<FlowerPotBlock> POTTED_WOLFSBANE = BLOCKS.register("potted_wolfsbane", () ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, WOLFSBANE, Block.Properties.of(Material.DECORATION).strength(0f));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(WOLFSBANE.getId(), () -> pot);
        return pot;
    });
    public static final RegistryObject<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF = BLOCKS.register("totem_top_werewolves_werewolf", () -> new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED = BLOCKS.register("totem_top_werewolves_werewolf_crafted", () -> new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<SaplingBlock> JACARANDA_SAPLING = registerWithItem("jacaranda_sapling", () -> new SaplingBlock(new JacarandaTree()));
    public static final RegistryObject<LeavesBlock> JACARANDA_LEAVES = registerWithItem("jacaranda_leaves", () -> new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> JACARANDA_LOG = registerWithItem("jacaranda_log", () -> {
        RotatedPillarBlock log = BlocksAccessor.createLogBlock_werewolves(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log, 5, 5);
        return log;
    });
    public static final RegistryObject<net.minecraft.world.level.block.SaplingBlock> MAGIC_SAPLING = registerWithItem("magic_sapling", () -> new SaplingBlock(new MagicTree()));
    public static final RegistryObject<LeavesBlock> MAGIC_LEAVES = registerWithItem("magic_leaves", () -> new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> MAGIC_LOG = registerWithItem("magic_log", () -> {
        RotatedPillarBlock log = BlocksAccessor.createLogBlock_werewolves(MaterialColor.COLOR_BLUE, MaterialColor.COLOR_BLUE);
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log, 5, 5);
        return log;
    });
    public static final RegistryObject<Block> MAGIC_PLANKS = registerWithItem("magic_planks", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StoneAltarBlock> STONE_ALTAR = registerWithItem("stone_altar", StoneAltarBlock::new);
    public static final RegistryObject<StoneAltarFireBowlBlock> STONE_ALTAR_FIRE_BOWL = registerWithItem("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);
    public static final RegistryObject<Block> DAFFODIL = registerWithItem("daffodil", DaffodilBlock::new);
    public static final RegistryObject<Block> POTTED_DAFFODIL = BLOCKS.register("potted_daffodil", () ->  {
        var pot = new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, DAFFODIL, Block.Properties.of(Material.DECORATION).instabreak().noOcclusion());
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(DAFFODIL.getId(), () -> pot);
        return pot;
    });
    public static final RegistryObject<Block> WOLF_BERRY_BUSH = BLOCKS.register("wolf_berry_bush", () -> new WolfBerryBushBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));

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
        return registerWithItem(name, supplier, new Item.Properties().tab(WUtils.creativeTab));
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier, Item.@NotNull Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static @NotNull Set<Block> getAllBlocks() {
        return BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet());
    }
}
