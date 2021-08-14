package de.teamlapen.werewolves.core;

import com.google.common.collect.Maps;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;

public class ModLootTables {

    private static final Map<ResourceLocation, ResourceLocation> INJECTION_TABLES = Maps.newHashMap();

    public static final ResourceLocation villager = entity(EntityType.VILLAGER);
    public static final ResourceLocation skeleton = entity(EntityType.SKELETON);

    static ResourceLocation entity(EntityType<?> type) {
        ResourceLocation loc = type.getDefaultLootTable();
        ResourceLocation newLoc = new ResourceLocation(REFERENCE.MODID, "inject/" + loc.getPath());
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (INJECTION_TABLES.containsKey(event.getName())) {
            try {
                event.getTable().addPool(getInjectPool(event.getName()));
            } catch (NullPointerException ignored){

            }
        }
    }

    private static LootPool getInjectPool(ResourceLocation loc) {
        TableLootEntry.lootTableReference(INJECTION_TABLES.get(loc)).setWeight(1);
        return LootPool.lootPool().name("werewolves_inject_pool").bonusRolls(0,1).setRolls(new RandomValueRange(1)).add(TableLootEntry.lootTableReference(INJECTION_TABLES.get(loc)).setWeight(1)).build();
    }
}
