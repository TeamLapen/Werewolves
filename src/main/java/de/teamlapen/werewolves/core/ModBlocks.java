package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.SaplingBlock;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfsbaneBlock;
import de.teamlapen.werewolves.mixin.BlocksAccessor;
import de.teamlapen.werewolves.mixin.FireBlockAccessor;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import de.teamlapen.werewolves.world.tree.JacarandaTree;
import de.teamlapen.werewolves.world.tree.MagicTree;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModBlocks {

    private static final Set<Block> ALL_BLOCKS = new HashSet<>();

    public static final OreBlock silver_ore = getNull();
    public static final OreBlock deepslate_silver_ore = getNull();
    public static final WolfsbaneBlock wolfsbane = getNull();
    public static final Block silver_block = getNull();
    public static final Block raw_silver_block = getNull();
    public static final FlowerPotBlock potted_wolfsbane = getNull();
    public static final TotemTopBlock totem_top_werewolves_werewolf = getNull();
    public static final TotemTopBlock totem_top_werewolves_werewolf_crafted = getNull();
    public static final net.minecraft.world.level.block.SaplingBlock jacaranda_sapling = getNull();
    public static final LeavesBlock jacaranda_leaves = getNull();
    public static final RotatedPillarBlock jacaranda_log = getNull();
    public static final net.minecraft.world.level.block.SaplingBlock magic_sapling = getNull();
    public static final LeavesBlock magic_leaves = getNull();
    public static final RotatedPillarBlock magic_log = getNull();
    public static final Block magic_planks = getNull();
    public static final StoneAltarBlock stone_altar = getNull();
    public static final StoneAltarFireBowlBlock stone_altar_fire_bowl = getNull();

    @ObjectHolder(de.teamlapen.vampirism.REFERENCE.MODID)
    public static class V {
        public static final Block med_chair = getNull();
    }

    static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(itemBlock(silver_ore));
        registry.register(itemBlock(deepslate_silver_ore));
        registry.register(itemBlock(wolfsbane));
        registry.register(itemBlock(silver_block));
        registry.register(itemBlock(raw_silver_block));
        registry.register(itemBlock(jacaranda_sapling));
        registry.register(itemBlock(jacaranda_leaves));
        registry.register(itemBlock(jacaranda_log));
        registry.register(itemBlock(magic_sapling));
        registry.register(itemBlock(magic_leaves));
        registry.register(itemBlock(magic_log));
        registry.register(itemBlock(magic_planks));
        registry.register(itemBlock(stone_altar));
        registry.register(itemBlock(totem_top_werewolves_werewolf, new Item.Properties()));
        registry.register(itemBlock(totem_top_werewolves_werewolf_crafted, new Item.Properties()));
        registry.register(itemBlock(stone_altar_fire_bowl));
    }


    static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(prepareRegister(new OreBlock(Block.Properties.of(Material.STONE).strength(3.0F, 5.0F).requiresCorrectToolForDrops())).setRegistryName(REFERENCE.MODID, "silver_ore"));
        registry.register(prepareRegister(new OreBlock(Block.Properties.of(Material.STONE).strength(4.5F, 3.0F).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE))).setRegistryName(REFERENCE.MODID, "deepslate_silver_ore"));
        WolfsbaneBlock wolfsbane = new WolfsbaneBlock();
        registry.register(prepareRegister(wolfsbane));
        registry.register(prepareRegister(new Block(Block.Properties.of(Material.METAL, MaterialColor.METAL).strength(5.0F, 6.0F).requiresCorrectToolForDrops().sound(SoundType.METAL)).setRegistryName(REFERENCE.MODID, "silver_block")));
        registry.register(prepareRegister(new FlowerPotBlock(wolfsbane, Block.Properties.of(Material.DECORATION).strength(0f)).setRegistryName(REFERENCE.MODID, "potted_wolfsbane")));
        registry.register(prepareRegister(new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY).setRegistryName(REFERENCE.MODID, "totem_top_werewolves_werewolf")));
        registry.register(prepareRegister(new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY).setRegistryName(REFERENCE.MODID, "totem_top_werewolves_werewolf_crafted")));
        registry.register(prepareRegister(new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()).setRegistryName(REFERENCE.MODID, "jacaranda_leaves")));
        registry.register(prepareRegister(new SaplingBlock(new JacarandaTree()).setRegistryName(REFERENCE.MODID, "jacaranda_sapling")));
        Block log1 = BlocksAccessor.createLogBlock_werewolves(MaterialColor.COLOR_BROWN, MaterialColor.COLOR_BROWN).setRegistryName(REFERENCE.MODID, "jacaranda_log");
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log1, 5, 5);
        registry.register(prepareRegister(log1));
        registry.register(prepareRegister(new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()).setRegistryName(REFERENCE.MODID, "magic_leaves")));
        registry.register(prepareRegister(new SaplingBlock(new MagicTree()).setRegistryName(REFERENCE.MODID, "magic_sapling")));
        Block log2 = BlocksAccessor.createLogBlock_werewolves(MaterialColor.COLOR_BLUE, MaterialColor.COLOR_BLUE).setRegistryName(REFERENCE.MODID, "magic_log");
        ((FireBlockAccessor) Blocks.FIRE).invokeSetFireInfo_werewolves(log2, 5, 5);
        registry.register(prepareRegister(log2));
        registry.register(prepareRegister(new Block(Block.Properties.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)).setRegistryName(REFERENCE.MODID, "magic_planks")));
        registry.register(prepareRegister(new StoneAltarBlock().setRegistryName(REFERENCE.MODID, StoneAltarBlock.REG_NAME)));
        registry.register(prepareRegister(new StoneAltarFireBowlBlock().setRegistryName(REFERENCE.MODID, StoneAltarFireBowlBlock.REG_NAME)));
        registry.register(prepareRegister(new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.RAW_IRON).requiresCorrectToolForDrops().strength(5.0f, 6.0f))).setRegistryName(REFERENCE.MODID, "raw_silver_block"));
    }

    private static Block prepareRegister(Block block) {
        ALL_BLOCKS.add(block);
        return block;
    }

    @Nonnull
    private static BlockItem itemBlock(@Nonnull Block block) {
        return itemBlock(block, new Item.Properties().tab(WUtils.creativeTab));
    }

    @SuppressWarnings("ConstantConditions")
    @Nonnull
    private static BlockItem itemBlock(@Nonnull Block block, @Nonnull Item.Properties props) {
        assert block != null;
        BlockItem item = new BlockItem(block, props);
        item.setRegistryName(block.getRegistryName());
        return item;
    }

    public static Set<Block> getAllBlocks() {
        return ImmutableSet.copyOf(ALL_BLOCKS);
    }
}
