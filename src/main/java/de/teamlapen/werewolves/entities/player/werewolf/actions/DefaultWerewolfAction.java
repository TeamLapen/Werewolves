package de.teamlapen.werewolves.entities.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.DefaultAction;
import de.teamlapen.werewolves.api.WReference;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;

import javax.annotation.Nonnull;

public abstract class DefaultWerewolfAction extends DefaultAction<IWerewolfPlayer> {

    @Nonnull
    @Override
    public IPlayableFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
