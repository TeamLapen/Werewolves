package de.teamlapen.werewolves.core;

import de.teamlapen.werewolves.entities.werewolf.BasicWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.HumanWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.TransformedWerewolfEntity;
import de.teamlapen.werewolves.entities.werewolf.WerewolfBaseEntity;
import de.teamlapen.werewolves.util.REFERENCE;
import de.teamlapen.werewolves.util.WReference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(REFERENCE.MODID)
public class ModEntities extends de.teamlapen.vampirism.core.ModEntities {

    public static final EntityType<TransformedWerewolfEntity> transformed_werewolf;
    public static final EntityType<BasicWerewolfEntity.Beast> werewolf_beast;
    public static final EntityType<BasicWerewolfEntity.Survivalist> werewolf_survivalist;
    public static final EntityType<HumanWerewolfEntity> human_werewolf;

    //needed for worldgen
    static {
        transformed_werewolf = prepareEntityType("transformed_werewolf", EntityType.Builder.create(TransformedWerewolfEntity::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.7f, 2f), false);
        werewolf_beast = prepareEntityType("werewolf_beast", EntityType.Builder.create(BasicWerewolfEntity.Beast::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.8f, 2f), true);
        werewolf_survivalist = prepareEntityType("werewolf_survivalist", EntityType.Builder.create(BasicWerewolfEntity.Survivalist::new, WReference.WEREWOLF_CREATUE_TYPE).size(0.8f, 1f), true);
        human_werewolf = prepareEntityType("human_werewolf", EntityType.Builder.create(HumanWerewolfEntity::new, EntityClassification.CREATURE).size(0.6f, 1.9f), true);
    }

    static void registerEntities(IForgeRegistry<EntityType<?>> registry) {
        registry.register(transformed_werewolf);
        registry.register(werewolf_beast);
        registry.register(werewolf_survivalist);
        registry.register(human_werewolf);
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
        EntitySpawnPlacementRegistry.register(werewolf_beast, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(werewolf_survivalist, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, WerewolfBaseEntity::spawnPredicateWerewolf);
        EntitySpawnPlacementRegistry.register(human_werewolf, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HumanWerewolfEntity::spawnPredicateHumanWerewolf);
    }
}
