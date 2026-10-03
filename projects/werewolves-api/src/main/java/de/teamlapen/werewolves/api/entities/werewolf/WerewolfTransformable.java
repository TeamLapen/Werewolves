package de.teamlapen.werewolves.api.entities.werewolf;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public interface WerewolfTransformable extends IWerewolfDataholder {
    int TYPES = 126;

    static <T extends Mob> T copyData(EntityType<T> newEntity, Mob oldEntity) {
        return copyData(newEntity.create(oldEntity.level(), EntitySpawnReason.CONVERSION), oldEntity);
    }

    static <T extends Mob> T copyData(T entity, Mob oldEntity) {
        entity.copyPosition(oldEntity);
        entity.level().addFreshEntity(entity);
        oldEntity.remove(Entity.RemovalReason.DISCARDED);
        entity.setHealth(oldEntity.getHealth() / oldEntity.getMaxHealth() * entity.getMaxHealth());
        return entity;
    }

    default WerewolfTransformable transformBack() {
        WerewolfTransformable transformable = _transformBack();
        transformable.reset();
        return transformable;
    }

    default WerewolfTransformable transformToWerewolf(TransformType type) {
        WerewolfTransformable transformable = _transformToWerewolf();
        transformable.start(type);
        return transformable;
    }

    boolean canTransform();

    WerewolfForm getForm();

    default void reset() {
    }

    default void start(TransformType type) {
    }

    WerewolfTransformable _transformToWerewolf();

    WerewolfTransformable _transformBack();

}
