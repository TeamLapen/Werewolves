package de.teamlapen.werewolves.api.entities.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.factions.IFactionEntity;
import de.teamlapen.werewolves.api.WReference;
import org.jetbrains.annotations.NotNull;

public interface IWerewolf extends IFactionEntity, IWerewolfDataholder {

    @Override
    default @NotNull IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
