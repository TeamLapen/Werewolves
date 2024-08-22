package de.teamlapen.werewolves.api;

import de.teamlapen.vampirism.api.util.VResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ModRegistryItems {

    public static final DeferredHolder<Block, Block> JACARANDA_PLANKS = DeferredHolder.create(ResourceKey.create(Registries.BLOCK, WResourceLocation.mod("jacaranda_planks")));
    public static final DeferredHolder<Block, Block> MAGIC_PLANKS = DeferredHolder.create(ResourceKey.create(Registries.BLOCK, WResourceLocation.mod("magic_planks")));
    public static final DeferredHolder<Item, Item> JACARANDA_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WResourceLocation.mod("jacaranda_boat")));
    public static final DeferredHolder<Item, Item> MAGIC_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WResourceLocation.mod("magic_boat")));
    public static final DeferredHolder<Item, Item> JACARANDA_CHEST_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WResourceLocation.mod("jacaranda_chest_boat")));
    public static final DeferredHolder<Item, Item> MAGIC_CHEST_BOAT = DeferredHolder.create(ResourceKey.create(Registries.ITEM, WResourceLocation.mod("magic_chest_boat")));
}
