package de.teamlapen.vampirewerewolf.core;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;
import de.teamlapen.lib.lib.item.ItemMetaBlock;
import de.teamlapen.vampirewerewolf.blocks.BlockSilverOre;
import de.teamlapen.vampirewerewolf.blocks.WerewolfFlower;
import de.teamlapen.vampirewerewolf.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import javax.annotation.Nonnull;

/**
 * Handles all block registrations and reference.
 */
@GameRegistry.ObjectHolder(REFERENCE.MODID)
public class ModBlocks {

    public static final BlockSilverOre silver_ore = getNull();
    public static final WerewolfFlower werewolf_flower = getNull();

    static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(itemBlock(silver_ore));
        registry.register(new ItemMetaBlock(werewolf_flower));
    }

    private static @Nonnull ItemBlock itemBlock(@Nonnull Block b) {
        ItemBlock item = new ItemBlock(b);
        //noinspection ConstantConditions
        item.setRegistryName(b.getRegistryName());
        return item;
    }

    static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(new BlockSilverOre());
        registry.register(new WerewolfFlower());
    }
}
