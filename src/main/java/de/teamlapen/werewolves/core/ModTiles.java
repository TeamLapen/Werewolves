package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.blocks.entity.StoneAltarTileEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModTiles {

    public static final TileEntityType<StoneAltarTileEntity> stone_altar = getNull();

    static void registerTiles(IForgeRegistry<TileEntityType<?>> registry) {
        registry.register(create("stone_altar", StoneAltarTileEntity::new, ModBlocks.stone_altar));
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends TileEntity> TileEntityType<?> create(String id, Supplier<? extends T> factoryIn, Block... blocks) {
        return TileEntityType.Builder.of(factoryIn, blocks).build(null).setRegistryName(REFERENCE.MODID, id);
    }
}
