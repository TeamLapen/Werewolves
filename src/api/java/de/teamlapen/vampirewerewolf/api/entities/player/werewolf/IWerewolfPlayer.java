package de.teamlapen.vampirewerewolf.api.entities.player.werewolf;

import de.teamlapen.vampirewerewolf.api.entities.werewolf.IWerewolf;
import de.teamlapen.vampirism.api.entity.minions.IMinionLord;
import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;

/**
 * Interface for the werewolf player data
 * Attached to all players as capability
 */
public interface IWerewolfPlayer extends IFactionPlayer<IWerewolfPlayer>, IWerewolf, IMinionLord {

}
