package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableSet;
import de.teamlapen.vampirism.entity.hunter.BasicHunterEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.entities.werewolf.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, REFERENCE.MODID);

    public static final RegistryObject<EntityType<BasicWerewolfEntity.Beast>> werewolf_beast = ENTITIES.register("werewolf_beast", () -> prepareEntityType("werewolf_beast", EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true));
    public static final RegistryObject<EntityType<BasicWerewolfEntity.Survivalist>> werewolf_survivalist = ENTITIES.register("werewolf_survivalist", () -> prepareEntityType("werewolf_survivalist", EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 1f), true));
    public static final RegistryObject<EntityType<HumanWerewolfEntity>> human_werewolf = ENTITIES.register("human_werewolf", () -> prepareEntityType("human_werewolf", EntityType.Builder.of(HumanWerewolfEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.9f), true));
    public static final RegistryObject<EntityType<WerewolfTaskMasterEntity>> task_master_werewolf = ENTITIES.register("task_master_werewolf", () -> prepareEntityType("task_master_werewolf", EntityType.Builder.of(WerewolfTaskMasterEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.6f, 1.95f), true));
    public static final RegistryObject<EntityType<AggressiveWolfEntity>> wolf = ENTITIES.register("wolf", () -> prepareEntityType("wolf", EntityType.Builder.of(AggressiveWolfEntity::new, EntityClassification.AMBIENT).sized(0.6F, 0.85F), false));
    public static final RegistryObject<EntityType<WerewolfMinionEntity>> werewolf_minion = ENTITIES.register("werewolf_minion", () -> prepareEntityType("werewolf_minion", EntityType.Builder.of(WerewolfMinionEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.95f), false));
    public static final RegistryObject<EntityType<WerewolfAlphaEntity>> alpha_werewolf = ENTITIES.register("alpha_werewolf", () -> prepareEntityType("alpha_werewolf", EntityType.Builder.of(WerewolfAlphaEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true));

    static {
        V.init();
    }
    public static class V {
        public static final RegistryObject<EntityType<BasicHunterEntity>> hunter = RegistryObject.of(new ResourceLocation("vampirism", "hunter"), ForgeRegistries.ENTITIES);
        public static final RegistryObject<EntityType<BasicHunterEntity.IMob>> hunter_imob = RegistryObject.of(new ResourceLocation("vampirism", "hunter_imob"), ForgeRegistries.ENTITIES);

        static void init() {
        }
    }

    static void registerEntities(IEventBus bus) {
        ENTITIES.register(bus);
    }

    static void initializeEntities() {
        WerewolfMinionEntity.registerMinionData();
    }

    static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(werewolf_beast.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(werewolf_survivalist.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(human_werewolf.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf);
        EntitySpawnPlacementRegistry.register(wolf.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (entityType, world, spawnReason, blockPos, random) -> true);
        EntitySpawnPlacementRegistry.register(alpha_werewolf.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfAlphaEntity::spawnPredicateAlpha);
    }

    private static <T extends Entity> EntityType<T> prepareEntityType(String id, EntityType.Builder<T> builder, boolean spawnable) {
        EntityType.Builder<T> type = builder.setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
        if (!spawnable)
            type.noSummon();
        return type.build(REFERENCE.MODID + ":" + id);
    }

    static void onRegisterEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(human_werewolf.get(), HumanWerewolfEntity.getAttributeBuilder().build());
        event.put(werewolf_beast.get(), BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(werewolf_survivalist.get(), BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(wolf.get(), AggressiveWolfEntity.createAttributes().build());
        event.put(task_master_werewolf.get(), WerewolfTaskMasterEntity.getAttributeBuilder().build());
        event.put(werewolf_minion.get(), WerewolfMinionEntity.getAttributeBuilder().build());
        event.put(alpha_werewolf.get(), WerewolfAlphaEntity.getAttributeBuilder().build());
    }

    static void onModifyEntityTypeAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.bite_damage.get());
        event.add(EntityType.PLAYER, ModAttributes.time_regain.get());
    }

    public static Set<EntityType<?>> getAllEntities() {
        return ImmutableSet.copyOf(ENTITIES.getEntries().stream().flatMap(RegistryObject::stream).collect(Collectors.toList()));
    }
}
