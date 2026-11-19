package de.teamlapen.werewolves.integration.vampirism.common.core;

import de.teamlapen.werewolves.common.core.ModBlocks;
import de.teamlapen.werewolves.integration.vampirism.common.world.blockentities.WolfsbaneDiffuserBlockEntity;
import de.teamlapen.werewolves.integration.vampirism.common.world.blockentities.WolfsbaneDiffuserCoreBlockEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompatBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WolfsbaneDiffuserBlockEntity>> WOLFSBANE_DIFFUSER = BLOCK_ENTITY_TYPES.register("wolfsbane_diffuser", () -> new BlockEntityType<>(WolfsbaneDiffuserBlockEntity::new, CompatBlocks.WOLFSBANE_DIFFUSER.get(), CompatBlocks.WOLFSBANE_DIFFUSER_LONG.get(), CompatBlocks.WOLFSBANE_DIFFUSER_STRONG.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WolfsbaneDiffuserCoreBlockEntity>> WOLFSBANE_DIFFUSER_CORE = BLOCK_ENTITY_TYPES.register("wolfsbane_diffuser_core", () -> new BlockEntityType<>(WolfsbaneDiffuserCoreBlockEntity::new, CompatBlocks.WOLFSBANE_DIFFUSER_CORE.get(), CompatBlocks.WOLFSBANE_DIFFUSER_CORE_STRONG.get(), CompatBlocks.WOLFSBANE_DIFFUSER_CORE_LONG.get()));

    public static void register(IEventBus bus) {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
