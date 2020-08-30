package de.teamlapen.werewolves.entities.werewolf;

import de.teamlapen.vampirism.api.entity.EntityClassType;
import de.teamlapen.vampirism.api.entity.actions.EntityActionTier;
import de.teamlapen.werewolves.entities.WerewolfFormUtil;

import javax.annotation.Nonnull;

public interface WerewolfTransformable {

    WerewolfTransformable transformToWerewolf();

    WerewolfTransformable transformBack();

    boolean canTransform();

    @Nonnull
    WerewolfFormUtil.Form getWerewolfForm();

    EntityClassType getEntityClass();

    EntityActionTier getEntityTier();

    int getEntityTextureType();
}
