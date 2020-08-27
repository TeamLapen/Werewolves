package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.entities.PermanentWerewolfEntity;
import de.teamlapen.werewolves.entities.TransformedWerewolfEntity;
import de.teamlapen.werewolves.entities.WerewolfEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(REFERENCE.MODID)
public class ModEntities extends de.teamlapen.vampirism.core.ModEntities {

    public static final EntityType<TransformedWerewolfEntity> transformed_werewolf;
    public static final EntityType<PermanentWerewolfEntity.Beast> permanent_werewolf_beast;
    public static final EntityType<PermanentWerewolfEntity.Survivalist> permanent_werewolf_survivalist;

    //needed for worldgen
    static {
        transformed_werewolf = prepareEntityType("transformed_werewolf", EntityType.Builder.create(TransformedWerewolfEntity::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.7f, 2f), false);
        permanent_werewolf_beast = prepareEntityType("permanent_werewolf_beast", EntityType.Builder.create(PermanentWerewolfEntity.Beast::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.8f, 2f), true);
        permanent_werewolf_survivalist = prepareEntityType("permanent_werewolf_survivalist", EntityType.Builder.create(PermanentWerewolfEntity.Survivalist::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.8f, 1f), true);
    }

    static void registerEntities(IForgeRegistry<EntityType<?>> registry) {
        registry.register(transformed_werewolf);
        registry.register(permanent_werewolf_beast);
        registry.register(permanent_werewolf_survivalist);
    }

    private static <T extends Entity> EntityType<T> prepareEntityType(String id, EntityType.Builder<T> builder, boolean spawnable) {
        EntityType.Builder<T> type = builder.setTrackingRange(80).setUpdateInterval(1).setShouldReceiveVelocityUpdates(true);
        if (!spawnable)
            type.disableSummoning();
        EntityType<T> entry = type.build(REFERENCE.MODID + ":" + id);
        entry.setRegistryName(REFERENCE.MODID, id);
        return entry;
    }

    static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(transformed_werewolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, TransformedWerewolfEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(permanent_werewolf_beast, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(permanent_werewolf_survivalist, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfEntity::spawnPredicateWerewolf);
    }
}
