package de.teamlapen.vampirewerewolf.player.werewolf;

import de.teamlapen.vampirism.api.entity.factions.IFaction;

public class WerewolfPlayerSpecialAttributes {
    public boolean disguised = false;
    public IFaction disguisedAs = null;
    public boolean werewolf = false;
    public int heals = 0;
    public boolean healing = false;
    public boolean moreFoodFromRawMeat = false;
    
    public void addHeal(float heal) {
        heals = (int) Math.max(heals, heals + heal);
    }

    public void removeHeal(float heal) {
        heals = (int) Math.max(0, heals - heal);
    }

}
