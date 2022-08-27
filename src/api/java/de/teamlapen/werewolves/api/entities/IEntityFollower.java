package de.teamlapen.werewolves.api.entities;

import de.teamlapen.vampirism.api.entity.IEntityLeader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface IEntityFollower {

    @NotNull
    Optional<IEntityLeader> getLeader();

    void setLeader(@Nullable IEntityLeader leader);
}
