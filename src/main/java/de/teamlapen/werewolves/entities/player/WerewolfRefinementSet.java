package de.teamlapen.werewolves.entities.player;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.player.refinements.RefinementSet;
import de.teamlapen.werewolves.api.WReference;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Set;

public class WerewolfRefinementSet extends RefinementSet {

    public WerewolfRefinementSet(Rarity rarity, int color, Set<RegistryObject<? extends IRefinement>> refinements) {
        super(rarity, color, refinements);
    }

    @SafeVarargs
    public WerewolfRefinementSet(Rarity rarity, int color, RegistryObject<? extends IRefinement>... refinements) {
        super(rarity, color, refinements);
    }

    @Nonnull
    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
