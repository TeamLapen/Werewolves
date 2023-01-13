package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.mixin.TileEntityTypeAccessor;
import de.teamlapen.werewolves.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

public class ModTiles {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, REFERENCE.MODID);

    public static final RegistryObject<BlockEntityType<StoneAltarBlockEntity>> STONE_ALTAR = BLOCK_ENTITY_TYPES.register("stone_altar", () -> create(StoneAltarBlockEntity::new, ModBlocks.STONE_ALTAR.get()));

    static void register(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factoryIn, Block... blocks) {
        return BlockEntityType.Builder.of(factoryIn, blocks).build(null);
    }

    public static void registerTileExtensionsUnsafe() {
        Set<Block> blocks = new HashSet<>(((TileEntityTypeAccessor) BlockEntityType.SIGN).getValidBlocks());
        blocks.add(ModBlocks.JACARANDA_SIGN.get());
        blocks.add(ModBlocks.MAGIC_SIGN.get());
        blocks.add(ModBlocks.JACARANDA_WALL_SIGN.get());
        blocks.add(ModBlocks.MAGIC_WALL_SIGN.get());
        ((TileEntityTypeAccessor) BlockEntityType.SIGN).setValidBlocks(blocks);
    }
}
