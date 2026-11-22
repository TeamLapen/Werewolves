package de.teamlapen.werewolves.common.tags;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.refinements.IRefinementSet;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.tags.TagKey;

public class ModRefinementSetTags {
    public static final TagKey<IRefinementSet> IS_WEREWOLF = tag("has_faction/werewolf");

    private static TagKey<IRefinementSet> tag(String name) {
        return TagKey.create(FactionRegistries.Keys.REFINEMENT_SET, WIdentifier.mod(name));
    }
}
