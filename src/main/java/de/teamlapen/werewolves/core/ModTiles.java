package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModTiles {

    public static final BlockEntityType<StoneAltarBlockEntity> stone_altar = getNull();

    static void registerTiles(IForgeRegistry<BlockEntityType<?>> registry) {
        registry.register(create("stone_altar", StoneAltarBlockEntity::new, ModBlocks.stone_altar));
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends BlockEntity> BlockEntityType<?> create(String id, BlockEntityType.BlockEntitySupplier<? extends T> factoryIn, Block... blocks) {
        return BlockEntityType.Builder.of(factoryIn, blocks).build(null).setRegistryName(REFERENCE.MODID, id);
    }
}
