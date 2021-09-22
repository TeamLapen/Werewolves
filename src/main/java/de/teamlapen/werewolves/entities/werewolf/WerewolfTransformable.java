package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.player.WerewolfForm;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface WerewolfTransformable {
    int TYPES = 126;

    static <T extends MobEntity> T copyData(EntityType<T> newEntity, MobEntity oldEntity) {
        return copyData(newEntity.create(oldEntity.getCommandSenderWorld()), oldEntity);
    }

    static <T extends MobEntity> T copyData(T entity, MobEntity oldEntity) {
        UUID uuid = entity.getUUID();
        entity.copyPosition(oldEntity);
        entity.restoreFrom(oldEntity);
        entity.setUUID(uuid);
        entity.getCommandSenderWorld().addFreshEntity(entity);
        oldEntity.remove(true);
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
    WerewolfForm getWerewolfForm();

    EntityClassType getEntityClass();

    EntityActionTier getEntityTier();

    int getEntityTextureType();

    int getEyeTextureType();

    default void reset() {
    }

    default void start(TransformType type) {
    }

    BasicWerewolfEntity _transformToWerewolf();

    WerewolfTransformable _transformBack();

    enum TransformType {
        TIME_LIMITED, FULL_MOON
    }
}
