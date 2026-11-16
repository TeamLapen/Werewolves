package de.teamlapen.werewolves.common.core;

import com.google.common.collect.Maps;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.api.WReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ModLootTables {

    private static final Set<ResourceKey<LootTable>> LOOT_TABLES = new HashSet<>();

    private static final Map<ResourceKey<LootTable>, ResourceKey<LootTable>> INJECTION_TABLES = Maps.newHashMap();

    public static final ResourceKey<LootTable> HUNTER_LIVER = register(WIdentifier.mod("hunter_liver"));

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

    // Minion tasks
    public static final ResourceKey<LootTable> MINION_COLLECT_WEREWOLF = register(WIdentifier.mod("minion_tasks/collect_werewolf"));


    static ResourceKey<LootTable> registerInject(EntityType<?> type) {
        ResourceKey<LootTable> loc = type.getDefaultLootTable().orElseThrow();
        ResourceKey<LootTable> newLoc = register(WIdentifier.mod(loc.identifier().withPrefix("inject/entity/").getPath()));
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    static ResourceKey<LootTable> registerInject(ResourceKey<LootTable> originalTable) {
        ResourceKey<LootTable> key = register(originalTable.identifier().withPrefix("inject/"));
        INJECTION_TABLES.put(originalTable, key);
        return key;
    }

    static ResourceKey<LootTable> register(Identifier resourceLocation) {
        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, resourceLocation);
        LOOT_TABLES.add(key);
        return key;
    }

    public static Set<ResourceKey<LootTable>> getLootTables() {
        return Collections.unmodifiableSet(LOOT_TABLES);
    }

    public static Map<ResourceKey<LootTable>, ResourceKey<LootTable>> getInjectTables() {
        return Collections.unmodifiableMap(INJECTION_TABLES);
    }
}
