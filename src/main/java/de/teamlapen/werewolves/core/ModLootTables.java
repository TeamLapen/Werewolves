package de.teamlapen.werewolves.core;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.world.loot.MobLootModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class ModLootTables {

    private static final Map<ResourceLocation, ResourceLocation> INJECTION_TABLES = Maps.newHashMap();

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, REFERENCE.MODID);

    // entities
    public static final ResourceLocation villager = entity(EntityType.VILLAGER);
    public static final ResourceLocation skeleton = entity(EntityType.SKELETON);

    // chests
    public static final ResourceLocation abandoned_mineshaft = chest("abandoned_mineshaft");
    public static final ResourceLocation jungle_temple = chest("jungle_temple");
    public static final ResourceLocation stronghold_corridor = chest("stronghold_corridor");
    public static final ResourceLocation desert_pyramid = chest("desert_pyramid");
    public static final ResourceLocation stronghold_library = chest("stronghold_library");

    public static final RegistryObject<Codec<MobLootModifier>> mob_modifier = GLOBAL_LOOT_MODIFIER.register("mob_modifier", () -> MobLootModifier.CODEC);

    static void register(IEventBus bus){
        GLOBAL_LOOT_MODIFIER.register(bus);
    }

    static ResourceLocation entity(EntityType<?> type) {
        ResourceLocation loc = type.getDefaultLootTable();
        ResourceLocation newLoc = new ResourceLocation(REFERENCE.MODID, "inject/entity/" + loc.getPath());
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    static ResourceLocation chest(String chest) {
        ResourceLocation loc = new ResourceLocation("chests/" + chest);
        ResourceLocation newLoc = new ResourceLocation(REFERENCE.MODID, "inject/chest/" + chest);
        INJECTION_TABLES.put(loc, newLoc);
        return newLoc;
    }

    @SubscribeEvent
    public static void onLootLoad(LootTableLoadEvent event) {
        if (INJECTION_TABLES.containsKey(event.getName())) {
            try {
                event.getTable().addPool(getInjectPool(event.getName()));
            } catch (NullPointerException ignored) {

            }
        }
    }

    private static LootPool getInjectPool(ResourceLocation loc) {
        LootTableReference.lootTableReference(INJECTION_TABLES.get(loc)).setWeight(1);
        return LootPool.lootPool().name("werewolves_inject_pool").setBonusRolls(UniformGenerator.between(0, 1)).setRolls(ConstantValue.exactly(1)).add(LootTableReference.lootTableReference(INJECTION_TABLES.get(loc)).setWeight(1)).build();
    }
}
