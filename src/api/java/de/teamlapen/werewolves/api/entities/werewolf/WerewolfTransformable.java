package de.teamlapen.werewolves.api.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface WerewolfTransformable extends IWerewolfDataholder {
    int TYPES = 126;

    static <T extends Mob> @NotNull T copyData(@NotNull EntityType<T> newEntity, @NotNull Mob oldEntity) {
        return copyData(newEntity.create(oldEntity.getCommandSenderWorld()), oldEntity);
    }

    static <T extends Mob> @NotNull T copyData(@NotNull T entity, @NotNull Mob oldEntity) {
        UUID uuid = entity.getUUID();
        entity.copyPosition(oldEntity);
        entity.restoreFrom(oldEntity);
        entity.setUUID(uuid);
        entity.getCommandSenderWorld().addFreshEntity(entity);
        oldEntity.remove(Entity.RemovalReason.DISCARDED);
        entity.setHealth(oldEntity.getHealth() / oldEntity.getMaxHealth() * entity.getMaxHealth());
        return entity;
    }

    default @NotNull WerewolfTransformable transformBack() {
        WerewolfTransformable transformable = _transformBack();
        transformable.reset();
        return transformable;
    }

    default @NotNull WerewolfTransformable transformToWerewolf(TransformType type) {
        WerewolfTransformable transformable = _transformToWerewolf();
        transformable.start(type);
        return transformable;
    }

    boolean canTransform();

    @NotNull
    WerewolfForm getForm();

    EntityClassType getEntityClass();

    EntityActionTier getEntityTier();

    default void reset() {
    }

    default void start(TransformType type) {
    }

    WerewolfTransformable _transformToWerewolf();

    WerewolfTransformable _transformBack();

}
