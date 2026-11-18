package de.teamlapen.werewolves.common.tags;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.werewolves.api.WIdentifier;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

public class ModFactionsTags {

    public static final TagKey<IFaction<?>> IS_WEREWOLF = tag("is_werewolf");
    public static final TagKey<IFaction<?>> WEREWOLF_MINION_TARGETS = tag("target/werewolf_minion");


    private static TagKey<IFaction<?>> tag(Identifier resourceLocation) {
        return TagKey.create(FactionRegistries.Keys.FACTION, resourceLocation);
    }

    private static TagKey<IFaction<?>> tag(String name) {
        return tag(WIdentifier.mod(name));
    }
}
