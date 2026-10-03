package de.teamlapen.werewolves.api;

import de.teamlapen.faction.api.FactionRegistries;
import de.teamlapen.faction.api.factions.IFaction;
import de.teamlapen.faction.api.factions.IFactionEntity;
import de.teamlapen.faction.api.registries.factions.DeferredFaction;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

class APIUtil {
    @SuppressWarnings("unchecked")
    static <Z extends IFactionEntity, L extends IFaction<Z>> DeferredFaction<Z, L> factionHolder(Identifier key) {
        return DeferredFaction.createFaction((ResourceKey<L>) ResourceKey.create(FactionRegistries.Keys.FACTION, key));
    }
}
