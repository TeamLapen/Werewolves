package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.blocks.ModSaplingBlock;
import de.teamlapen.werewolves.blocks.WFlowerBlock;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
import de.teamlapen.werewolves.world.JacarandaTree;
import de.teamlapen.werewolves.world.MagicTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModBlocks extends de.teamlapen.vampirism.core.ModBlocks {

    public static final OreBlock silver_ore = getNull();
    public static final WFlowerBlock wolfsbane = getNull();
    public static final Block silver_block = getNull();
    public static final FlowerPotBlock potted_wolfsbane = getNull();
    public static final TotemTopBlock totem_top_werewolves_werewolf = getNull();
    public static final SaplingBlock jacaranda_sapling = getNull();
    public static final LeavesBlock jacaranda_leaves = getNull();
    public static final LogBlock jacaranda_log = getNull();
    public static final SaplingBlock magic_sapling = getNull();
    public static final LeavesBlock magic_leaves = getNull();
    public static final LogBlock magic_log = getNull();


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
    }


    static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 5.0F)).setRegistryName(REFERENCE.MODID, "silver_ore"));
        WFlowerBlock wolfsbane = new WFlowerBlock(Effects.BLINDNESS, 5);
        registry.register(wolfsbane.setRegistryName(REFERENCE.MODID, "wolfsbane"));
        registry.register(new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(REFERENCE.MODID, "silver_block"));
        registry.register(new FlowerPotBlock(wolfsbane, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0f)).setRegistryName(REFERENCE.MODID, "potted_wolfsbane"));
        registry.register(new TotemTopBlock(REFERENCE.WEREWOLF_PLAYER_KEY).setRegistryName(REFERENCE.MODID, "totem_top_werewolves_werewolf"));
        registry.register(new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)).setRegistryName(REFERENCE.MODID, "jacaranda_leaves"));
        registry.register(new ModSaplingBlock(new JacarandaTree()).setRegistryName(REFERENCE.MODID, "jacaranda_sapling"));
        registry.register(new LogBlock(MaterialColor.BROWN, Block.Properties.create(Material.WOOD, MaterialColor.BROWN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName(REFERENCE.MODID, "jacaranda_log"));
        registry.register(new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)).setRegistryName(REFERENCE.MODID, "magic_leaves"));
        registry.register(new ModSaplingBlock(new MagicTree()).setRegistryName(REFERENCE.MODID, "magic_sapling"));
        registry.register(new LogBlock(MaterialColor.BLUE, Block.Properties.create(Material.WOOD, MaterialColor.BLUE).hardnessAndResistance(2.0F).sound(SoundType.WOOD)).setRegistryName(REFERENCE.MODID, "magic_log"));
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
}
