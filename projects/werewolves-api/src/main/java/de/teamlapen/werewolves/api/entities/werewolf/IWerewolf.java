package de.teamlapen.werewolves.api.entities.werewolf;

import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.IFactionEntity;
import de.teamlapen.werewolves.api.WerewolvesFactions;
import net.minecraft.core.Holder;

public interface IWerewolf extends IFactionEntity, IWerewolfDataholder {

    @Override
    default Holder<? extends IFaction<?>> getFaction() {
        return WerewolvesFactions.WEREWOLF;
    }
}
