package de.teamlapen.werewolves.entities.player;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.entity.player.refinements.RefinementSet;
import de.teamlapen.werewolves.api.WReference;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Supplier;

public class WerewolfRefinementSet extends RefinementSet {

    public WerewolfRefinementSet(Rarity rarity, int color, Set<Supplier<? extends IRefinement>> refinements) {
        super(rarity, color, refinements);
    }

    @SafeVarargs
    public WerewolfRefinementSet(Rarity rarity, int color, Supplier<? extends IRefinement>... refinements) {
        super(rarity, color, refinements);
    }

    @Nonnull
    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
