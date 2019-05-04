package de.teamlapen.werewolves.config;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.lib.lib.config.DefaultDouble;
import de.teamlapen.lib.lib.config.DefaultInt;
import de.teamlapen.vampirism.VampirismMod;
import java.io.File;

//TODO edit default values
//TODO edit default parameter
public class BalanceWerewolfPlayer extends BalanceValues {

    @DefaultInt(value = 2)
    public int WEREWOLF_DISGUISED_SPEED_ADDITION;

    @DefaultDouble(value = 6F)
    public double HARVESTSPEED_MAX;

    /**
     * Creates a configuration for balance values
     *
     * @param directory
     */
    public BalanceWerewolfPlayer(File directory) {
        super("werewolf_player", directory);
    }

    @Override
    protected boolean shouldUseAlternate() {
        return VampirismMod.isRealism();
    }
}
