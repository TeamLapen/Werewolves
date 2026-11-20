package de.teamlapen.werewolves.common.core;

import de.teamlapen.faction.common.event.PlayerEventHandlerEvent;
import de.teamlapen.werewolves.api.WEnums;
import de.teamlapen.werewolves.api.WIdentifier;
import de.teamlapen.werewolves.common.world.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.common.world.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.common.world.entities.werewolf.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ModEntities {

    public static final DeferredRegister.Entities ENTITY_TYPES = DeferredRegister.createEntities(REFERENCE.MODID);


    public static final DeferredHolder<EntityType<?>, EntityType<BasicWerewolfEntity.Beast>> WEREWOLF_BEAST = prepareEntityType("werewolf_beast", () -> EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WEnums.WEREWOLF_CATEGORY.getValue()).sized(0.8f, 2f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<BasicWerewolfEntity.Survivalist>> WEREWOLF_SURVIVALIST = prepareEntityType("werewolf_survivalist", () -> EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WEnums.WEREWOLF_CATEGORY.getValue()).sized(0.8f, 1f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<HumanWerewolfEntity>> HUMAN_WEREWOLF = prepareEntityType("human_werewolf", () -> EntityType.Builder.of(HumanWerewolfEntity::new, MobCategory.CREATURE).sized(0.6f, 1.9f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfTaskMasterEntity>> TASK_MASTER_WEREWOLF = prepareEntityType("task_master_werewolf", () -> EntityType.Builder.of(WerewolfTaskMasterEntity::new, WEnums.WEREWOLF_CATEGORY.getValue()).sized(0.6f, 1.95f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<AggressiveWolfEntity>> WOLF = prepareEntityType("wolf", () -> EntityType.Builder.of(AggressiveWolfEntity::new, MobCategory.AMBIENT).sized(0.6F, 0.85F), false);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfMinionEntity>> WEREWOLF_MINION = prepareEntityType("werewolf_minion", () -> EntityType.Builder.of(WerewolfMinionEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f), false);
    public static final DeferredHolder<EntityType<?>, EntityType<WerewolfAlphaEntity>> ALPHA_WEREWOLF = prepareEntityType("alpha_werewolf", () -> EntityType.Builder.of(WerewolfAlphaEntity::new, WEnums.WEREWOLF_CATEGORY.getValue()).sized(0.8f, 2f), true);
    public static final DeferredHolder<EntityType<?>, EntityType<Boat>> JACARANDA_BOAT = registerEntityType("jacarana_boat", EntityType.boatFactory(ModItems.JACARANDA_BOAT::get), MobCategory.MISC, x -> x.sized(1.375f,0.5625f).noLootTable().eyeHeight(0.5625f).clientTrackingRange(10).noSummon());
    public static final DeferredHolder<EntityType<?>, EntityType<Boat>> MAGIC_BOAT = registerEntityType("magic_boat", EntityType.boatFactory(ModItems.MAGIC_BOAT::get), MobCategory.MISC, x -> x.sized(1.375f,0.5625f).noLootTable().eyeHeight(0.5625f).clientTrackingRange(10).noSummon());
    public static final DeferredHolder<EntityType<?>, EntityType<ChestBoat>> JACARANDA_CHEST_BOAT = registerEntityType("jacarana_chest_boat", EntityType.chestBoatFactory(ModItems.JACARANDA_CHEST_BOAT::get), MobCategory.MISC, x -> x.sized(1.375f,0.5625f).noLootTable().eyeHeight(0.5625f).clientTrackingRange(10).noSummon());
    public static final DeferredHolder<EntityType<?>, EntityType<ChestBoat>> MAGIC_CHEST_BOAT = registerEntityType("magic_chest_boat", EntityType.chestBoatFactory(ModItems.MAGIC_CHEST_BOAT::get), MobCategory.MISC, x -> x.sized(1.375f,0.5625f).noLootTable().eyeHeight(0.5625f).clientTrackingRange(10).noSummon());

    static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }

    public static void registerSpawns(RegisterSpawnPlacementsEvent event) {
        event.register(WEREWOLF_BEAST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(WEREWOLF_SURVIVALIST.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(HUMAN_WEREWOLF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(WOLF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, spawnReason, blockPos, random) -> true, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ALPHA_WEREWOLF.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfAlphaEntity::spawnPredicateAlpha, RegisterSpawnPlacementsEvent.Operation.OR);
    }

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> prepareEntityType(String id, Supplier<EntityType.Builder<T>> builder, boolean spawnable) {
        return ENTITY_TYPES.register(id, () -> {
            EntityType.Builder<T> type = builder.get().setTrackingRange(10).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
            if (!spawnable) {
                type.noSummon();
            }
            return type.build(ResourceKey.create(Registries.ENTITY_TYPE, WIdentifier.mod(id)));
        });
    }

    public static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntityType(String name, EntityType.EntityFactory<E> factory, MobCategory category, UnaryOperator<EntityType.Builder<E>> builder) {
        return ENTITY_TYPES.registerEntityType(name, factory, category, b -> builder.apply(b.clientTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true)));
    }

    public static void onRegisterEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(HUMAN_WEREWOLF.get(), HumanWerewolfEntity.getAttributeBuilder().build());
        event.put(WEREWOLF_BEAST.get(), BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(WEREWOLF_SURVIVALIST.get(), BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(WOLF.get(), AggressiveWolfEntity.createAttributes().build());
        event.put(TASK_MASTER_WEREWOLF.get(), WerewolfTaskMasterEntity.getAttributeBuilder().build());
        event.put(WEREWOLF_MINION.get(), WerewolfMinionEntity.getAttributeBuilder().build());
        event.put(ALPHA_WEREWOLF.get(), WerewolfAlphaEntity.getAttributeBuilder().build());
    }

    public static void onModifyEntityTypeAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.BITE_DAMAGE);
        event.add(EntityType.PLAYER, ModAttributes.TIME_REGAIN);
        event.add(EntityType.PLAYER, ModAttributes.FOOD_CONSUMPTION);
        event.add(EntityType.PLAYER, ModAttributes.FOOD_GAIN);
    }

    public static void registerPlayerEventHandler(PlayerEventHandlerEvent event) {
        event.addAttachmentListener(ModAttachments.WEREWOLF_PLAYER);
    }

    public static Set<EntityType<?>> getAllEntities() {
        return ENTITY_TYPES.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }
}
