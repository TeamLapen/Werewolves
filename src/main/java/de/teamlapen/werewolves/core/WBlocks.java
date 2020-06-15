package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.blocks.TotemTopBlock;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.blocks.WFlowerBlock;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WUtils;
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
public class WBlocks {

    public static final OreBlock silver_ore = getNull();
    public static final WFlowerBlock wolfsbane = getNull();
    public static final Block silver_block = getNull();
    public static final FlowerPotBlock potted_wolfsbane = getNull();
    public static final TotemTopBlock totem_top_werewolves_werewolf = getNull();


    static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(itemBlock(silver_ore));
        registry.register(itemBlock(wolfsbane));
        registry.register(itemBlock(silver_block));
    }


    static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(new OreBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 5.0F)).setRegistryName(REFERENCE.MODID, "silver_ore"));
        WFlowerBlock wolfsbane = new WFlowerBlock(Effects.BLINDNESS,5);
        registry.register(wolfsbane.setRegistryName(REFERENCE.MODID,"wolfsbane"));
        registry.register(new Block(Block.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)).setRegistryName(REFERENCE.MODID, "silver_block"));
        registry.register(new FlowerPotBlock(wolfsbane, Block.Properties.create(Material.MISCELLANEOUS).hardnessAndResistance(0f)).setRegistryName(REFERENCE.MODID, "potted_wolfsbane"));
        registry.register(new TotemTopBlock(REFERENCE.WEREWOLF_PLAYER_KEY).setRegistryName(REFERENCE.MODID,"totem_top_werewolves_werewolf"));
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
