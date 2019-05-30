package de.teamlapen.werewolves.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;

public class WerewolfPlayerSpecialAttributes {
    public IFaction<?> disguisedAs = null;
    /* active level of the werewolf form */
    public int werewolf = 0;
    /* level of the werewolf form */
    public int werewolfLevel = 0;
    /* if the werewolf form can switch his shape */
    public boolean transformable = true;
    /* healing action */
    public boolean healing = false;
    /* heals of the healing action */
    public int heals = 0;
    public boolean moreFoodFromRawMeat = false;
    /* if the werewolf form can mine in werewolf form */
    public boolean werewolfharvest = false;
    /* animalhunter action */
    public boolean animalHunter = false;
    public boolean eatFlesh = false;

    public void addHeal(float heal) {
        this.heals = (int) Math.max(this.heals, this.heals + heal);
    }

    public void removeHeal(float heal) {
        this.heals = (int) Math.max(0, this.heals - heal);
    }
}
