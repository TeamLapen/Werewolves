package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.util.WerewolfForm;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface WerewolfTransformable extends IWerewolfDataholder {
    int TYPES = 126;

    static <T extends Mob> T copyData(EntityType<T> newEntity, Mob oldEntity) {
        return copyData(newEntity.create(oldEntity.getCommandSenderWorld()), oldEntity);
    }

    static <T extends Mob> T copyData(T entity, Mob oldEntity) {
        UUID uuid = entity.getUUID();
        entity.copyPosition(oldEntity);
        entity.restoreFrom(oldEntity);
        entity.setUUID(uuid);
        entity.getCommandSenderWorld().addFreshEntity(entity);
        oldEntity.remove(Entity.RemovalReason.DISCARDED);
        entity.setHealth(oldEntity.getHealth() / oldEntity.getMaxHealth() * entity.getMaxHealth());
        return entity;
    }

    default WerewolfTransformable transformBack() {
        WerewolfTransformable transformable = _transformBack();
        transformable.reset();
        return transformable;
    }

    default BasicWerewolfEntity transformToWerewolf(TransformType type) {
        BasicWerewolfEntity transformable = _transformToWerewolf();
        transformable.start(type);
        return transformable;
    }

    boolean canTransform();

    @Nonnull
    WerewolfForm getForm();

    EntityClassType getEntityClass();

    EntityActionTier getEntityTier();

    int getSkinType();

    int getEyeType();

    default void reset() {
    }

    default void start(TransformType type) {
    }

    BasicWerewolfEntity _transformToWerewolf();

    WerewolfTransformable _transformBack();

    enum TransformType {
        TIME_LIMITED, FULL_MOON, RAID
    }
}
