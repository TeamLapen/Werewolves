package de.teamlapen.werewolves.common.world.entities.player;

import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.refinements.IRefinement;
import de.teamlapen.faction.common.RefinementSet;
import de.teamlapen.werewolves.common.tags.ModFactionsTags;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;

import java.util.Set;

public class WerewolfRefinementSet extends RefinementSet {

    public WerewolfRefinementSet(Rarity rarity, int color, Set<Holder<IRefinement>> refinements) {
        super(rarity, color, refinements);
    }

    @SafeVarargs
    public WerewolfRefinementSet(Rarity rarity, int color, Holder<IRefinement>... refinements) {
        super(rarity, color, refinements);
    }

    @Override
    public TagKey<IFaction<?>> getFaction() {
        return ModFactionsTags.IS_WEREWOLF;
    }
}
