package de.teamlapen.werewolves.api;

import de.teamlapen.faction.api.factions.IPlayableFaction;
import de.teamlapen.faction.api.registries.factions.DeferredFaction;
import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import net.minecraft.resources.Identifier;

public class WerewolvesFactions {

    public static final DeferredFaction<IWerewolfPlayer, IPlayableFaction<IWerewolfPlayer>> WEREWOLF = APIUtil.factionHolder(Keys.WEREWOLF);

    public static class Keys {
        public static final Identifier WEREWOLF = WIdentifier.mod("werewolf");
    }
}
