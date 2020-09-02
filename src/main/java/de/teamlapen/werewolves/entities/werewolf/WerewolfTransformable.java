package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;

import javax.annotation.Nonnull;

public interface WerewolfTransformable {

    default WerewolfTransformable transformBack() {
        WerewolfTransformable transformable = transformBack2();
        transformable.reset();
        return transformable;
    }

    default WerewolfTransformable transformToWerewolf() {
        WerewolfTransformable transformable = transformToWerewolf2();
        transformable.start();
        return transformable;
    }

    WerewolfTransformable transformToWerewolf2();

    WerewolfTransformable transformBack2();

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
}
