package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface WerewolfTransformable {
    int TYPES = 126;

    static <T extends MobEntity> T copyData(EntityType<T> newEntity, MobEntity oldEntity) {
        return copyData(newEntity.create(oldEntity.getEntityWorld()), oldEntity);
    }

    static <T extends MobEntity> T copyData(T entity, MobEntity oldEntity) {
        UUID uuid = entity.getUniqueID();
        entity.copyLocationAndAnglesFrom(oldEntity);
        entity.copyDataFromOld(oldEntity);
        entity.setUniqueId(uuid);
        entity.getEntityWorld().addEntity(entity);
        oldEntity.remove(true);
        entity.setHealth(oldEntity.getHealth() / oldEntity.getMaxHealth() * entity.getMaxHealth());
        return entity;
    }

    default WerewolfTransformable transformBack() {
        WerewolfTransformable transformable = _transformBack();
        transformable.reset();
        return transformable;
    }

    default BasicWerewolfEntity transformToWerewolf() {
        BasicWerewolfEntity transformable = _transformToWerewolf();
        transformable.start();
        return transformable;
    }

    boolean canTransform();

    @Nonnull
    WerewolfFormUtil.Form getWerewolfForm();

    EntityClassType getEntityClass();

    EntityActionTier getEntityTier();

    int getEntityTextureType();

    default void reset() {
    }

    default void start() {
    }

    BasicWerewolfEntity _transformToWerewolf();

    WerewolfTransformable _transformBack();
}
