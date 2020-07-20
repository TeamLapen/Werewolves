package de.teamlapen.werewolves.player.werewolf.actions;

import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.api.entity.player.actions.DefaultAction;
import de.teamlapen.werewolves.player.IWerewolfPlayer;
import de.teamlapen.werewolves.util.WReference;

import javax.annotation.Nonnull;

public abstract class DefaultWerewolfAction extends DefaultAction<IWerewolfPlayer> {

    @Nonnull
    @Override
    public IPlayableFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
