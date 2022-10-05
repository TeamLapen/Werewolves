package de.teamlapen.werewolves.entities.player;

import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.player.refinement.IRefinement;
import de.teamlapen.vampirism.player.refinements.RefinementSet;
import de.teamlapen.werewolves.util.WReference;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class WerewolfRefinementSet extends RefinementSet {

    public WerewolfRefinementSet(Rarity rarity, int color, Set<RegistryObject<? extends IRefinement>> refinements) {
        super(rarity, color, refinements);
    }

    @SafeVarargs
    public WerewolfRefinementSet(Rarity rarity, int color, RegistryObject<IRefinement>... refinements) {
        super(rarity, color, refinements);
    }

    @Nonnull
    @Override
    public IFaction<?> getFaction() {
        return WReference.WEREWOLF_FACTION;
    }
}
