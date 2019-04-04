package de.teamlapen.vampirewerewolf.util;

import de.teamlapen.vampirewerewolf.api.entities.player.werewolf.IWerewolfPlayer;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import net.minecraft.entity.EnumCreatureType;

public class VReference {
    public static IPlayableFaction<IWerewolfPlayer> WEREWOLF_FACTION;

    /**
     * Werewolve creatures are of this creature type. But when they are counted for spawning they belong to {@link EnumCreatureType#MONSTER}
     */
    public static EnumCreatureType WEREWOLF_CREATURE_TYPE;
}
