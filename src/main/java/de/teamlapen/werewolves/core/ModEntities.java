package de.teamlapen.werewolves.core;

import de.teamlapen.vampirism.entity.hunter.BasicHunterEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.blocks.WerewolvesBoatEntity;
import de.teamlapen.werewolves.blocks.WerewolvesChestBoatEntity;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.entities.werewolf.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, REFERENCE.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<BasicWerewolfEntity.Beast>> WEREWOLF_BEAST = prepareEntityType("werewolf_beast", () -> EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<BasicWerewolfEntity.Survivalist>> WEREWOLF_SURVIVALIST = prepareEntityType("werewolf_survivalist", () -> EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 1f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<HumanWerewolfEntity>> HUMAN_WEREWOLF = prepareEntityType("human_werewolf", () -> EntityType.Builder.of(HumanWerewolfEntity::new, MobCategory.CREATURE).sized(0.6f, 1.9f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfTaskMasterEntity>> TASK_MASTER_WEREWOLF = prepareEntityType("task_master_werewolf", () -> EntityType.Builder.of(WerewolfTaskMasterEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.6f, 1.95f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<AggressiveWolfEntity>> WOLF = prepareEntityType("wolf", () -> EntityType.Builder.of(AggressiveWolfEntity::new, MobCategory.AMBIENT).sized(0.6F, 0.85F), false);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfMinionEntity>> WEREWOLF_MINION = prepareEntityType("werewolf_minion", () -> EntityType.Builder.of(WerewolfMinionEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f), false);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfAlphaEntity>> ALPHA_WEREWOLF = prepareEntityType("alpha_werewolf", () -> EntityType.Builder.of(WerewolfAlphaEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolvesBoatEntity>> BOAT = prepareEntityType("boat", () -> EntityType.Builder.<WerewolvesBoatEntity>of(WerewolvesBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10), false);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolvesChestBoatEntity>> CHEST_BOAT = prepareEntityType("chest_boat", () -> EntityType.Builder.<WerewolvesChestBoatEntity>of(WerewolvesChestBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10), false);

    public static class V {
        public static final DeferredHolder<EntityType<?>, EntityType<BasicHunterEntity>> HUNTER = DeferredHolder.create(ResourceKey.create(Registries.ENTITY_TYPE, new ResourceLocation("vampirism", "hunter")));
        public static final DeferredHolder<EntityType<?>, EntityType<BasicHunterEntity.IMob>> HUNTER_IMOB = DeferredHolder.create(ResourceKey.create(Registries.ENTITY_TYPE, new ResourceLocation("vampirism", "hunter_imob")));

        private static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }

    static void registerSpawns(SpawnPlacementRegisterEvent event) {
        event.register(WEREWOLF_BEAST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(WEREWOLF_SURVIVALIST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(HUMAN_WEREWOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, spawnReason, blockPos, random) -> true, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(ALPHA_WEREWOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfAlphaEntity::spawnPredicateAlpha, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> prepareEntityType(String id, Supplier<EntityType.Builder<T>> builder, boolean spawnable) {
        return ENTITY_TYPES.register(id, () -> {
            EntityType.Builder<T> type = builder.get().setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
            if (!spawnable) {
                type.noSummon();
            }
            return type.build(REFERENCE.MODID + ":" + id);
        });
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
        event.add(EntityType.PLAYER, ModAttributes.FOOD_CONSUMPTION.get());
        event.add(EntityType.PLAYER, ModAttributes.FOOD_GAIN.get());
    }

    public static Set<EntityType<?>> getAllEntities() {
        return ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }
}
