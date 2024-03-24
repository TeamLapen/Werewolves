package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.mixin.accessor.TileEntityTypeAccessor;
import de.teamlapen.werewolves.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.blocks.entity.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Set;

public class ModTiles {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneAltarBlockEntity>> STONE_ALTAR = BLOCK_ENTITY_TYPES.register("stone_altar", () -> create(StoneAltarBlockEntity::new, ModBlocks.STONE_ALTAR.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WolfsbaneDiffuserBlockEntity>> WOLFSBANE_DIFFUSER = BLOCK_ENTITY_TYPES.register("wolfsbane_diffuser", () -> create(WolfsbaneDiffuserBlockEntity::new, ModBlocks.WOLFSBANE_DIFFUSER.get(), ModBlocks.WOLFSBANE_DIFFUSER_LONG.get(), ModBlocks.WOLFSBANE_DIFFUSER_IMPROVED.get()));

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
