package de.teamlapen.werewolves.core;

import com.google.common.collect.Maps;
import de.teamlapen.werewolves.api.WResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModLootTables {

    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();

    private static final Map<ResourceKey<LootTable>, ResourceKey<LootTable>> INJECTION_TABLES = Maps.newHashMap();

    public static final ResourceKey<LootTable> HUNTER_LIVER = register(WResourceLocation.mod("hunter_liver"));

    // chests
    public static final ResourceKey<LootTable> ABANDONED_MINESHAFT = registerInject(BuiltInLootTables.ABANDONED_MINESHAFT);
    public static final ResourceKey<LootTable> JUNGLE_TEMPLE = registerInject(BuiltInLootTables.JUNGLE_TEMPLE);
    public static final ResourceKey<LootTable> STRONGHOLD_CORRIDOR = registerInject(BuiltInLootTables.STRONGHOLD_CORRIDOR);
    public static final ResourceKey<LootTable> DESERT_PYRAMID = registerInject(BuiltInLootTables.DESERT_PYRAMID);
    public static final ResourceKey<LootTable> STRONGHOLD_LIBRARY = registerInject(BuiltInLootTables.STRONGHOLD_LIBRARY);
    public static final ResourceKey<LootTable> NETHER_BRIDGE = registerInject(BuiltInLootTables.NETHER_BRIDGE);


    // entities
    public static final ResourceKey<LootTable> VILLAGER = registerInject(EntityType.VILLAGER);
    public static final ResourceKey<LootTable> SKELETON = registerInject(EntityType.SKELETON);


    static ResourceKey<LootTable> registerInject(EntityType<?> type) {
        ResourceKey<LootTable> loc = type.getDefaultLootTable();
        ResourceKey<LootTable> newLoc = register(WResourceLocation.mod(loc.location().withPrefix("inject/entity/").getPath()));
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    static @NotNull ResourceKey<LootTable> registerInject(ResourceKey<LootTable> originalTable) {
        ResourceKey<LootTable> key = register(originalTable.location().withPrefix("inject/"));
        INJECTION_TABLES.put(originalTable, key);
        return key;
    }

    static @NotNull ResourceKey<LootTable> register(@NotNull ResourceLocation resourceLocation) {
        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, resourceLocation);
        LOOT_TABLES.add(key);
        return key;
    }

    public static @NotNull Set<ResourceKey<LootTable>> getLootTables() {
        return Collections.unmodifiableSet(LOOT_TABLES);
    }

    public static @NotNull Map<ResourceKey<LootTable>, ResourceKey<LootTable>> getInjectTables() {
        return Collections.unmodifiableMap(INJECTION_TABLES);
    }
}
