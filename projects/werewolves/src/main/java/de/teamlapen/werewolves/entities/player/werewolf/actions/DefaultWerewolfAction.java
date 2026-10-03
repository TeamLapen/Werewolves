package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.DefaultAction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;

import javax.annotation.Nonnull;
import java.util.Optional;

public abstract class DefaultWerewolfAction extends DefaultAction<IWerewolfPlayer> {

    @Nonnull
    @Override
    public Optional<IPlayableFaction<?>> getFaction() {
        return Optional.of(WReference.WEREWOLF_FACTION);
    }
}
