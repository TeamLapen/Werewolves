package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.ModSaplingBlock;
import de.teamlapen.werewolves.blocks.StoneAltarBlock;
import de.teamlapen.werewolves.blocks.StoneAltarFireBowlBlock;
import de.teamlapen.werewolves.blocks.WolfsbaneBlock;
import de.teamlapen.werewolves.mixin.MixinAccessors;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import de.teamlapen.werewolves.world.JacarandaTree;
import de.teamlapen.werewolves.world.MagicTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModBlocks extends de.teamlapen.vampirism.core.ModBlocks {

    public static final TotemTopBlock totem_top_werewolves_werewolf_crafted = getNull();

    public static final OreBlock silver_ore = getNull();
    public static final WolfsbaneBlock wolfsbane = getNull();
    public static final Block silver_block = getNull();
    public static final FlowerPotBlock potted_wolfsbane = getNull();
    public static final TotemTopBlock totem_top_werewolves_werewolf = getNull();
    private static final Set<Block> ALL_BLOCKS = new HashSet<>();
    public static final SaplingBlock jacaranda_sapling = getNull();
    public static final LeavesBlock jacaranda_leaves = getNull();
    public static final RotatedPillarBlock jacaranda_log = getNull();
    public static final SaplingBlock magic_sapling = getNull();
    public static final LeavesBlock magic_leaves = getNull();
    public static final RotatedPillarBlock magic_log = getNull();
    public static final Block magic_planks = getNull();
    public static final StoneAltarBlock stone_altar = getNull();
    public static final StoneAltarFireBowlBlock stone_altar_fire_bowl = getNull();

    static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(itemBlock(silver_ore));
        registry.register(itemBlock(wolfsbane));
        registry.register(itemBlock(silver_block));
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
        registry.register(prepareRegister(new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 5.0F))).setRegistryName(REFERENCE.MODID, "silver_ore"));
        WolfsbaneBlock wolfsbane = new WolfsbaneBlock();
        registry.register(prepareRegister(wolfsbane));
        registry.register(prepareRegister(new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(REFERENCE.MODID, "silver_block")));
        registry.register(prepareRegister(new FlowerPotBlock(wolfsbane, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0f)).setRegistryName(REFERENCE.MODID, "potted_wolfsbane")));
        registry.register(prepareRegister(new TotemTopBlock(false, REFERENCE.WEREWOLF_PLAYER_KEY).setRegistryName(REFERENCE.MODID, "totem_top_werewolves_werewolf")));
        registry.register(prepareRegister(new TotemTopBlock(true, REFERENCE.WEREWOLF_PLAYER_KEY).setRegistryName(REFERENCE.MODID, "totem_top_werewolves_werewolf_crafted")));
        registry.register(prepareRegister(new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName(REFERENCE.MODID, "jacaranda_leaves")));
        registry.register(prepareRegister(new ModSaplingBlock(new JacarandaTree()).setRegistryName(REFERENCE.MODID, "jacaranda_sapling")));
        Block log1 = MixinAccessors.BlocksInvoker.createLogBlock(MaterialColor.BROWN, MaterialColor.BROWN).setRegistryName(REFERENCE.MODID, "jacaranda_log");
        ((MixinAccessors.FireBlockInvoker) Blocks.FIRE).invokeSetFireInfo(log1, 5, 5);
        registry.register(prepareRegister(log1));
        registry.register(prepareRegister(new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()).setRegistryName(REFERENCE.MODID, "magic_leaves")));
        registry.register(prepareRegister(new ModSaplingBlock(new MagicTree()).setRegistryName(REFERENCE.MODID, "magic_sapling")));
        Block log2 = MixinAccessors.BlocksInvoker.createLogBlock(MaterialColor.BLUE, MaterialColor.BLUE).setRegistryName(REFERENCE.MODID, "magic_log");
        ((MixinAccessors.FireBlockInvoker) Blocks.FIRE).invokeSetFireInfo(log2, 5, 5);
        registry.register(prepareRegister(log2));
        registry.register(prepareRegister(new Block(Block.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)).setRegistryName(REFERENCE.MODID, "magic_planks")));
        registry.register(prepareRegister(new StoneAltarBlock().setRegistryName(REFERENCE.MODID, StoneAltarBlock.REG_NAME)));
        registry.register(prepareRegister(new StoneAltarFireBowlBlock().setRegistryName(REFERENCE.MODID, StoneAltarFireBowlBlock.REG_NAME)));
    }

    private static Block prepareRegister(Block block) {
        ALL_BLOCKS.add(block);
        return block;
    }

    @Nonnull
    private static BlockItem itemBlock(@Nonnull Block block) {
        return itemBlock(block, new Item.Properties().group(WUtils.creativeTab));
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
