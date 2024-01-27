package de.teamlapen.werewolves.api.items;

import de.teamlapen.werewolves.api.entities.player.IWerewolfPlayer;
import de.teamlapen.werewolves.api.entities.werewolf.WerewolfForm;

public interface IWerewolfArmor {

    boolean canWear(IWerewolfPlayer player, WerewolfForm form);
}
