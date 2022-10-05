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

    public static final RegistryObject<EntityType<BasicWerewolfEntity.Beast>> WEREWOLF_BEAST = ENTITIES.register("werewolf_beast", () -> prepareEntityType("werewolf_beast", EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true));
    public static final RegistryObject<EntityType<BasicWerewolfEntity.Survivalist>> WEREWOLF_SURVIVALIST = ENTITIES.register("werewolf_survivalist", () -> prepareEntityType("werewolf_survivalist", EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 1f), true));
    public static final RegistryObject<EntityType<HumanWerewolfEntity>> HUMAN_WEREWOLF = ENTITIES.register("human_werewolf", () -> prepareEntityType("human_werewolf", EntityType.Builder.of(HumanWerewolfEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.9f), true));
    public static final RegistryObject<EntityType<WerewolfTaskMasterEntity>> TASK_MASTER_WEREWOLF = ENTITIES.register("task_master_werewolf", () -> prepareEntityType("task_master_werewolf", EntityType.Builder.of(WerewolfTaskMasterEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.6f, 1.95f), true));
    public static final RegistryObject<EntityType<AggressiveWolfEntity>> WOLF = ENTITIES.register("wolf", () -> prepareEntityType("wolf", EntityType.Builder.of(AggressiveWolfEntity::new, EntityClassification.AMBIENT).sized(0.6F, 0.85F), false));
    public static final RegistryObject<EntityType<WerewolfMinionEntity>> WEREWOLF_MINION = ENTITIES.register("werewolf_minion", () -> prepareEntityType("werewolf_minion", EntityType.Builder.of(WerewolfMinionEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.95f), false));
    public static final RegistryObject<EntityType<WerewolfAlphaEntity>> ALPHA_WEREWOLF = ENTITIES.register("alpha_werewolf", () -> prepareEntityType("alpha_werewolf", EntityType.Builder.of(WerewolfAlphaEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true));

    public static class V {
        public static final RegistryObject<EntityType<BasicHunterEntity>> HUNTER = RegistryObject.of(new ResourceLocation("vampirism", "hunter"), ForgeRegistries.ENTITIES);
        public static final RegistryObject<EntityType<BasicHunterEntity.IMob>> HUNTER_IMOB = RegistryObject.of(new ResourceLocation("vampirism", "hunter_imob"), ForgeRegistries.ENTITIES);

        private static void init() {
        }
    }

    static void registerEntities(IEventBus bus) {
        ENTITIES.register(bus);
    }

    static void initializeEntities() {
        WerewolfMinionEntity.registerMinionData();
    }

    static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(WEREWOLF_BEAST.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(WEREWOLF_SURVIVALIST.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(HUMAN_WEREWOLF.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf);
        EntitySpawnPlacementRegistry.register(WOLF.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (entityType, world, spawnReason, blockPos, random) -> true);
        EntitySpawnPlacementRegistry.register(ALPHA_WEREWOLF.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfAlphaEntity::spawnPredicateAlpha);
    }

    private static <T extends Entity> EntityType<T> prepareEntityType(String id, EntityType.Builder<T> builder, boolean spawnable) {
        EntityType.Builder<T> type = builder.setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
        if (!spawnable)
            type.noSummon();
        return type.build(REFERENCE.MODID + ":" + id);
    }

    static void onRegisterEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(HUMAN_WEREWOLF.get(), HumanWerewolfEntity.getAttributeBuilder().build());
        event.put(WEREWOLF_BEAST.get(), BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(WEREWOLF_SURVIVALIST.get(), BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(WOLF.get(), AggressiveWolfEntity.createAttributes().build());
        event.put(TASK_MASTER_WEREWOLF.get(), WerewolfTaskMasterEntity.getAttributeBuilder().build());
        event.put(WEREWOLF_MINION.get(), WerewolfMinionEntity.getAttributeBuilder().build());
        event.put(ALPHA_WEREWOLF.get(), WerewolfAlphaEntity.getAttributeBuilder().build());
    }

    static void onModifyEntityTypeAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.BITE_DAMAGE.get());
        event.add(EntityType.PLAYER, ModAttributes.TIME_REGAIN.get());
    }

    public static Set<EntityType<?>> getAllEntities() {
        return ImmutableSet.copyOf(ENTITIES.getEntries().stream().flatMap(RegistryObject::stream).collect(Collectors.toList()));
    }

    static {
        V.init();
    }
}
