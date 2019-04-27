package de.teamlapen.werewolves.api.entities.player.werewolf;

import de.teamlapen.vampirism.api.entity.minions.IMinionLord;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;

/**
 * Interface for the werewolf player data
 * Attached to all players as capability
 */
public interface IWerewolfPlayer extends IFactionPlayer<IWerewolfPlayer>, IWerewolf, IMinionLord {

    void transformWerewolf();

    void transformHuman();
}
