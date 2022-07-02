package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.blocks.entity.StoneAltarTileEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModTiles {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, REFERENCE.MODID);

    public static final RegistryObject<TileEntityType<StoneAltarTileEntity>> stone_altar = TILE_ENTITIES.register("stone_altar", () -> create(StoneAltarTileEntity::new, ModBlocks.stone_altar.get()));

    static void registerTiles(IEventBus bus) {
        TILE_ENTITIES.register(bus);
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends TileEntity> TileEntityType<T> create(Supplier<T> factoryIn, Block... blocks) {
        return TileEntityType.Builder.of(factoryIn, blocks).build(null);
    }
}
