package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.teamlapen.vampirism.entity.hunter.BasicHunterEntity;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.entities.minion.WerewolfMinionEntity;
import de.teamlapen.werewolves.entities.werewolf.*;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, REFERENCE.MODID);

    private static final Set<EntityType<?>> ALL_ENTITIES = Sets.newHashSet();

    public static final RegistryObject<EntityType<BasicWerewolfEntity.Beast>> werewolf_beast = prepareEntityType("werewolf_beast", () -> EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);
    public static final RegistryObject<EntityType<BasicWerewolfEntity.Survivalist>> werewolf_survivalist = prepareEntityType("werewolf_survivalist", () -> EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 1f), true);
    public static final RegistryObject<EntityType<HumanWerewolfEntity>> human_werewolf = prepareEntityType("human_werewolf", () -> EntityType.Builder.of(HumanWerewolfEntity::new, MobCategory.CREATURE).sized(0.6f, 1.9f), true);
    public static final RegistryObject<EntityType<WerewolfTaskMasterEntity>> task_master_werewolf = prepareEntityType("task_master_werewolf", () -> EntityType.Builder.of(WerewolfTaskMasterEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.6f, 1.95f), true);
    public static final RegistryObject<EntityType<AggressiveWolfEntity>> wolf = prepareEntityType("wolf", () -> EntityType.Builder.of(AggressiveWolfEntity::new, MobCategory.AMBIENT).sized(0.6F, 0.85F), false);
    public static final RegistryObject<EntityType<WerewolfMinionEntity>> werewolf_minion = prepareEntityType("werewolf_minion", () -> EntityType.Builder.of(WerewolfMinionEntity::new, MobCategory.CREATURE).sized(0.6f, 1.95f), false);
    public static final RegistryObject<EntityType<WerewolfAlphaEntity>> alpha_werewolf = prepareEntityType("alpha_werewolf", () -> EntityType.Builder.of(WerewolfAlphaEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);

    public static class V {
        public static final RegistryObject<EntityType<BasicHunterEntity>> hunter = RegistryObject.create(new ResourceLocation("vampirism", "hunter"), ForgeRegistries.ENTITIES);
        public static final RegistryObject<EntityType<BasicHunterEntity.IMob>> hunter_imob = RegistryObject.create(new ResourceLocation("vampirism", "hunter_imob"), ForgeRegistries.ENTITIES);

        static void init() {

        }
    }

    static {
        V.init();
    }

    static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
        WerewolfMinionEntity.registerMinionData();
    }

    static void registerSpawns() {
        SpawnPlacements.register(werewolf_beast.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        SpawnPlacements.register(werewolf_survivalist.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        SpawnPlacements.register(human_werewolf.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf);
        SpawnPlacements.register(wolf.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (entityType, world, spawnReason, blockPos, random) -> true);
        SpawnPlacements.register(alpha_werewolf.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WerewolfAlphaEntity::spawnPredicateAlpha);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> prepareEntityType(String id, Supplier<EntityType.Builder<T>> builder, boolean spawnable) {
        return ENTITY_TYPES.register(id, () -> {
            EntityType.Builder<T> type = builder.get().setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
            if (!spawnable) {
                type.noSummon();
            }
            return type.build(REFERENCE.MODID + ":" + id);
        });
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
        return ImmutableSet.copyOf(ALL_ENTITIES);
    }
}
