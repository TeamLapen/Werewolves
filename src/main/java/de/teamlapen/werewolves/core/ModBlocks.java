package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.ModSaplingBlock;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfsbaneBlock;
import de.teamlapen.werewolves.mixin.BlocksInvoker;
import de.teamlapen.werewolves.mixin.FireBlockInvoker;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import de.teamlapen.werewolves.world.JacarandaTree;
import de.teamlapen.werewolves.world.MagicTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, REFERENCE.MODID);

    public static final RegistryObject<OreBlock> SILVER_ORE = registerWithItem("silver_ore", () -> new OreBlock(Block.Properties.of(Material.STONE).strength(3.0F, 5.0F)));
    public static final RegistryObject<WolfsbaneBlock> WOLFSBANE = registerWithItem("wolfsbane", WolfsbaneBlock::new);
    public static final RegistryObject<Block> SILVER_BLOCK = registerWithItem("silver_block", () -> new Block(Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F, 6.0F).sound(SoundType.METAL)));
    public static final RegistryObject<FlowerPotBlock> POTTED_WOLFSBANE = BLOCKS.register("potted_wolfsbane", () -> new FlowerPotBlock(() -> ((FlowerPotBlock) Blocks.FLOWER_POT), ModBlocks.WOLFSBANE, Block.Properties.of(Material.DECORATION).strength(0f)));
    public static final RegistryObject<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF = registerWithItem("totem_top_werewolves_werewolf", () -> new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<TotemTopBlock> TOTEM_TOP_WEREWOLVES_WEREWOLF_CRAFTED = registerWithItem("totem_top_werewolves_werewolf_crafted", () -> new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY));
    public static final RegistryObject<SaplingBlock> JACARANDA_SAPLING = registerWithItem("jacaranda_sapling", () -> new ModSaplingBlock(new JacarandaTree()));
    public static final RegistryObject<LeavesBlock> JACARANDA_LEAVES = registerWithItem("jacaranda_leaves", () -> new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> JACARANDA_LOG = registerWithItem("jacaranda_log", flammable(() -> BlocksInvoker.createLogBlock_werewolves(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN),5,5));
    public static final RegistryObject<SaplingBlock> MAGIC_SAPLING = registerWithItem("magic_sapling", () -> new ModSaplingBlock(new MagicTree()));
    public static final RegistryObject<LeavesBlock> MAGIC_LEAVES = registerWithItem("magic_leaves", () -> new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<RotatedPillarBlock> MAGIC_LOG = registerWithItem("magic_log", flammable(() -> BlocksInvoker.createLogBlock_werewolves(MaterialColor.COLOR_BLUE, MaterialColor.COLOR_BLUE),5,5));
    public static final RegistryObject<Block> MAGIC_PLANKS = registerWithItem("magic_planks", () -> new Block(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StoneAltarBlock> STONE_ALTAR = registerWithItem("stone_altar", StoneAltarBlock::new);
    public static final RegistryObject<StoneAltarFireBowlBlock> STONE_ALTAR_FIRE_BOWL = registerWithItem("stone_altar_fire_bowl", StoneAltarFireBowlBlock::new);

    public static class V {
        public static final RegistryObject<Block> MED_CHAIR = RegistryObject.of(new ResourceLocation("vampirism", "med_chair"), ForgeRegistries.BLOCKS);
        public static final RegistryObject<Block> CURSED_EARTH = RegistryObject.of(new ResourceLocation("vampirism", "cursed_earth"), ForgeRegistries.BLOCKS);

        private static void init() {};
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier) {
        return registerWithItem(name, supplier, new Item.Properties().tab(WUtils.creativeTab));
    }

    private static <T extends Block> RegistryObject<T> registerWithItem(String name, Supplier<T> supplier, Item.Properties properties) {
        RegistryObject<T> block = BLOCKS.register(name, supplier);
        ModItems.ITEMS.register(name, ()-> new BlockItem(block.get(), properties));
        return block;
    }

    private static <T extends Block> Supplier<T> flammable(Supplier<T> supplier, int i1, int i2) {
        return () -> {
            T block = supplier.get();
            ((FireBlockInvoker) Blocks.FIRE).invokeSetFireInfo_werewolves(block, i1,i2);
            return block;
        };
    }

    static void registerBlocks(IEventBus bus) {
        BLOCKS.register(bus);
    }

    static {
        V.init();
    }

}
