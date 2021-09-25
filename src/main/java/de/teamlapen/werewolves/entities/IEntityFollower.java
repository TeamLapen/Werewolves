package de.teamlapen.werewolves.entities;

import de.teamlapen.vampirism.api.entity.IEntityLeader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public interface IEntityFollower {

    @Nonnull
    Optional<IEntityLeader> getLeader();

    void setLeader(@Nullable IEntityLeader leader);
}
