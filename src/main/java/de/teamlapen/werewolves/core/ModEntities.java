package de.teamlapen.werewolves.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import de.teamlapen.werewolves.WerewolvesMod;
import de.teamlapen.werewolves.entities.AggressiveWolfEntity;
import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.HumanWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import de.teamlapen.werewolves.entities.werewolf.WerewolfTaskMasterEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Set;

import static de.teamlapen.lib.lib.util.UtilLib.getNull;

@ObjectHolder(REFERENCE.MODID)
public class ModEntities extends de.teamlapen.vampirism.core.ModEntities {

    private static final Set<EntityType<?>> ALL_ENTITIES = Sets.newHashSet();

    public static final EntityType<BasicWerewolfEntity.Beast> werewolf_beast;
    public static final EntityType<BasicWerewolfEntity.Survivalist> werewolf_survivalist;
    public static final EntityType<HumanWerewolfEntity> human_werewolf;
    public static final EntityType<WerewolfTaskMasterEntity> task_master_werewolf = getNull();
    public static final EntityType<AggressiveWolfEntity> wolf = getNull();

    static void registerEntities(IForgeRegistry<EntityType<?>> registry) {
        registry.register(werewolf_beast);
        registry.register(werewolf_survivalist);
        registry.register(human_werewolf);
        registry.register(prepareEntityType("wolf", EntityType.Builder.of(AggressiveWolfEntity::new, EntityClassification.AMBIENT).sized(0.6F, 0.85F), false));
        registry.register(prepareEntityType("task_master_werewolf", EntityType.Builder.of(WerewolfTaskMasterEntity::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.6f, 1.95f), true));
    }

    static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(werewolf_beast, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(werewolf_survivalist, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(human_werewolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf);
        EntitySpawnPlacementRegistry.register(wolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
    }

    private static <T extends Entity> EntityType<T> prepareEntityType(String id, EntityType.Builder<T> builder, boolean spawnable) {
        EntityType.Builder<T> type = builder.setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
        if (!spawnable)
            type.noSummon();
        EntityType<T> entry = type.build(REFERENCE.MODID + ":" + id);
        entry.setRegistryName(REFERENCE.MODID, id);
        ALL_ENTITIES.add(entry);
        return entry;
    }

    static void onRegisterEntityTypeAttributes(EntityAttributeCreationEvent event) {
        event.put(human_werewolf, HumanWerewolfEntity.getAttributeBuilder().build());
        event.put(werewolf_beast, BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(werewolf_survivalist, BasicWerewolfEntity.getAttributeBuilder().build());
        event.put(wolf, AggressiveWolfEntity.createAttributes().build());
        event.put(task_master_werewolf, WerewolfTaskMasterEntity.getAttributeBuilder().build());
    }

    static void onModifyEntityTypeAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.bite_damage);
        event.add(EntityType.PLAYER, ModAttributes.time_regain);
    }

    //needed for worldgen
    static {
        werewolf_beast = prepareEntityType("werewolf_beast", EntityType.Builder.of(BasicWerewolfEntity.Beast::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 2f), true);
        werewolf_survivalist = prepareEntityType("werewolf_survivalist", EntityType.Builder.of(BasicWerewolfEntity.Survivalist::new, WerewolvesMod.WEREWOLF_CREATURE_TYPE).sized(0.8f, 1f), true);
        human_werewolf = prepareEntityType("human_werewolf", EntityType.Builder.of(HumanWerewolfEntity::new, EntityClassification.CREATURE).sized(0.6f, 1.9f), true);
    }

    public static Set<EntityType<?>> getAllEntities() {
        return ImmutableSet.copyOf(ALL_ENTITIES);
    }
}
