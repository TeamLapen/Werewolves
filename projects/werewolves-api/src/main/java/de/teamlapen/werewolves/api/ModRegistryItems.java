package de.teamlapen.werewolves.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ModRegistryItems {

    public static final DeferredHolder<Block, Block> JACARANDA_PLANKS = DeferredHolder.create(ResourceKey.create(Registries.BLOCK, WIdentifier.mod("jacaranda_planks")));
    public static final DeferredHolder<Block, Block> MAGIC_PLANKS = DeferredHolder.create(ResourceKey.create(Registries.BLOCK, WIdentifier.mod("magic_planks")));
    public static final DeferredHolder<Item, Item> JACARANDA_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WIdentifier.mod("jacaranda_boat")));
    public static final DeferredHolder<Item, Item> MAGIC_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WIdentifier.mod("magic_boat")));
    public static final DeferredHolder<Item, Item> JACARANDA_CHEST_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WIdentifier.mod("jacaranda_chest_boat")));
    public static final DeferredHolder<Item, Item> MAGIC_CHEST_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WIdentifier.mod("magic_chest_boat")));
}
