package de.teamlapen.werewolves.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;

public class WerewolfPlayerSpecialAttributes {
    public IFaction<?> disguisedAs = null;
    public int werewolf = 0;
    public int werewolfLevel = 0;
    public boolean transformable = true;
    public int heals = 0;
    public boolean healing = false;
    public boolean moreFoodFromRawMeat = false;
    public boolean werewolfharvest = false;
    public int harvestLevel = 0;
    public float harvestSpeed = 1;
    public boolean animalHunter = false;
    public boolean eatFlesh = false;

    public void addHeal(float heal) {
        this.heals = (int) Math.max(this.heals, this.heals + heal);
    }

    public void removeHeal(float heal) {
        this.heals = (int) Math.max(0, this.heals - heal);
    }
}
