package de.teamlapen.werewolves.common.core;

import de.teamlapen.werewolves.common.world.blocks.entity.StoneAltarBlockEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StoneAltarBlockEntity>> STONE_ALTAR = BLOCK_ENTITY_TYPES.register("stone_altar", () -> create(StoneAltarBlockEntity::new, ModBlocks.STONE_ALTAR.get()));

    static void register(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factoryIn, Block... blocks) {
        return new BlockEntityType<>(factoryIn, blocks);
    }

    /**
     * Lets hoppers and pipes insert into the stone altar. The slot restrictions are checked by {@link StoneAltarBlockEntity#canPlaceItem(int, net.minecraft.world.item.ItemStack)}
     */
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK, STONE_ALTAR.get(), (altar, side) -> VanillaContainerWrapper.of(altar));
    }

    static void registerTileExtensions(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, ModBlocks.JACARANDA_SIGN.get(), ModBlocks.MAGIC_SIGN.get(), ModBlocks.JACARANDA_WALL_SIGN.get(), ModBlocks.MAGIC_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN, ModBlocks.JACARANDA_HANGING_SIGN.get(), ModBlocks.MAGIC_HANGING_SIGN.get(), ModBlocks.JACARANDA_WALL_HANGING_SIGN.get(), ModBlocks.MAGIC_WALL_HANGING_SIGN.get());
    }
}
