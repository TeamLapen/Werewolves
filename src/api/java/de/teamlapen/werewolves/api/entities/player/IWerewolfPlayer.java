package de.teamlapen.werewolves.api.entities.player;

import de.teamlapen.vampirism.api.entity.player.IFactionPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.IWerewolf;

public interface IWerewolfPlayer extends IWerewolf, IFactionPlayer<IWerewolfPlayer> {
}
